package com.nhnacademy.springbootmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        // TODO #3 적절한 thymeleaf view 반환
        return "/thymeleaf/index";
    }

    @GetMapping("/mustache")
    public String mustacheIndex(Model model) {
        model.addAttribute("message", "Hello World!");
        // TODO #4 적절한 mustache view 반환
        return "/mustache/index";
    }
}
