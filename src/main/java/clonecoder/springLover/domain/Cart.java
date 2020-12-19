package clonecoder.springLover.domain;

import clonecoder.springLover.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter @Setter
@ToString
public class Cart {

    @Id
    @GeneratedValue
    @Column(name = "cart_id")
    private Long id;

    private int count;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    // 생성 메서드
    public static Cart createCart(Member member, Product product, int count) {
        Cart cart = new Cart();
        cart.setMember(member);
        cart.setProduct(product);
        cart.setCount(count);
        return cart;
    }

}
