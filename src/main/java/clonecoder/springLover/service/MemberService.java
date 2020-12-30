package clonecoder.springLover.service;

import clonecoder.springLover.domain.Member;
import clonecoder.springLover.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public Long join(Member member) {
        duplicateCheck(member.getEmail());
        Long savedId = memberRepository.save(member);
        return savedId;
    }

    @Transactional
    public boolean changePassword(HttpServletRequest request, String curPas, String newPas) {
        Member member = checkValidity(request);
        if(member.getPassword().equals(curPas)) {
            member.setPassword(newPas);
            return true;
        }
        return false;

    }

    public boolean login(String email, String password) {
        List<Member> members = memberRepository.findByEmail(email);
        if(members.size() == 0) return false;
        if(password.equals(members.get(0).getPassword())) {
            return true;
        };
        return false;
    }

    private void duplicateCheck(String email) {
        List<Member> members = memberRepository.findByEmail(email);
        if(members.size() > 0) {
            throw new IllegalStateException("중복된 회원이 있습니다");
        }
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findByEmail(String email) {
        List<Member> members = memberRepository.findByEmail(email);
        return members.get(0);
    }

    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

    public Member checkValidity(HttpServletRequest request) {
        Long memberId = (Long) request.getSession().getAttribute("id");
        Member foundMember = findOne(memberId);
        if(foundMember == null) {
            throw new IllegalArgumentException("올바른 로그인 정보가 필요합니다.");
        }
        return foundMember;
    }
}
