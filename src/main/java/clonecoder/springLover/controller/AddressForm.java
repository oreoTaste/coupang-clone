package clonecoder.springLover.controller;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class AddressForm {
    private String city;
    private String street;
    private String detail;
    private String zipcode;
    private String ask;

    private String receiverName;
    private String receiverTel;
}
