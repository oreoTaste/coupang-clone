package clonecoder.springLover.domain;

import clonecoder.springLover.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class OrderProduct {
    @Id
    @GeneratedValue
    @Column(name = "order_product_id")
    private Long id;

    private int price;
    private int stock;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "review_id")
    private Review review;

    // 생성 메서드
    public static OrderProduct createOrderProduct(Product product, int price, int stock) throws NotEnoughStockException {
        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setPrice(price);
        orderProduct.setProduct(product);
        orderProduct.setStock(stock);
        product.removeStock(stock);
        return orderProduct;
    }

    // 비즈니스 로직
    // order table에 활용 (가격조회)
    public int getTotalPrice() {
        return this.price * this.stock;
    }

    // order 테이블에서 활용 (주문취소 시)
    public void cancel() {
        this.product.addStock(stock);
    }

    @Override
    public String toString() {
        return "OrderProduct{" +
                "id=" + id +
                ", price=" + price +
                ", stock=" + stock +
                ", order=" + order +
                ", product=" + product +
//                ", review=" + review +
                '}';
    }
}
