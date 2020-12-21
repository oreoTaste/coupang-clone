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

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Controller
public class HomeController {
    private final MemberService memberService;
    private final ProductService productService;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @RequestMapping("/main")
    public String main(Member member, Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<Product> products = productService.findProducts(30);
        model.addAttribute("products", products);

        if(request.getSession().getAttribute("id") == null) {
            if(member.getEmail() == null) {
                // 로그인 없이 진입할 경우 그냥 통과
                CookieForm.setTempIdToCookie(request, response);
                return "main";
            }
            // 로그인 한 경우 session에 값추가
            Member realMember = memberService.findByEmail(member.getEmail());
            request.getSession().setAttribute("id", realMember.getId());
            request.getSession().setAttribute("email", realMember.getEmail());
        }
        return "main";
    }

}
