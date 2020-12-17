package clonecoder.springLover.controller;

import clonecoder.springLover.domain.Member;
import clonecoder.springLover.domain.Product;
import clonecoder.springLover.service.MemberService;
import clonecoder.springLover.service.OrderService;
import clonecoder.springLover.service.ProductService;
import clonecoder.springLover.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class AddressController {

    private final MemberService memberService;

    @GetMapping("register/address")
    public String registerForm(HttpServletRequest request) {
        Long memberId = (Long) request.getSession().getAttribute("id");
        Member foundMember = memberService.findOne(memberId);
        if(foundMember == null) {
            throw new IllegalStateException("로그인 이후 주소를 등록할 수 있습니다.");
        }
        return "address/registerForm";
    }


}
