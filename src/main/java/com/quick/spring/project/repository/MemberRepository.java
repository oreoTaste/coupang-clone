package com.quick.spring.project.repository;

import com.quick.spring.project.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    public long insert(Member member);
    public List<Member> findAll();
    public Optional<Member> findById(Long id);
    public Optional<Member> findByName(String name);
}
