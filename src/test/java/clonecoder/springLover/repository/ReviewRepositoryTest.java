package clonecoder.springLover.repository;

import clonecoder.springLover.domain.Review;
import clonecoder.springLover.domain.EvaluationSearch;
import clonecoder.springLover.domain.Member;
import clonecoder.springLover.domain.Product;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ReviewRepositoryTest {

    @Autowired
    ReviewRepository reviewRepository;
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


        Review review = new Review();
        review.setReview("wow 예뻐요");
        // when
        Long commentId = reviewRepository.save(review);
        Review savedReview = reviewRepository.findOne(commentId);

        // then
        Assertions.assertEquals(review.getId(), savedReview.getId());
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
        List<Review> reviewList = reviewRepository.findAllByString(evaluationSearch);

        // then
        Assertions.assertEquals(reviewList.size(), 1);
    }
}