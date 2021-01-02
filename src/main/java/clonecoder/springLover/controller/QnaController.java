package clonecoder.springLover.controller;

import clonecoder.springLover.service.*;
import clonecoder.springLover.util.JsonToString;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class QnaController {

    private final MemberService memberService;
    private final QnaService qnaService;
    private final ProductService productService;

    // 결제 확인후 오더접수(출고전)
    @PostMapping("qna/register")
    @Transactional
    @ResponseBody
    public HashMap<String, String> registerQna(HttpServletRequest request,
                                               Model model) throws Exception {

        Map<String, String> map = JsonToString.getJsonStringMap(request);
        String comment = map.get("comment");
        Long productId = Long.parseLong(map.get("productId"));

        qnaService.registerQna(request, comment, productId);

        HashMap<String, String> resp = new HashMap<>();
        resp.put("answer", "OK");
        return resp;

    }

}
