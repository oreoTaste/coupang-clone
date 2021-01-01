package clonecoder.springLover.controller;

import clonecoder.springLover.domain.Address;
import clonecoder.springLover.domain.Member;
import clonecoder.springLover.domain.Order;
import clonecoder.springLover.domain.Product;
import clonecoder.springLover.service.*;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.connector.Request;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final OrderService orderService;
    private final MemberService memberService;
    private final StorageService storageService;
    private final AddressService addressService;

    @GetMapping("register/product")
    public String registerForm(HttpServletRequest request) throws Exception {
        Long id = (Long) request.getSession().getAttribute("id");
        Member member = memberService.findOne(id);
        if (member == null) {
            throw new IllegalAccessException("유효한 회원만 상품을 등록할 수 있습니다.");
        }
        return "product/registerForm";
    }

    @PostMapping("register/product")
    @Transactional
    public String register(@RequestParam("description") MultipartFile description,
                           @RequestParam("thumbnail") MultipartFile thumbnail,
                           @ModelAttribute() ProductForm productForm,
                           HttpServletRequest request) throws Exception {

        productService.register(description, thumbnail, productForm, request);
        return "redirect:../main";
    }

    @GetMapping("product/{id}")
    public String productDetail(@PathVariable Long id, HttpServletRequest request, Model model) throws Exception {
        Product product = productService.findOne(id);
        model.addAttribute("product", product);
        return "product/detail";
    }

    @GetMapping("search")
    public String search(String keyword,
                         HttpServletRequest request,
                         Model model) throws Exception {
        Long count = productService.countByKeyword(keyword);
        List<Product> products = productService.findByKeyword(keyword);
        model.addAttribute("count", count);
        model.addAttribute("keyword", keyword);
        model.addAttribute("products", products);
        return "product/searchList";
    }

}
