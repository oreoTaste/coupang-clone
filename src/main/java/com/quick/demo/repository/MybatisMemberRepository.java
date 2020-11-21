package com.quick.demo.repository;

import com.quick.demo.domain.Member;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MybatisMemberRepository implements MemberRepository{

    private SqlSession sqlSession;
    @Autowired
    public MybatisMemberRepository(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Override
    public String save(Member member) {
        sqlSession.insert("Member.save", member);
        return member.getName();
    }

    @Override
    public Optional<Member> findByName(String name) {
        return sqlSession.selectOne("Member.findByName", name);
    }

    @Override
    public List<Member> findAll() {
        return sqlSession.selectList("Member.findAll");
    }


}
