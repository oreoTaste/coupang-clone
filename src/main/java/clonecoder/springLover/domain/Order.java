package clonecoder.springLover.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {
    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ColumnDefault(value = "CURRENT_TIMESTAMP")
    private Timestamp date;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="delivery_id")
    private Delivery delivery;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    private List<OrderProduct> orderProductList = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private OrderStatus status;


    // 연관관계 메서드
    public void setMember(Member member) {
        this.member = member;
        member.getOrderList().add(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

    // 생성 메서드
    public static Order createDefaultOrder(Member member, Delivery delivery, OrderProduct... orderProducts) {
        return createOrder(OrderStatus.ORDER, member, delivery, orderProducts);
    }

    public static Order createPayedOrder(Member member, Delivery delivery, OrderProduct... orderProducts) {
        return createOrder(OrderStatus.PAID, member, delivery, orderProducts);
    }

    public static Order createOrder(OrderStatus orderStatus, Member member, Delivery delivery, OrderProduct... orderProducts) {
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        order.setDate(new Timestamp(System.currentTimeMillis()));
        order.setStatus(orderStatus);

        for(OrderProduct orderProduct : orderProducts) {
            order.addOrderProducts(orderProduct);
        }
        return order;
    }


    private void addOrderProducts(OrderProduct orderProduct) {
        orderProductList.add(orderProduct);
        orderProduct.setOrder(this);
    }

    // 비즈니스 로직
    public void cancel() {
        if (delivery.getStatus() != DeliveryStatus.READY) {
            throw new IllegalStateException("배송중 혹은 배송완료입니다.");
        }

        this.setStatus(OrderStatus.CANCEL);
        for (OrderProduct orderProduct : orderProductList) {
            orderProduct.cancel();
        }
    }

    // 조회 로직 (가격조회)
    public int getTotalPrice() {
        int totalPrice = 0;
        for(OrderProduct orderProduct : orderProductList) {
            totalPrice += orderProduct.getTotalPrice();
        }
        return totalPrice;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", date=" + date +
                ", member=" + member +
                ", delivery=" + delivery +
                ", orderProductList=" + orderProductList.size() +
                ", status=" + status +
                '}';
    }
}
