package singleton;

        import org.springframework.context.ApplicationContext;
        import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestSingleton {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        // 启动工厂
        ApplicationContext context = new ClassPathXmlApplicationContext("singleton/spring.xml");
        SingletonDAO singletonDAO = (SingletonDAO) context.getBean("singletonDAO");

        // 关闭工厂
        ((ClassPathXmlApplicationContext) context).close();
    }
}
