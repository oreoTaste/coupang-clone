package clonecoder.springLover.repository;

import clonecoder.springLover.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {
    @Autowired
    private EntityManager em;

    @Transactional
    public Long save(Member member) {
        em.persist(member);
        return member.getId();
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }

    public List<Member> findByEmail(String email) {
        List<Member> memberList = em.createQuery("select m from Member m where m.email = :email", Member.class)
                                    .setParameter("email", email)
                                    .getResultList();
        if(memberList.isEmpty()) {
            return null;
        } else {
            return memberList;
        }
    }


}
