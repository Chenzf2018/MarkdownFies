package autowireinject;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestAutowire {
    public static void main(String[] args) {
        // 启动工厂
        ApplicationContext context = new ClassPathXmlApplicationContext("autowireinject/spring.xml");
        // 获取对象
        StudentService service = (StudentService) context.getBean("studentService");
        service.testAutowire("czf");
    }
}
