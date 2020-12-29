package clonecoder.springLover.controller;

import clonecoder.springLover.domain.Cart;
import clonecoder.springLover.domain.DeliveryStatus;
import clonecoder.springLover.domain.Member;
import clonecoder.springLover.domain.Order;
import clonecoder.springLover.service.DeliveryService;
import clonecoder.springLover.service.MemberService;
import clonecoder.springLover.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;
    private final OrderService orderService;
    private final MemberService memberService;

    @GetMapping("delivery/count")
    @ResponseBody
    public int countNotDelivered(HttpServletRequest request, Model model) throws Exception {
        return deliveryService.countCurrentDeliveries(request);
    }

    @GetMapping("deliveryStatus/{orderId}")
    public String deliveryStatus(@PathVariable Long orderId,
                                 HttpServletRequest request,
                                 Model model) throws Exception {
        memberService.checkValidity(request);
        Order foundOrder = orderService.findOne(orderId);
        model.addAttribute("order", foundOrder);
        return "delivery/show";
    }

}
