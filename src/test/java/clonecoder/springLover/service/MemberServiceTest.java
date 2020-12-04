package clonecoder.springLover.service;

import clonecoder.springLover.domain.Member;
import clonecoder.springLover.repository.MemberRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@SpringBootTest
@Transactional
@RunWith(SpringRunner.class)
public class MemberServiceTest {
    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    public void 회원가입() throws Exception {
        // given
        Member member = new Member();
        member.setName("YK");
        member.setEmail("email@email.com");

        // when
        Long memberId = memberService.join(member);
        Member foundMember = memberService.findOne(memberId);

        // then
        Assertions.assertEquals(foundMember.getEmail(), member.getEmail());
        Assertions.assertEquals(foundMember.getId(), member.getId());
    }

    @Test(expected = IllegalStateException.class)
    public void 중복회원_테스트() throws Exception {
        // given
        Member member = new Member();
        member.setName("YK");
        member.setEmail("email@email.com");
        memberService.join(member);
        Member member2 = new Member();
        member2.setName("YK2");
        member2.setEmail("email@email.com");

        // when
        Long memberId = memberService.join(member2);

        // then
        fail("중복된 회원이 있으면 회원등록이 되지 않아야 합니다.");
    }

}