package clonecoder.springLover.service;

import clonecoder.springLover.controller.ProductForm;
import clonecoder.springLover.domain.Cart;
import clonecoder.springLover.domain.Member;
import clonecoder.springLover.domain.Product;
import clonecoder.springLover.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CartServiceTest {

    @Autowired CartService cartService;
    @Autowired MemberService memberService;
    @Autowired ProductService productService;

    @Test
    @Transactional
    public void 카트담기() throws Exception {
        // given
        Member member = Member.createMember("email", "name", "tel", "password");
        memberService.join(member);

        Product product = Product.create(ProductForm.createProductForm("productName", 10000, 10));
        productService.saveProduct(product);

        // when
        cartService.save(member.getId(), product.getId(), 1);
        List<Cart> myCart = cartService.findMyCart(member.getId());

        // then
        assertEquals("장바구니 상품의 종류는 1개이다", 1, myCart.size());
        assertEquals("원래 상품의 재고는 그대로다", 10, product.getStock());
    }

    @Test
    @Transactional
    public void 카트담고없애기() throws Exception {
        // given
        Member member = Member.createMember("email", "name", "tel", "password");
        memberService.join(member);

        Product product = Product.create(ProductForm.createProductForm("productName", 10000, 10));
        productService.saveProduct(product);

        // when
        cartService.save(member.getId(), product.getId(), 1);
        List<Cart> myCart = cartService.findMyCart(member.getId());
        cartService.clear(myCart.get(0));

        // then
        assertEquals("장바구니 상품의 종류는 0개이다", 0, myCart.size());
    }

}