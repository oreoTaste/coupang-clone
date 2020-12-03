package clonecoder.springLover.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MemberController {
    @PostMapping("login")
    public String login() {
        return "/";
    }

    @GetMapping("register")
    public String registerForm() {
        return "member/registerForm";
    }

    @PostMapping("register")
    public String register(MemberForm memberForm) {
        System.out.println(memberForm);
        return "/";
    }
}
