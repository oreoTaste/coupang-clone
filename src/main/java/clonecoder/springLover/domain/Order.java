package clonecoder.springLover.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {
    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="delivery_id")
    private Delivery delivery;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="order_product_id")
    private List<OrderProduct> orderProductList;

    private OrderStatus status;


    // 연관관계 메서드
    public void setMember(Member member) {
        this.member = member;
        member.getOrderList().add(this);
    }

    public void setOrderProduct(OrderProduct orderProduct) {
        orderProductList.add(orderProduct);
        orderProduct.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    // 생성 메서드
    public static Order createOrder(Member member, Delivery delivery, OrderProduct... orderProducts) {
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for(OrderProduct orderProduct : orderProducts) {
            order.addOrderProducts(orderProduct);
        }
        order.setStatus(OrderStatus.ORDER);
        return order;
    }

    private void addOrderProducts(OrderProduct orderProduct) {
        orderProductList.add(orderProduct);
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


}
