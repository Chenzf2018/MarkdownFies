package factorybean;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Calendar;

public class Test {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("factorybean/spring.xml");
        Calendar calendar = (Calendar) context.getBean("calendar");
        Calendar calendar1 = (Calendar) context.getBean("calendar");
        System.out.println("验证是否为单例模式创建对象：" + (calendar == calendar1));
        System.out.println(calendar.getTime());
    }
}
