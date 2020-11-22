package com.quick.spring.project.repository;

import com.quick.spring.project.domain.Member;
import org.springframework.stereotype.Repository;
import java.util.*;

// @Repository
public class MemoryMemberRepository implements MemberRepository{

    List<Member> members = new LinkedList<>();

    @Override
    public long insert(Member member) {
        members.add(member);
        return 0;
    }

    @Override
    public List<Member> findAll() {
        return members;
    }
}
