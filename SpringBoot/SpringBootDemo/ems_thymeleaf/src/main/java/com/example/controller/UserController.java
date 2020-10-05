package com.example.controller;

import com.example.entity.User;
import com.example.service.UserService;
import com.example.utils.ValidateImageCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Controller
@RequestMapping("/chenzf")
// 在当前类直接声明日志对象
@Slf4j  // lombok.extern.slf4j

public class UserController {

    // 声明日志对象
    // private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    /**
     * 测试logback
     * @param id
     * @return
     */
    @ResponseBody
    @GetMapping("/testLogback")
    public String testLogback(String id) {
        // System.out.println("测试logback：id = " + id);
        log.info("INFO信息：id = " + id);
        log.debug("DEBUG信息：id = " + id);
        return id;
    }

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

    /**
     * 测试热部署
     * @param id
     * @return
     */
    @ResponseBody
    @GetMapping("/testDevTools")
    public String testDevTools(String id) {
        System.out.println("开启热部署：id = " + id);
        return id;
    }
}
