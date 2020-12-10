package clonecoder.springLover.controller;

import clonecoder.springLover.domain.Member;
import clonecoder.springLover.domain.Product;
import clonecoder.springLover.service.MemberService;
import clonecoder.springLover.service.ProductService;
import clonecoder.springLover.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.connector.Request;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final MemberService memberService;
    private final StorageService storageService;

    @GetMapping("register/product")
    public String registerForm(HttpServletRequest request) throws Exception {
        String email = (String) request.getSession().getAttribute("email");
        Member member = memberService.findByEmail(email);
        if(member == null) {
            throw new IllegalAccessException("유효하지 않은 접근입니다");
        }
        return "product/registerForm";
    }

    @PostMapping("register/product")
    public String register(@RequestParam("description") MultipartFile description,
                           @RequestParam("thumbnail") MultipartFile thumbnail,
                           @ModelAttribute() ProductForm productForm,
                           HttpServletRequest request
                           ) throws Exception {

        String key = "" + System.currentTimeMillis();
        storageService.store(description, key + "/description", request);
        storageService.store(thumbnail, key + "/thumbnail", request);
        Product product = new Product().create(productForm);
        product.setDescription(key + "/description");
        product.setThumbnail(key + "/thumbnail");
        productService.saveProduct(product);
        return "main";
    }
}
