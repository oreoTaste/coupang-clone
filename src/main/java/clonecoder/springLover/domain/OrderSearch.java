package clonecoder.springLover.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter @Setter
@RequiredArgsConstructor
public class OrderSearch {

    private String memberEmail;
    private OrderStatus orderStatus;


}
