package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/index")
    public String toIndex() {
        return "/login";
    }

    @GetMapping("/toRegistController")
    public String toRegistController() {
        return "/regist";
    }

    @GetMapping("/employee/AddEmployee")
    public String addEmployee() {
        return "/addEmployee";
    }
}
