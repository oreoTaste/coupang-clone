package clonecoder.springLover.controller;

import clonecoder.springLover.domain.Cart;
import clonecoder.springLover.domain.Member;
import clonecoder.springLover.domain.Product;
import clonecoder.springLover.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

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
            Member member = memberService.checkValidity(request);
            List<Cart> myCart = cartService.findMyCart(member.getId());
            return myCart.size();
        } catch (Exception e) {
            List<CookieForm> cartFromCookie = CookieForm.getCartFromCookie(request);
            return cartFromCookie.size();
        }
    }

    @GetMapping("cart/show")
    public String showMyCart(Model model,
                             HttpServletRequest request,
                             HttpServletResponse response) throws Exception {
        List<Cart> rocketList = new ArrayList<>();
        List<Cart> normalList = new ArrayList<>();
        Member member = null;
        try {
            member = memberService.checkValidity(request);
            List<Cart> myCart = cartService.findMyCart(member.getId());

            for(Cart cart : myCart) {
                if(cart.getProduct().getIs_rocket().equals("on")) {
                    rocketList.add(cart);
                } else {
                    normalList.add(cart);
                }
            }
            model.addAttribute("rocketList", rocketList);
            model.addAttribute("normalList", normalList);

        } catch (Exception e) {
            String tempIdFromCookie = CookieForm.getTempIdFromCookie(request);
            List<CookieForm> cartFromCookie = CookieForm.getCartFromCookie(request);

            if(cartFromCookie.get(0).getTempId().equals(tempIdFromCookie)) {
                for(CookieForm cookieForm : cartFromCookie) {

                    Product product = productService.findOne(cookieForm.getProductId());
                    int quantity = cookieForm.getQuantity();
                    Cart cart = new Cart();
                    cart.setProduct(product);
                    cart.setCount(quantity);
                    if(product.getIs_rocket().equals("on")) {
                        rocketList.add(cart);
                    } else {
                        normalList.add(cart);
                    }
                }
                model.addAttribute("rocketList", rocketList);
                model.addAttribute("normalList", normalList);
            }
        } finally {
            return "cart/show";
        }
    }

    @PostMapping("order/shoppingCart/id={productId}&quantity={quantity}")
    @ResponseBody
    public String addToShoppingCart(@PathVariable Long productId,
                                    @PathVariable int quantity,
                                    HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {

        try {
            Member member = memberService.checkValidity(request);

                cartService.save(member.getId(), productId, quantity);
                return "OK";
            } catch(Exception e) {
                CookieForm.setCartToCookie(request, response, productId, quantity);
                return "OK";
        }
    }

    @PostMapping("cart/adjust/id={productId}&quantity={quantity}")
    @ResponseBody
    public String adjustCart(@PathVariable Long productId,
                             @PathVariable int quantity,
                             HttpServletRequest request,
                             HttpServletResponse response) throws Exception {

        try {
            Member member = memberService.checkValidity(request);
            List<Cart> myCart = cartService.findMyCart(member.getId());
            for (Cart cart : myCart) {
                if (cart.getProduct().getId() == productId) {
                    cartService.adjust(member.getId(), productId, quantity);
                    return "OK";
                }
            }
        } catch (Exception e) {
            CookieForm.adjustCartToCookie(request, response, productId, quantity);
            return "OK";
        }
        return "Not OK";
    }

}
