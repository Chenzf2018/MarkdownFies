package service;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestAOP {
    public static void main(String[] args) {
        // 启动工厂
        ApplicationContext context = new ClassPathXmlApplicationContext("service/spring.xml");
        EmpService service = (EmpService) context.getBean("empService");
        service.save("陈祖峰");
        // 没有切面：class service.EmpServiceImpl；有切面：class com.sun.proxy.$Proxy2
        System.out.println(service.getClass());
    }
}
