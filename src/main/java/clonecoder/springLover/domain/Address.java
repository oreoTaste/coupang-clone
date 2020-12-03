package clonecoder.springLover.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter @Setter
public class Address {
    @Id
    @GeneratedValue
    @Column(name = "address_id")
    private Long id;

    private String city;
    private String street;
    private String zipcode;

}
