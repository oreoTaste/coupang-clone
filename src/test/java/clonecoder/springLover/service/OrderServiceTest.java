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

import java.util.ArrayList;
import java.util.List;

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
        List<Long> productIdList = new ArrayList<>();
        productIdList.add(product.getId());
        List<Integer> countList = new ArrayList<>();
        countList.add(5);

        Address address = new Address();
        address.setCity("Seoul");
        address.setStreet("my street");
        em.persist(address);
        // when
        Long orderId = orderService.cardCheckout(member.getId(), productIdList, countList, address);
        Order foundOrder = orderService.findOne(orderId);
        List<OrderProduct> orderProductList = foundOrder.getOrderProductList();

        // then
        assertEquals("상품 주문시 상태는 ORDER이어야 한다", OrderStatus.ORDER, foundOrder.getStatus());
        assertEquals("상품 주문시 상품종류는 정확해야 한다", 1, foundOrder.getOrderProductList().size());
        assertEquals("상품 주문시 주문금액은 가격 * 수량이다", 10000 * 5, foundOrder.getTotalPrice());
        assertEquals("상품 주문시 재고수량이 줄어야 한다", 95, product.getStock());
        assertEquals("orderProduct의 FK에 orderId가 매칭되어야 한다", orderId, orderProductList.get(0).getOrder().getId());
    }

    @Test(expected = NotEnoughStockException.class)
    public void 초과주문() throws Exception {
        // given
        Member member = Member.createMember("email", "name", "tel", "password");
        em.persist(member);

        ProductForm productForm = ProductForm.createProductForm("제품명", 10000, 100);
        Product product = Product.create(productForm);
        em.persist(product);

        List<Long> productList = new ArrayList<>();
        productList.add(product.getId());
        List<Integer> countList = new ArrayList<>();
        countList.add(101);

        Address address = new Address();
        address.setCity("Seoul");
        address.setStreet("happy road");
        em.persist(address);

        // when
        orderService.cardCheckout(member.getId(), productList, countList, address);

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

        List<Long> productList = new ArrayList<>();
        productList.add(product.getId());
        List<Integer> countList = new ArrayList<>();
        countList.add(100);

        Address address = new Address();
        address.setCity("Seoul");
        address.setStreet("happy road");
        em.persist(address);

        Long orderId = orderService.cardCheckout(member.getId(), productList, countList, address);

        // when
        orderService.cancel(orderId);
        Order foundOrder = orderService.findOne(orderId);

        // then
        assertEquals("취소후 재고수량이 같아야합니다.", 100, product.getStock());
        assertEquals("취소후 주문상태는 cancel입니다", OrderStatus.CANCEL, foundOrder.getStatus());
    }
}