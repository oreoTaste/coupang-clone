package com.quick.spring.project.service;

import com.quick.spring.project.domain.Member;
import com.quick.spring.project.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {
    MemberRepository memberRepository;
    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    public long save(Member member) {
        memberRepository.insert(member);
        return 0;
    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }
}
