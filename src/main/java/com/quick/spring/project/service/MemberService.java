package com.quick.spring.project.service;

import com.quick.spring.project.domain.Member;
import com.quick.spring.project.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {
    MemberRepository memberRepository;
    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public long save(Member member) throws Exception{
        memberRepository.insert(member);
        return 0;
    }

    public List<Member> findAll() throws Exception {
        return memberRepository.findAll();
    }

    public Optional<Member> findByEmail(String email) throws Exception{
        return memberRepository.findByEmail(email);
    }
}
