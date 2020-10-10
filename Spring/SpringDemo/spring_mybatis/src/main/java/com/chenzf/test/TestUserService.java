package com.chenzf.test;

import com.chenzf.entity.User;
import com.chenzf.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;

public class TestUserService {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        UserService userService = (UserService) context.getBean("userService");

        // 先保存用户
        userService.saveUser(new User("", "zufeng", 1, new Date()));
        // 再查看用户
        userService.findAllUser().forEach(user -> System.out.println(user));
    }
}
