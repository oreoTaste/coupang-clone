package clonecoder.springLover.service;

import clonecoder.springLover.domain.Product;
import clonecoder.springLover.repository.MemberRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@SpringBootTest
@Transactional
@RunWith(SpringRunner.class)
public class ProductServiceTest {
    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired ProductService productService;
    @Autowired EntityManager em;

    @Test
    public void 제품등록() {
        // given
        Product product = new Product();
        product.setName("질좋은 샴푸");

        // when
        Long productId = productService.saveProduct(product);
        Product savedProduct = productService.findOne(productId);

        // then
        Assert.assertEquals("저장해도 같은 이름의 상품입니다.", product.getName(), savedProduct.getName());
        Assert.assertEquals("저장하면 id값이 자동으로 연동됩니다.", product.getId(), savedProduct.getId());
    }


}