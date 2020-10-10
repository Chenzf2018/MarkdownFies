package singleton;

public class SingletonDAOImpl implements SingletonDAO {
    @Override
    public void testSingleton(String name) {
        System.out.println("Test Singleton(SingletonDAOImpl): name = " + name);
    }

    // init-method
    public void initMethod() {
        System.out.println("组件对象初始化");
    }

    // destroy-method
    public void destroyMethod() {
        System.out.println("组件对象销毁");
    }
}
