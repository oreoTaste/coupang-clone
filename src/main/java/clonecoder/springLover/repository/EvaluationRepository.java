package clonecoder.springLover.repository;

import clonecoder.springLover.domain.Evaluation;
import clonecoder.springLover.domain.EvaluationSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class EvaluationRepository {
    private final EntityManager em;

    public Long save(Evaluation evaluation) {
        em.persist(evaluation);
        return evaluation.getId();
    }

    public List<Evaluation> findAll() {
        return em.createQuery("select e from Evaluation e", Evaluation.class).getResultList();
    }

    public Evaluation findOne(Long id) {
        return em.find(Evaluation.class, id);
    }

    public List<Evaluation> findAllByString(EvaluationSearch evaluationSearch) {
        //language=JPAQL
        String jpql = "select e From Evaluation e join e.member m join e.product p";
        boolean isFirstCondition = true;
        //주문 상태 검색
        if (evaluationSearch.getProductId() != null) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " e.product_id = :id";
        }
        //회원 이름 검색
        if (StringUtils.hasText(evaluationSearch.getEmail())) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " e.email like :email";
        }
        TypedQuery<Evaluation> query = em.createQuery(jpql, Evaluation.class)
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
