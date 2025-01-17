package com.chenzf.controller;

import com.chenzf.entity.User;
import com.chenzf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/UserController")

public class UserController {

    /**
     * findAllUser方法需要调用userService对象
     */
    @Autowired
    private UserService userService;

    @RequestMapping("/findAllUser")
    public String findAllUser(HttpServletRequest request) {
        // 1. 收集数据
        // 2. 调用业务对象
        List<User> users = userService.findAllUser();
        // 3. 存储数据并跳转显示页面
        request.setAttribute("users", users);
        return "findAllUser";
    }

    /**
     * 添加用户
     * @param user 添加的对象
     * @return 没有异常则跳转至findAllUser方法
     */
    @RequestMapping("/saveUser")
    public String saveUser(User user) {
        //2.调用业务方法
        try {
            userService.saveUser(user);
            return "redirect:/UserController/findAllUser";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/addUser.jsp";
        }
    }
}
