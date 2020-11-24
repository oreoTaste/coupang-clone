package com.quick.spring.project.controller;

import com.quick.spring.project.domain.Member;
import com.quick.spring.project.exception.DuplicateMemberException;
import com.quick.spring.project.service.Encryption;
import com.quick.spring.project.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MemberController {
    @Autowired
    DuplicateMemberException duplicateMemberException;

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
    public String register(Member member) throws Exception {
        try {
            memberService.findByEmail(member.getEmail());
        } catch (Exception e) {
            throw new DuplicateMemberException("이미 등록된 이메일 주소입니다.");
        }
        member.setPassword(Encryption.encrypt(member.getPassword()));
        memberService.save(member);
        System.out.println("name: " + member.getName() + " email: " + member.getEmail() + " id: " + member.getId());
        return "redirect:/";
    }

    @GetMapping("/list")
    public String list(Model model) throws Exception {
        model.addAttribute("members", memberService.findAll());
        return "list";
    }


}
