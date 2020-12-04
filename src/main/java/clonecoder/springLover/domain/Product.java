package clonecoder.springLover.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Product {
    @Id
    @Column(name = "product_id")
    @GeneratedValue
    private Long id;

    private int price;
    private int stock;
    private String name;
    private String model;
    private String description;
    private String is_rocket;
    private String thumbnail;
    private String producer;
    private String origin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="comment_id")
    private Comment comment;

    // 비즈니스 로직
    // order 테이블에서 활용 (주문취소 시)
    public void addStock(int quantity) {
        this.stock += quantity;
    }

    // orderProduct에서 활용 (생성메서드에서)
    public void removeStock(int quantity) {
        this.stock -= quantity;
    }

}
