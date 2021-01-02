package clonecoder.springLover.controller;

import clonecoder.springLover.domain.*;
import clonecoder.springLover.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class ReviewController {

    private final ProductService productService;
    private final MemberService memberService;
    private final OrderService orderService;
    private final OrderProductService orderProductService;
    private final StorageService storageService;
    private final ReviewService reviewService;

    // 구매후기 목록
    @GetMapping("productreview/reviewable")
    public String productReviewForm(HttpServletRequest request,
                                    Model model) {

        List<OrderProduct> orderProductList = productService.findMyProduct(request);
        model.addAttribute("orderProductList", orderProductList);

        Member member = memberService.checkValidity(request);
        model.addAttribute("member", member);

        List<Review> reviews = orderProductList.stream().map((e) ->
                e.getReview()).collect(Collectors.toList());
        model.addAttribute("reviews", reviews);
        return "product/review";
    }

    // 구매후기 작성 폼
    @GetMapping("productreview/register/{orderProductId}")
    public String productReviewRegisterForm(@PathVariable Long orderProductId,
                                            HttpServletRequest request,
                                            Model model) {

        model.addAttribute("member", memberService.checkValidity(request));
        model.addAttribute("orderProduct", orderProductService.findOne(orderProductId));
        return "product/reviewForm";
    }

    // 구매후기 작성 후 입력
    @PostMapping("productreview/register/{orderProductId}")
    public String productReviewRegister(@PathVariable Long orderProductId,
                                        @RequestParam("photo") MultipartFile photo,
                                        Review review,
                                        HttpServletRequest request,
                                        Model model) throws Exception {

        Review registeredReview = reviewService.register(orderProductId, photo, review, request);
        model.addAttribute("review", registeredReview);

        return "redirect:/productreview/reviewable";
    }


}
