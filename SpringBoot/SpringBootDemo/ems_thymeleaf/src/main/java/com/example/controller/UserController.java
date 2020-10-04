package com.example.controller;

import com.example.entity.User;
import com.example.service.UserService;
import com.example.utils.ValidateImageCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Controller
@RequestMapping("/chenzf")

public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 登录页面
     * @param username
     * @param userpassword
     * @return
     */
    @PostMapping("/login")
    public String login(String username, String userpassword) {
        User login = userService.login(username, userpassword);
        if (login != null) {
            // 跳转到员工列表界面
            return "redirect:/employee/findAllEmployee";
        } else {
            // 跳转到登录页面
            return "redirect:/index";
        }
    }

    /**
     * 注册
     * @param user
     * @return 跳转至登录页面或注册页面
     */
    @PostMapping("/regist")
    public String regist(User user, String validateCode, HttpSession session) {
        String sessionCode = (String) session.getAttribute("validateCode");
        if (sessionCode.equalsIgnoreCase(validateCode)) {
            userService.regist(user);
            // 跳转至登录页面
            return "redirect:/index";
        } else {
            // 跳转至控制器再跳转到注册页面
            return "redirect:/toRegistController";
        }
    }


    /**
     * 生成验证码
     * @param session
     * @param response
     * @throws IOException
     */
    @GetMapping("/validateCode")
    public void getImage(HttpSession session, HttpServletResponse response) throws IOException {
        String validateCode = ValidateImageCodeUtils.getSecurityCode();
        BufferedImage image = ValidateImageCodeUtils.createImage(validateCode);
        // 存入session作用域
        session.setAttribute("validateCode", validateCode);
        // 响应图片
        ServletOutputStream outputStream = response.getOutputStream();
        ImageIO.write(image, "png", outputStream);
    }
}
