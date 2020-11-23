package com.quick.spring.project.controller;

import com.quick.spring.project.domain.Member;
import com.quick.spring.project.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String form() {
        return "form";
    }
    @PostMapping("/new")
    public String register(Member member) {
        memberService.save(member);
        System.out.println("name: " + member.getName() + " email: " + member.getEmail() + " id: " + member.getId());
        return "redirect:/";
    }
    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("members", memberService.findAll());
        return "list";
    }
}
