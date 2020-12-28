package clonecoder.springLover.controller;

import clonecoder.springLover.domain.Cart;
import clonecoder.springLover.domain.DeliveryStatus;
import clonecoder.springLover.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;

    @GetMapping("delivery/count")
    @ResponseBody
    public int countNotDelivered(HttpServletRequest request, Model model) throws Exception {
        return deliveryService.countCurrentDeliveries(request);
    }

}
