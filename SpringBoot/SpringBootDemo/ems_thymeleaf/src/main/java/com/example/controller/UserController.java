package com.example.controller;

import com.example.utils.ValidateImageCodeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/validateCode")
    public void getImage(HttpSession session, HttpServletResponse response) throws IOException {
        // 生成验证码
        String validateCode = ValidateImageCodeUtils.getSecurityCode();
        BufferedImage image = ValidateImageCodeUtils.createImage(validateCode);
        // 存入session作用域
        session.setAttribute("validateCode", validateCode);
        // 响应图片
        ServletOutputStream outputStream = response.getOutputStream();
        ImageIO.write(image, "png", outputStream);
    }
}
