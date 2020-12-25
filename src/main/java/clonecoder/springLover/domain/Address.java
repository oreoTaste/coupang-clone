package clonecoder.springLover.domain;

import clonecoder.springLover.controller.AddressForm;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Address {
    @Id
    @GeneratedValue
    @Column(name = "address_id")
    private Long id;

    private String city;
    private String street;
    private String detail;
    private String zipcode;
    private String ask;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    @Column(name = "receiver_name")
    private String receiverName;
    @Column(name = "receiver_tel")
    private String receiverTel;

    // 생성 메서드
    public static Address createAddress(AddressForm addressForm) {
        Address address = new Address();
        address.setStreet(addressForm.getStreet());
        address.setCity(addressForm.getCity());
        address.setAsk(addressForm.getAsk());
        address.setDetail(addressForm.getDetail());
        address.setZipcode(addressForm.getZipcode());
        address.setReceiverName(addressForm.getReceiverName());
        address.setReceiverTel(addressForm.getReceiverTel());
        return address;
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
                ", delivery=" + delivery +
                ", receiverName='" + receiverName + '\'' +
                ", receiverTel='" + receiverTel + '\'' +
                '}';
    }
}
