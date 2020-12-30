package clonecoder.springLover.controller;

import clonecoder.springLover.domain.*;
import clonecoder.springLover.service.CartService;
import clonecoder.springLover.service.MemberService;
import clonecoder.springLover.service.OrderService;
import clonecoder.springLover.service.ProductService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.core.util.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
public class HomeController {
    private final MemberService memberService;
    private final ProductService productService;
    private final CartService cartService;
    private final OrderService orderService;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/mycoupang")
    public String mycoupang(HttpServletRequest request,
                            Model model) {
        String email = (String) request.getSession().getAttribute("email");
        OrderSearch orderSearch = new OrderSearch();
        orderSearch.setMemberEmail(email);
        List<Order> orders = orderService.findOrders(orderSearch);
        model.addAttribute("orders", orders);
        return "order/list";
    }

    @GetMapping("/mycoupang/userModify")
    public String modifyUserInfo(HttpServletRequest request,
                                 Model model) {
        Member member = memberService.checkValidity(request);
        model.addAttribute("member", member);
        return "member/modify";
    }

    @PostMapping("/mycoupang/checkPassword")
    @ResponseBody
    public Object checkPassword(HttpServletRequest request) throws IOException {

        String json = IOUtils.toString(request.getReader());
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> map = new HashMap<>();
        map = mapper.readValue(json, new TypeReference<Map<String, String>>(){});
        Member member = memberService.checkValidity(request);

        String password = "";
        if(map.containsKey("password")) {
            password += map.get("password");
        }

        if(member.getPassword().equals(password)) {
            HashMap<String, String> resp = new HashMap<>();
            resp.put("answer", "OK");
            return resp;
        }
        return null;
    }

    @PostMapping("/mycoupang/userModify")
    public String modify(HttpServletRequest request,
                         Model model) {
        Member member = memberService.checkValidity(request);
        model.addAttribute("member", member);
        System.out.println("++++++++++++++++++++++++++++++");
        System.out.println("member/modifyForm");
        System.out.println("++++++++++++++++++++++++++++++");
        return "member/modifyForm";
    }


    @GetMapping("/mycoupang/cancel")
    public String myCancel(HttpServletRequest request,
                            Model model) {
        String email = (String) request.getSession().getAttribute("email");
        OrderSearch orderSearch = new OrderSearch();
        orderSearch.setMemberEmail(email);
        List<Order> orders = orderService.findOrders(orderSearch);
        List<Order> cancelList = orders.stream().filter((e) -> {
            return e.getStatus().equals(OrderStatus.CANCEL);
        }).collect(Collectors.toList());
        model.addAttribute("orders", cancelList);
        return "order/list";
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
        try {
            Member emailMember = memberService.findByEmail(member.getEmail());
            if(emailMember.getPassword().equals(member.getPassword())) {
                request.getSession().setAttribute("id", emailMember.getId());
                request.getSession().setAttribute("email", emailMember.getEmail());
                request.getSession().setAttribute("login", 1);
                return afterLogin(request, response, emailMember.getId());
            } else {
            return "redirect:/";
            }
        } catch (Exception e) {
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
