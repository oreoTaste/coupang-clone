package com.quick.spring.project.repository;

import com.quick.spring.project.domain.Member;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class MybatisMemberRepository implements MemberRepository {
    
    SqlSession sqlSession;
    @Autowired
    public MybatisMemberRepository(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }
    
    @Transactional
    @Override
    public long insert(Member member) {
        return sqlSession.insert("Member.save", member);
    }

    @Override
    public List<Member> findAll() {
        return sqlSession.selectList("Member.findAll");
    }
}
