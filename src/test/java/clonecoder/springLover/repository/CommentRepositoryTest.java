package clonecoder.springLover.repository;

import clonecoder.springLover.domain.Comment;
import clonecoder.springLover.domain.EvaluationSearch;
import clonecoder.springLover.domain.Member;
import clonecoder.springLover.domain.Product;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CommentRepositoryTest {

    @Autowired CommentRepository commentRepository;
    @Autowired MemberRepository memberRepository;
    @Autowired ProductRepository productRepository;
    @Autowired EntityManager em;

    @Transactional
    public Long 댓글추가() throws Exception {
        // given
        Member member = new Member();
        member.setName("YK");
        member.setEmail("email@email.com");
        Long memberId = memberRepository.save(member);
        Member foundMember = memberRepository.findOne(memberId);

        Product product = new Product();
        product.setName("좋은 제품");
        Long productId = productRepository.save(product);
        Product foundProduct = productRepository.findOne(productId);


        Comment comment = new Comment();
        comment.setComment("wow 예뻐요");
        comment.setMember(foundMember);
        comment.setProduct(foundProduct);
        // when
        Long commentId = commentRepository.save(comment);
        Comment savedComment = commentRepository.findOne(commentId);

        // then
        Assertions.assertEquals(comment.getId(), savedComment.getId());
        Assertions.assertEquals(comment.getProduct(), savedComment.getProduct());
        Assertions.assertEquals(comment.getMember(), savedComment.getMember());
        return productId;
    }

    @Test
    @Transactional
    public void 댓글검색() throws Exception {
        // given
        Long productId = 댓글추가();

        // when
        EvaluationSearch evaluationSearch = new EvaluationSearch();
        evaluationSearch.setEmail("email@email.com");
        evaluationSearch.setProductId(productId);
        List<Comment> commentList = commentRepository.findAllByString(evaluationSearch);

        // then
        Assertions.assertEquals(commentList.size(), 1);
        Assertions.assertEquals(commentList.get(0).getMember().getEmail(), "email@email.com");
    }
}