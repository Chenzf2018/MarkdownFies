package com.chenzf;

import com.chenzf.service.InterceptorService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestInterceptor {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("com/chenzf/spring.xml");
        InterceptorService service = (InterceptorService) context.getBean("interceptorService");
        System.out.println(service.getClass());
        service.find("chenzufeng");
    }
}
