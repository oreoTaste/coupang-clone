package clonecoder.springLover.repository;

import clonecoder.springLover.domain.Review;
import clonecoder.springLover.domain.EvaluationSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReviewRepository {
    private final EntityManager em;

    public Long save(Review review) {
        em.persist(review);
        return review.getId();
    }

    public List<Review> findAll() {
        return em.createQuery("select r from Review r", Review.class).getResultList();
    }

    public Review findOne(Long id) {
        return em.find(Review.class, id);
    }

    public List<Review> findAllByString(EvaluationSearch evaluationSearch) {
        //language=JPAQL
        String jpql = "select r From Review r"
                + " join r.member m"
                + " on r.member.id = m.id"
                + " join r.product p"
                + " on r.product.id = p.id"
                + " where m.email = :email "
                + " where m.email = :email "
                + " and r.id = :id";

        TypedQuery<Review> query = em.createQuery(jpql, Review.class)
                .setMaxResults(1000); //최대 1000건
        if (evaluationSearch.getProductId() != null) {
            query = query.setParameter("id", evaluationSearch.getProductId());
        }
        if (StringUtils.hasText(evaluationSearch.getEmail())) {
            query = query.setParameter("email", evaluationSearch.getEmail());
        }
        return query.getResultList();
    }

}
