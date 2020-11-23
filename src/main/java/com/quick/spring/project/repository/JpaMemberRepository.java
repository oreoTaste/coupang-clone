package com.quick.spring.project.repository;

import com.quick.spring.project.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
/*
@Repository
public class JpaMemberRepository implements MemberRepository{
    EntityManager em;
    @Autowired
    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Transactional
    @Override
    public long insert(Member member) {
        em.persist(member);
        em.flush();
        return member.getId();
    }

    @Override
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(em.find(Member.class, id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter(1, name).getResultList().stream().findAny();
    }
}
*/