package clonecoder.springLover.domain;

import clonecoder.springLover.controller.AddressForm;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.util.List;

@Getter @Setter
@Embeddable
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

    protected Address() { }
    public Address(AddressForm addressForm) {
        this.setStreet(addressForm.getStreet());
        this.setCity(addressForm.getCity());
        this.setAsk(addressForm.getAsk());
        this.setDetail(addressForm.getDetail());
        this.setZipcode(addressForm.getZipcode());
        this.setReceiverName(addressForm.getReceiverName());
        this.setReceiverTel(addressForm.getReceiverTel());
    }

    private String receiverName;
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
                ", receiverName='" + receiverName + '\'' +
                ", receiverTel='" + receiverTel + '\'' +
                '}';
    }
}
