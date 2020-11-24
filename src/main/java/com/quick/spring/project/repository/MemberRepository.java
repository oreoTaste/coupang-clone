package com.quick.spring.project.repository;

import com.quick.spring.project.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    public long insert(Member member) throws Exception;
    public List<Member> findAll() throws Exception;
    public Optional<Member> findById(Long id) throws Exception;
    public Optional<Member> findByName(String name) throws Exception;
    public default Optional<Member> findByEmail(String email) throws Exception { return Optional.empty();}
}
