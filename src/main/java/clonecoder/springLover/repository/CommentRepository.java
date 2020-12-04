package clonecoder.springLover.repository;

import clonecoder.springLover.domain.Comment;
import clonecoder.springLover.domain.EvaluationSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.LinkedList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentRepository {
    private final EntityManager em;

    public Long save(Comment comment) {
        em.persist(comment);
        return comment.getId();
    }

    public List<Comment> findAll() {
        return em.createQuery("select c from Comment c", Comment.class).getResultList();
    }

    public Comment findOne(Long id) {
        return em.find(Comment.class, id);
    }

    public List<Comment> findAllByString(EvaluationSearch evaluationSearch) {
        //language=JPAQL
        String jpql = "select c From Comment c"
                + " join c.member m"
                + " on c.member.id = m.id"
                + " join c.product p"
                + " on c.product.id = p.id"
                + " where m.email = :email "
                + " and p.id = :id";

        TypedQuery<Comment> query = em.createQuery(jpql, Comment.class)
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
