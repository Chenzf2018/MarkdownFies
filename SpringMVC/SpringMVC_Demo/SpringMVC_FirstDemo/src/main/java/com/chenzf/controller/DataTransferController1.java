package com.chenzf.controller;

import com.chenzf.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 测试SpringMVC中数据传递机制
 */

@Controller
@RequestMapping("/DataTransferController1")
public class DataTransferController1 {

    /**
     * 使用forward跳转页面数据传递
     * @return dataTransfer.jsp
     */
    @RequestMapping("/testForwardDataTransfer1")
    public String testForwardDataTransfer(Model model) {
        // 1.收集参数
        // 2.调用业务方法——先直接存储String
        System.out.println("DataTransferController调用业务，存储数据：");

        String name = "陈祖峰";
        model.addAttribute("username", name);

        User user = new User("陈祖峰", 27, 20000.0, true, new Date());
        User user1 = new User("祖峰", 28, 30000.0, true, new Date());

        List<User> users = Arrays.asList(user, user1);

        model.addAttribute("user", user);
        model.addAttribute("users", users);

        // 3.流程跳转/响应处理
        return "dataTransfer";
    }

}