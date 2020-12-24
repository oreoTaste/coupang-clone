package clonecoder.springLover.controller;

import clonecoder.springLover.domain.Member;
import clonecoder.springLover.domain.Product;
import clonecoder.springLover.service.CartService;
import clonecoder.springLover.service.MemberService;
import clonecoder.springLover.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Controller
public class HomeController {
    private final MemberService memberService;
    private final ProductService productService;
    private final CartService cartService;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response) {
        request.getSession().invalidate();
        System.out.println("++++++++++++++++++++++++++");
        System.out.println("you entered /logout controller");
        System.out.println(request.getSession().getAttribute("login"));
        System.out.println("++++++++++++++++++++++++++++++++");
//        request.getSession().removeAttribute("id" );
//        request.getSession().removeAttribute("login" );
        return "redirect:/main";
    }


    @RequestMapping("/main")
    public String main(Member member,
                       Model model,
                       HttpServletRequest request,
                       HttpServletResponse response) throws Exception {
        List<Product> products = productService.findProducts(30);
        model.addAttribute("products", products);

        if(request.getSession().getAttribute("id") != null) {
            return afterLogin(request, response, (Long) request.getSession().getAttribute("id"));
        }

        if(member.getEmail() == null) {
            // 로그인 없이 진입할 경우 그냥 통과
            System.out.println();
            CookieForm.setTempIdToCookie(request, response);
            return "main";
        }

        Member emailMember = memberService.findByEmail(member.getEmail());
        if(emailMember.getPassword().equals(member.getPassword())) {
            request.getSession().setAttribute("id", emailMember.getId());
            request.getSession().setAttribute("login", 1);
            return afterLogin(request, response, emailMember.getId());

        } else {
            return "redirect:/";
        }
    }

    private String afterLogin(HttpServletRequest request,
                            HttpServletResponse response,
                            Long memberId) throws Exception{
        List<CookieForm> cartFromCookie = CookieForm.getCartFromCookieAndRemove(request, response);
        for(int i = 0; i < cartFromCookie.size(); i++) {
            cartService.save(memberId,
                    cartFromCookie.get(i).getProductId(),
                    cartFromCookie.get(i).getQuantity());
        }

        if(cartFromCookie.size() > 0) return "redirect:/cart/show";
        return "main";
    }

}
