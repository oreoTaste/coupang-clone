package com.quick.demo.repository;

import com.quick.demo.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    public String save(Member member);
    public Optional<Member> findByName(String name);
    public List<Member> findAll();
}
