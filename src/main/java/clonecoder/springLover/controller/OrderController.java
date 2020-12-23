package clonecoder.springLover.controller;

import clonecoder.springLover.domain.*;
import clonecoder.springLover.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final ProductService productService;
    private final OrderService orderService;
    private final MemberService memberService;
    private final AddressService addressService;
    private final CartService cartService;

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
    @ResponseBody
    public String checkout(HttpServletRequest request,
                           Model model) throws Exception {
        String exAddressId = request.getParameter("exAddressId");
        String exProductId = request.getParameter("exProductId");

        System.out.println("exAddressId: " + exAddressId);

        List<Long> idList = new ArrayList<>();
        List<Integer> cntList = new ArrayList<>();
        String[] split = exProductId.split(",");
        for(String part : split) {
            idList.add(Long.parseLong(part.split(":")[0]));
            cntList.add(Integer.parseInt(part.split(":")[1]));
        }

        Member member = memberService.checkValidity(request);
        Address address = addressService.getAddress(Long.parseLong(exAddressId));
        Long orderId = orderService.checkout(member.getId(), idList, cntList, address);
        return orderId.toString();
    }
    @GetMapping("checkout/after/{orderId}")
    @Transactional
    public String checkoutAfter(@PathVariable Long orderId,
                                HttpServletRequest request,
                                Model model) throws Exception {

        Member member = memberService.checkValidity(request);
        Order order = orderService.findOne(orderId);
        model.addAttribute("order", order);

        Stream<Long> productIdStream = order.getOrderProductList().stream().map((e) -> {
            return e.getProduct().getId();
        });
        List<Long> productIdList = productIdStream.collect(Collectors.toList());

        cartService.clear(member, productIdList);
        return "order/after";
    }

}
