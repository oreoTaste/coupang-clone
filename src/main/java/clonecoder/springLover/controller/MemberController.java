package clonecoder.springLover.controller;

import clonecoder.springLover.domain.Member;
import clonecoder.springLover.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.connector.Request;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("login")
    public String login(Member member, Model model, HttpServletRequest request) throws Exception {
        if(!memberService.login(member.getEmail(), member.getPassword())){
            throw new IllegalAccessException("등록되지 않은 회원입니다");
        }

        request.getSession().setAttribute("email", memberService.findByEmail(member.getEmail()).getEmail());
        return "main";
    }

    @GetMapping("register")
    public String registerForm() {
        return "member/registerForm";
    }

    @PostMapping("register")
    public String register(MemberForm memberForm) {
        String password = memberForm.getPassword();
        String email = memberForm.getEmail();
        String name = memberForm.getName();
        String tel = memberForm.getTel();
        Member member = Member.createMember(email, name, tel, password);

        memberService.join(member);
        return "redirect:/";
    }
}
