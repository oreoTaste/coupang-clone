package clonecoder.springLover.controller;

import clonecoder.springLover.domain.Category;
import clonecoder.springLover.domain.Review;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class ProductForm {

    private Long id;
    private int price;
    private int stock;
    private String name;
    private String model;
    private String is_rocket;
    private String producer;
    private String origin;
    private Category category;
    private Review review;

    // 생성 메서드
    public static ProductForm createProductForm(String name, int price, int stock) {
        ProductForm productForm = new ProductForm();
        productForm.setName(name);
        productForm.setPrice(price);
        productForm.setStock(stock);
        return productForm;
    }
}
