package com.quick.spring.project.repository;

import com.quick.spring.project.domain.Member;

import java.util.List;

public interface MemberRepository {
    long insert(Member member);
    List<Member> findAll();
}
