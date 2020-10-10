package afterreturningadvice;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestAfterAdvice {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("afterreturningadvice/spring.xml");
        AfterAdviceService service = (AfterAdviceService) context.getBean("afterAdviceService");
        System.out.println(service.getClass());
        System.out.println("========先执行目标方法========");
        service.save("chenzufeng");
    }
}
