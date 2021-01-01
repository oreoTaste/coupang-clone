package clonecoder.springLover.domain;

import clonecoder.springLover.controller.AddressForm;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter //@Setter
public class Address extends AddressForm{
    @Id
    @GeneratedValue
    @Column(name = "address_id")
    private Long id;
    private String city;
    private String street;
    private String detail;
    private String zipcode;
    private String ask;
    private String receiverName;
    private String receiverTel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;


    @OneToOne
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    // 생성자
    public Address() {}
    public Address(AddressForm addressForm) {
        this.street = addressForm.getStreet();
        this.city = addressForm.getCity();
        this.ask = addressForm.getAsk();
        this.detail = addressForm.getDetail();
        this.zipcode = addressForm.getZipcode();
        this.receiverName = addressForm.getReceiverName();
        this.receiverTel = addressForm.getReceiverTel();
    }

    // 생성 메서드
    public Address setAddress(Address addressForm) {
        this.street = addressForm.getStreet();
        this.city = addressForm.getCity();
        this.ask = addressForm.getAsk();
        this.detail = addressForm.getDetail();
        this.zipcode = addressForm.getZipcode();
        this.receiverName = addressForm.getReceiverName();
        this.receiverTel = addressForm.getReceiverTel();
        return this;
    }

    public void setMember(Member member) {
        this.member = member;
    }
    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", detail='" + detail + '\'' +
                ", zipcode='" + zipcode + '\'' +
                ", ask='" + ask + '\'' +
                ", memberId=" + member.getId() +
                ", delivery=" + delivery.getId() +
                ", receiverName='" + receiverName + '\'' +
                ", receiverTel='" + receiverTel + '\'' +
                '}';
    }
}
