package com.chenzf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 测试forward和redirect跳转
 */

@Controller
@RequestMapping("/ForwardAndRedirectController")

public class ForwardAndRedirectController {

    /**
     * 测试forward跳转到页面
     * controller跳转到jsp页面就是forward跳转
     * @return 返回页面逻辑名
     */
    @RequestMapping("/testForward")
    public String testForward() {
        System.out.println("测试forward跳转到index.jsp");
        return "index";
    }

    /**
     * 测试fredirct跳转到页面
     * @return 返回页面全名
     */
    @RequestMapping("/testRedirect")
    public String testRedirect() {
        System.out.println("测试redirect跳转到index.jsp");
        return "redirect:/index.jsp";
    }
}
