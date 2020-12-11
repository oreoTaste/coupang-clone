package clonecoder.springLover.controller;

import clonecoder.springLover.domain.Member;
import clonecoder.springLover.domain.Product;
import clonecoder.springLover.service.MemberService;
import clonecoder.springLover.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class HomeController {
    private final MemberService memberService;
    private final ProductService productService;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @RequestMapping("login")
    public String login(Member member, Model model, HttpServletRequest request) throws Exception {
        // 다른 페이지에서 넘어온 경우(member값이 없는 경우) : member 채워주기
        if(member.getEmail() == null) {
            Long id = (Long) request.getSession().getAttribute("id");
            member = memberService.findOne(id);
        }
        if(!memberService.login(member.getEmail(), member.getPassword())){
            throw new IllegalAccessException("등록되지 않은 회원입니다");
        }
        memberService.findByEmail(member.getEmail()).getId();
        request.getSession().setAttribute("id", memberService.findByEmail(member.getEmail()).getId());
        List<Product> products = productService.findProducts();
        model.addAttribute("products", products);
        return "main";
    }

}
