package clonecoder.springLover.repository;

import clonecoder.springLover.controller.ProductForm;
import clonecoder.springLover.domain.Member;
import clonecoder.springLover.domain.Product;
import clonecoder.springLover.domain.Qna;
import clonecoder.springLover.service.MemberService;
import clonecoder.springLover.service.ProductService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class QnaRepositoryTest {
    @Autowired MemberService memberService;
    @Autowired ProductService productService;
    @Autowired QnaRepository qnaRepository;

    @Test
    public void 질문등록() throws Exception {
        // given
        Member member = Member.createMember("email", "name", "tel", "password");
        memberService.join(member);

        Product product = Product.create(ProductForm.createProductForm("good product", 10000, 10));
        productService.saveProduct(product);

        Qna qna = new Qna();
        qna.setWriter(member);
        qna.setProduct(product);
        qna.setComment("정말 좋은 상품인가요?");

        // when : 첫번째 qna 등록 및 두번째 qna 등록!
        qnaRepository.save(qna);
        Qna qna2 = new Qna();
        qna2.setWriter(member);
        qna2.setProduct(product);
        qna2.setComment("네 정말 좋은 상품입니다");
        qna2.setSuperQna(qna);
        qnaRepository.save(qna2);

        // then
        Assert.assertEquals("저장한 comment가 같습니다", "정말 좋은 상품인가요?" , qna.getComment());
        Assert.assertEquals("작성자가 같습니다", member , qna.getWriter());
        Assert.assertEquals("상품이 같습니다", product , qna.getProduct());
        Assert.assertEquals("qna2의 super(질문)이 qna입니다", qna , qna2.getSuperQna());
        Assert.assertEquals("qna등록일은 비어있습니다", null , qna2.getDate());
        Assert.assertEquals("등록된 qna수는 2개입니다", 2 , qnaRepository.findAll().size());
    }

}