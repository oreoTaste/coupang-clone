package clonecoder.springLover.controller;

import clonecoder.springLover.domain.Member;
import clonecoder.springLover.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.ConstructorArgs;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("login")
    public String login() {
        return "redirect:main";
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
        return "redirect:main";
    }
}
