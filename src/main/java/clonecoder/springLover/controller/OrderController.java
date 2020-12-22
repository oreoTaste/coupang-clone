package clonecoder.springLover.controller;

import clonecoder.springLover.domain.Address;
import clonecoder.springLover.domain.Member;
import clonecoder.springLover.domain.Order;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final ProductService productService;
    private final OrderService orderService;
    private final MemberService memberService;
    private final AddressService addressService;

    @GetMapping("product/checkout/productId={productId}&count={count}")
    public String checkoutForm(@PathVariable String productId,
                           @PathVariable String count,
                           Model model,
                           HttpServletRequest request) {
        Long memberId = (Long) request.getSession().getAttribute("id");
        Member member = memberService.findOne(memberId);
        model.addAttribute("member", member);

        List<Product> productList = new ArrayList<>();
        List<Integer> countList = new ArrayList<>();

        String[] splitProductId = productId.split(",");
        for(String id : splitProductId) {
            Product product = productService.findOne(Long.parseLong(id));
            productList.add(product);
        }
        model.addAttribute("productList", productList);

        String[] splitCount = count.split(",");
        for(String cnt : splitCount) {
            countList.add(Integer.parseInt(cnt));
        }
        model.addAttribute("countList", countList);
        return "order/directCheckout";
    }

    @PostMapping("checkout")
    @Transactional
    public String checkout(Long addressId,
                           Long productId,
                           int count,
                           HttpServletRequest request,
                           Model model) throws Exception {

//        Member member = memberService.checkValidity(request);
//        Address address = addressService.getAddress(addressId);
//        System.out.println(addressId);
//        System.out.println(productId);
//        System.out.println(count);
//        System.out.println(member);
//        Long orderId = orderService.checkout(member.getId(), productId, count, address);
//        Order order = orderService.findOne(orderId);
//        model.addAttribute("order", order);
        return "OK";
    }
    @GetMapping("checkout/after")
    @Transactional
    public String checkoutAfter(Long addressId,
                                Long productId,
                                int count,
                                HttpServletRequest request,
                                Model model) throws Exception {


        return "order/after";
    }

}
