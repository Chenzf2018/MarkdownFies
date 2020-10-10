package constructorInjection;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TetsConstructorInjection {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("/constructorInjection/spring.xml");
        ConstructorInjectionDAO dao = (ConstructorInjectionDAO) context.getBean("constructorInjectionDAO");
        dao.testConstructorInjection("chenzufeng");
    }
}
