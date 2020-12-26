package clonecoder.springLover.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter @Setter
@ToString
public class Delivery {
    @Id
    @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status; // 배송 중인지 여부;

    @OneToOne(mappedBy="delivery", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Order order;

    @Embedded
    private Address address;

    // 연관관계 메서드
    public void setOrder(Order order) {
        this.order = order;
        order.setDelivery(this);
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    // 생성 메서드
    public Delivery createDelivery(Order order, Address address) {
        Delivery delivery = new Delivery();
        delivery.setAddress(address);
        delivery.setOrder(order);
        delivery.setStatus(DeliveryStatus.READY);
        return delivery;
    }



}
