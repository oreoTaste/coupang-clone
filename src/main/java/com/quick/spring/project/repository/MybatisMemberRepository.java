package com.quick.spring.project.repository;

import com.quick.spring.project.domain.Member;
import com.quick.spring.project.exception.DuplicateMemberException;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class MybatisMemberRepository implements MemberRepository{
    SqlSession sqlSession;
    public MybatisMemberRepository(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Transactional
    @Override
    public long insert(Member member) throws Exception {
        sqlSession.insert("Member.save", member);
        return member.getId();
    }

    @Override
    public List<Member> findAll() throws Exception {
        return sqlSession.selectList("Member.findAll");
    }

    @Override
    public Optional<Member> findById(Long id) throws Exception{
        return sqlSession.selectOne("Member.findById");
    }

    @Override
    public Optional<Member> findByName(String name) throws Exception{
        return sqlSession.selectOne("Member.findByName", name);
    }

    @Override
    public Optional<Member> findByEmail(String email) throws Exception {
        return sqlSession.selectOne("Member.findByEmail", email);
    }
}
