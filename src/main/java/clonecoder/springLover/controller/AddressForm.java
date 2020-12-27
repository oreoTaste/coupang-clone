package clonecoder.springLover.controller;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.Embeddable;
import javax.persistence.Entity;

@Getter @Setter
@ToString
public class AddressForm {
    protected String city;
    protected String street;
    protected String detail;
    protected String zipcode;
    protected String ask;
    protected String receiverName;
    protected String receiverTel;
}
