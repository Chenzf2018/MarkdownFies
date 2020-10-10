package com.chenzf.test;

import com.chenzf.dao.UserDAO;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestSqlSessionFactory {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) context.getBean("sqlSessionFactory");
        SqlSession sqlSession = sqlSessionFactory.openSession();

        System.out.println(sqlSession);

        UserDAO userDAO = sqlSession.getMapper(UserDAO.class);
        userDAO.findAllUser().forEach(user -> System.out.println(user));
    }
}
