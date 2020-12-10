package clonecoder.springLover.domain;

import clonecoder.springLover.controller.ProductForm;
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

    // 생성 메서드
    public Product create(ProductForm productForm) {
        Product product = new Product();
        product.setCategory(productForm.getCategory());
        product.setProducer(productForm.getProducer());
        product.setCategory(productForm.getCategory());
        product.setComment(productForm.getComment());
        product.setName(productForm.getName());
        product.setOrigin(productForm.getOrigin());
        product.setPrice(productForm.getPrice());
        product.setId(productForm.getId());
        product.setStock(productForm.getStock());
        product.setModel(productForm.getModel());
        product.setIs_rocket(productForm.getIs_rocket());
        return product;
    }

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
