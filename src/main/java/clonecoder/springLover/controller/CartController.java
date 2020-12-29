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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class CartController {

    private final ProductService productService;
    private final MemberService memberService;
    private final CartService cartService;

    @GetMapping("cart/count")
    @ResponseBody
    public int countMyCart(HttpServletRequest request, Model model) throws Exception {
        try {
            List<Cart> myCart = cartService.findMyCart(request);
            return myCart.size();
        } catch (Exception e) {
            List<CookieForm> cartFromCookie = CookieForm.getCartFromCookie(request);
            return cartFromCookie.size();
        }
    }

    @GetMapping("cart/show")
    @Transactional
    public String showMyCart(Model model,
                             HttpServletRequest request,
                             HttpServletResponse response) throws Exception {
        List<Cart> rocketList = new ArrayList<>();
        List<Cart> normalList = new ArrayList<>();
        Member member = null;

        if(request.getSession().getAttribute("id") != null){
            cartService.sortList(request, rocketList, normalList);
        } else {
            cartService.sortListFromCookie(request, rocketList, normalList);
        }
        model.addAttribute("rocketList", rocketList);
        model.addAttribute("normalList", normalList);
        return "cart/show";
    }

    @PostMapping("order/shoppingCart/id={productId}&quantity={quantity}")
    @ResponseBody
    public String addToShoppingCart(@PathVariable Long productId,
                                    @PathVariable int quantity,
                                    HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {
        try {
            cartService.save(request, productId, quantity);
            return "OK";
        } catch(Exception e) {
            CookieForm.setCartToCookie(request, response, productId, quantity);
            return "OK";
        }
    }

    @GetMapping("cart/delete/{productIds}")
    @ResponseBody
    public String deleteShoppingCart(@PathVariable String productIds,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws Exception {

        List<Long> productIdList = Arrays.stream(productIds.split(",")).map((e) -> {
            return Long.parseLong(e);
        }).collect(Collectors.toList());

        if(request.getSession().getAttribute("id") != null){
            cartService.clear(request, productIdList);
        } else {
            cartService.clearCookie(request.getCookies(), productIdList, request, response);
        }
        return "OK";
    }

    @PostMapping("cart/adjust/id={productId}&quantity={quantity}")
    @ResponseBody
    public String adjustCart(@PathVariable Long productId,
                             @PathVariable int quantity,
                             HttpServletRequest request,
                             HttpServletResponse response) throws Exception {

        try {
            if(cartService.adjust(request, productId, quantity)) {
                return "OK";
            }
        } catch (Exception e) {
            CookieForm.adjustCartToCookie(request, response, productId, quantity);
            return "OK";
        }
        return "Not OK";
    }

}
