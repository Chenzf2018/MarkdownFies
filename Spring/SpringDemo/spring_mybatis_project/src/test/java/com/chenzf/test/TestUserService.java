package com.chenzf.test;

import com.chenzf.entity.User;
import com.chenzf.service.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;

public class TestUserService {

    private ClassPathXmlApplicationContext context;

    @Before
    public void before() {
        this.context = new ClassPathXmlApplicationContext("spring.xml");
    }
    
    @Test
    public void testSaveUser() {
        UserService userService = (UserService) context.getBean("userService");
        User user = new User();
        user.setBirth(new Date());
        user.setAge(20);
        user.setName("祖峰");
        userService.saveUser(user);
    }

    @Test
    public void testFindAllUser() {
        UserService userService = (UserService) context.getBean("userService");
        userService.findAllUser().forEach(user -> System.out.println("user = " + user));
    }

    @After
    public void after() {
        context.close();
    }
}
