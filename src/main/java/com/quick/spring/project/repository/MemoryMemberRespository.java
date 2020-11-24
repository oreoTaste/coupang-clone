package com.quick.spring.project.repository;

import com.quick.spring.project.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//@Repository
public class MemoryMemberRespository implements MemberRepository{
    List<Member> list = new ArrayList<>();

    @Override
    public long insert(Member member) {
        list.add(member);
        return 0;
    }

    @Override
    public List<Member> findAll() {
        return list;
    }

    @Override
    public Optional<Member> findById(Long id) {
        for(Member member : list) {
            if(member.getId() == id) {
                return Optional.ofNullable(member);
            }
        }
        return Optional.ofNullable(null);
    }

    @Override
    public Optional<Member> findByName(String name) {
        for(Member member : list) {
            if(member.getName() == name) {
                return Optional.ofNullable(member);
            }
        }
        return Optional.ofNullable(null);
    }

}
