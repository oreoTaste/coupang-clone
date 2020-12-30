package clonecoder.springLover.controller;

import clonecoder.springLover.domain.*;
import clonecoder.springLover.service.CartService;
import clonecoder.springLover.service.MemberService;
import clonecoder.springLover.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final ProductService productService;
    private final MemberService memberService;
    private final CartService cartService;

    @GetMapping("productreview/reviewable")
    public String productReviewForm(HttpServletRequest request,
                                    Model model) {

        model.addAttribute("myProduct", productService.findMyProduct(request));
        model.addAttribute("member", memberService.checkValidity(request));
        return "product/review";
    }



}
