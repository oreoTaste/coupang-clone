package com.quick.demo.controller;

import com.quick.demo.domain.Member;
import com.quick.demo.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MemberController {

    MemberService memberService;
    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/new")
    public String signin(Member member) {
        System.out.println("START : /new에 진입");
        return "new";
    }

    @PostMapping("/new")
    public String register(Member member) {
        System.out.println("END : /new로 진입완료" + member.getName() + "님의 비밀번호는 " + member.getPassword() + "입니다.");
        memberService.join(member);
        return "redirect:/";
    }
    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("members", memberService.findMembers());
        return "list";
    }
}
