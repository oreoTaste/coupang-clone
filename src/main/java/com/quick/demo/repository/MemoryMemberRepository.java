package com.quick.demo.repository;

import com.quick.demo.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

public class MemoryMemberRepository implements MemberRepository {
    Map<String, Member> list = new HashMap<>();

    @Override
    public String save(Member member) {
        list.put(member.getName(), member);
        return member.getName();
    }

    @Override
    public Optional<Member> findByName(String name) {
        return Optional.ofNullable(list.get(name));
    }

    @Override
    public List<Member> findAll() {
        Collection<Member> values = list.values();
        return values.stream().collect(Collectors.toList());
    }
}
