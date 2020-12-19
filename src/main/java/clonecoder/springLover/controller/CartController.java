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
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CartController {

    private final ProductService productService;
    private final MemberService memberService;
    private final CartService cartService;

    @GetMapping("cart/show")
    public String showMyCart(HttpServletRequest request, Model model) throws Exception {
        Member member = memberService.checkValidity(request);
        List<Cart> myCart = cartService.findMyCart(member.getId());
        model.addAttribute("myCart", myCart);
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

}
