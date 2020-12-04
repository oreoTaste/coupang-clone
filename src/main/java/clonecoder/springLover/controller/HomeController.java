package clonecoder.springLover.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home() {
        return "index";
    }

    @RequestMapping("/main")
    public String main(HttpServletRequest request) {
        System.out.println("main으로 진입!!");
        return "main";
    }

}
