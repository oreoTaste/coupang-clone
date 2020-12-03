package clonecoder.springLover.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Product {
    @Id
    @Column(name = "product_id")
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



}
