package clonecoder.springLover.controller;

import clonecoder.springLover.domain.Cart;
import clonecoder.springLover.domain.Member;
import clonecoder.springLover.domain.Product;
import clonecoder.springLover.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

@Controller
@RequiredArgsConstructor
public class CartController {

    private final ProductService productService;
    private final MemberService memberService;
    private final CartService cartService;

    @GetMapping("cart/count")
    @ResponseBody
    public int countMyCart(HttpServletRequest request, Model model) throws Exception {
        Member member = memberService.checkValidity(request);
        List<Cart> myCart = cartService.findMyCart(member.getId());
        return myCart.size();
    }

    @GetMapping("cart/show")
    public String showMyCart(HttpServletRequest request, Model model) throws Exception {
        Member member = memberService.checkValidity(request);
        List<Cart> myCart = cartService.findMyCart(member.getId());

        List<Cart> rocketList = new ArrayList<>();
        List<Cart> normalList = new ArrayList<>();
        for(Cart cart : myCart) {
            if(cart.getProduct().getIs_rocket().equals("on")) {
                rocketList.add(cart);
            } else {
                normalList.add(cart);
            }
        }
        model.addAttribute("rocketList", rocketList);
        model.addAttribute("normalList", normalList);
        return "cart/show";
    }

    @PostMapping("order/shoppingCart/id={productId}&quantity={quantity}")
    @ResponseBody
    public String addToShoppingCart(@PathVariable Long productId,
                                    @PathVariable int quantity,
                                    HttpServletRequest request) throws Exception {

        Member member = memberService.checkValidity(request);

        cartService.save(member.getId(), productId, quantity);
        return "OK";
    }

    @PostMapping("cart/adjust/id={productId}&quantity={quantity}")
    @ResponseBody
    public String adjustCart(@PathVariable Long productId,
                             @PathVariable int quantity,
                             HttpServletRequest request) throws Exception {

        Member member = memberService.checkValidity(request);
        List<Cart> myCart = cartService.findMyCart(member.getId());
        for(Cart cart : myCart) {
            if(cart.getProduct().getId() == productId) {
                cartService.adjust(member.getId(), productId, quantity);
                return "OK";
            }
        }
        return "Not OK";
    }

}
