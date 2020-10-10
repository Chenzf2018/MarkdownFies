package com.chenzf.test;

import com.chenzf.dao.UserDAO;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestUserDAO {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        UserDAO userDAO = (UserDAO) context.getBean("userDAO");
        userDAO.findAllUser().forEach(user -> System.out.println(user));
    }
}
