package clonecoder.springLover.repository;

import clonecoder.springLover.domain.Member;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    EntityManager em;

    @Test
    public void 회원등록() throws Exception {
        // given
        Member member = new Member();
        member.setEmail("email@email.com");
        member.setName("oreoTaste");

        // when
        Long savedId = memberRepository.save(member);
        Member savedMember = memberRepository.findOne(savedId);
        em.flush();

        // then
        Assert.assertEquals("아이디가 같아야함", member.getId(), savedMember.getId());
        Assert.assertEquals("이름이 같아야함", member.getName(), savedMember.getName());
    }
}