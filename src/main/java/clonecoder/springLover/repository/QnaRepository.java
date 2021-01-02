package clonecoder.springLover.repository;


import clonecoder.springLover.domain.Member;
import clonecoder.springLover.domain.Qna;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class QnaRepository {
    private final EntityManager em;

    public Long save(Qna qna) {
        em.persist(qna);
        return qna.getId();
    }

    public List<Qna> findAll() {
        return em.createQuery("select q from Qna q", Qna.class)
                .getResultList();
    }

    public List<Qna> findAll(int num) {
        TypedQuery<Qna> query = em.createQuery("select q from Qna q ORDER BY q.date DESC", Qna.class);
        return query.setMaxResults(num).getResultList();
    }

    public Qna findOne(Long id) {
        return em.find(Qna.class, id);
    }


}
