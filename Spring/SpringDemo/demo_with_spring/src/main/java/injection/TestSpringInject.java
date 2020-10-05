package injection;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestSpringInject {
    public static void main(String[] args) {
        // 启动工厂
        ApplicationContext context = new ClassPathXmlApplicationContext("injection/applicationContext.xml");
        // 获取对象
        InjectService injectService = (InjectService) context.getBean("injectService");
        injectService.testInject("Inject Successfully");
    }
}
