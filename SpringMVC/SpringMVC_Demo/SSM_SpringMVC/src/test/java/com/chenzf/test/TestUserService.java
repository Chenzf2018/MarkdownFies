package com.chenzf.test;

import com.chenzf.service.UserService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestUserService {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        UserService userService = (UserService) context.getBean("userService");
        userService.findAllUser().forEach(user -> System.out.println("user: " + user));
    }
}
