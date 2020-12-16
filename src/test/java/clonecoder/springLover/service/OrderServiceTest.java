package clonecoder.springLover.service;

import clonecoder.springLover.controller.ProductForm;
import clonecoder.springLover.domain.*;
import clonecoder.springLover.exception.NotEnoughStockException;
import clonecoder.springLover.repository.OrderRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.Assert.*;

@SpringBootTest
@Transactional
@RunWith(SpringRunner.class)
public class OrderServiceTest {

    @PersistenceContext EntityManager em;
    @Autowired OrderService orderService;

    @Test
    public void 주문처리() throws Exception {
        // given
        Member member = Member.createMember("email", "name", "tel", "password");
        em.persist(member);

        ProductForm productForm = ProductForm.createProductForm("name", 10000, 100);
        Product product = Product.create(productForm);
        em.persist(product);

        Address address = new Address();
        address.setCity("Seoul");
        address.setStreet("my street");
        em.persist(address);

        // when
        Long orderId = orderService.checkout(member.getId(), product.getId(), 5, address);
        Order foundOrder = orderService.findOne(orderId);

        // then
        assertEquals("상품 주문시 상태는 ORDER이어야 한다", OrderStatus.ORDER, foundOrder.getStatus());
        assertEquals("상품 주문시 상품종류는 정확해야 한다", 1, foundOrder.getOrderProductList().size());
        assertEquals("상품 주문시 주문금액은 가격 * 수량이다", 5 * 10000, foundOrder.getTotalPrice());
        assertEquals("상품 주문시 재고수량이 줄어야 한다", 95, product.getStock());
    }

    @Test(expected = NotEnoughStockException.class)
    public void 초과주문() throws Exception {
        // given
        Member member = Member.createMember("email", "name", "tel", "password");
        em.persist(member);

        ProductForm productForm = ProductForm.createProductForm("제품명", 10000, 100);
        Product product = Product.create(productForm);
        em.persist(product);

        Address address = new Address();
        address.setCity("Seoul");
        address.setStreet("happy road");
        em.persist(address);

        // when
        orderService.checkout(member.getId(), product.getId(), 101, address);

        // then
        fail("100개 물품 중에 101개 물품을 주문하면은 에러가 발생합니다");
    }
    
    @Test
    public void 주문취소() throws Exception {
        // given
        Member member = Member.createMember("email", "name", "tel", "password");
        em.persist(member);

        ProductForm productForm = ProductForm.createProductForm("제품명", 10000, 100);
        Product product = Product.create(productForm);
        em.persist(product);

        Address address = new Address();
        address.setCity("Seoul");
        address.setStreet("happy road");
        em.persist(address);

        Long orderId = orderService.checkout(member.getId(), product.getId(), 10, address);

        // when
        orderService.cancel(orderId);
        Order foundOrder = orderService.findOne(orderId);

        // then
        assertEquals("취소후 재고수량이 같아야합니다.", 100, product.getStock());
        assertEquals("취소후 주문상태는 cancel입니다", OrderStatus.CANCEL, foundOrder.getStatus());
    }
}