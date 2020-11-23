package com.quick.spring.project.repository;

import com.quick.spring.project.domain.Member;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MybatisMemberRepository implements MemberRepository{
    SqlSession sqlSession;
    public MybatisMemberRepository(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Override
    public long insert(Member member) {
        sqlSession.insert("Member.save", member);
        return member.getId();
    }

    @Override
    public List<Member> findAll() {
        return sqlSession.selectList("Member.findAll");
    }

    @Override
    public Optional<Member> findById(Long id) {
        return sqlSession.selectOne("Member.findById");
    }

    @Override
    public Optional<Member> findByName(String name) {
        return sqlSession.selectOne("Member.findByName");
    }
}
