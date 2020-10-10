package staticproxy;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

// 业务逻辑调用者

public class TestStaticProxy {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("staticproxy/spring.xml");
        UserService userService = (UserService) context.getBean("userServiceStaticProxy");
        userService.findAll("chenzufeng");
    }
}
