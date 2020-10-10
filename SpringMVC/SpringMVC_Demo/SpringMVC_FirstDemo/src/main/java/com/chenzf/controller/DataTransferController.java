package com.chenzf.controller;

import com.chenzf.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 测试SpringMVC中数据传递机制
 */

@Controller
@RequestMapping("/DataTransferController")
public class DataTransferController {

    /**
     * 使用forward跳转页面数据传递
     * @param request 当前请求对象
     * @param response 响应对象
     * @return dataTransfer.jsp
     */
    @RequestMapping("/testForwardDataTransfer")
    public String testForwardDataTransfer(HttpServletRequest request, HttpServletResponse response) {
        // 1.收集参数
        // 2.调用业务方法——先直接存储String
        System.out.println("DataTransferController调用业务，存储数据：");
        String name = "陈祖峰";
        // 存储数据
        request.setAttribute("username", name);

        User user = new User("陈祖峰", 27, 20000.0, true, new Date());
        User user1 = new User("祖峰", 27, 20000.0, true, new Date());

        List<User> users = Arrays.asList(user, user1);
        request.setAttribute("users", users);

        request.setAttribute("user", user);
        // 3.流程跳转/响应处理
        return "dataTransfer";
    }

    /**
     * 使用redirect跳转页面数据传递
     * @return dataTransfer.jsp
     */
    @RequestMapping("/testRedirectDataTransfer")
    public String testRedirectDataTransfer(HttpServletRequest request) throws UnsupportedEncodingException {
        // 1.收集参数
        // 2.调用业务方法
        System.out.println("DataTransferController调用业务，存储数据：");
        String name = "陈祖峰";
        User user2 = new User("陈祖峰", 27, 20000.0, true, new Date());
        request.getSession().setAttribute("user", user2);
        // 3.流程跳转/响应处理
        return "redirect:/dataTransfer.jsp?name=" + URLEncoder.encode(name, "UTF-8");
    }

}