package di;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestSpringDI {
    public static void main(String[] args) {
        // 启动工厂
        ApplicationContext context = new ClassPathXmlApplicationContext("/di/springDI.xml");

        // 获取service组件
        DeptService deptService = (DeptService) context.getBean("deptService");
        deptService.save("chenzufeng");
    }
}
