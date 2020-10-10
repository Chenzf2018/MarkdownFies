package constructorInjection;

public class ConstructorInjectionDAOImpl implements ConstructorInjectionDAO {

    /**
     * 构造注入
     */
    private String inputString;
    private Integer age;

    public ConstructorInjectionDAOImpl() {}

    public ConstructorInjectionDAOImpl(String inputString) {
        this.inputString = inputString;
    }

    public ConstructorInjectionDAOImpl(String inputString, Integer age) {
        this.inputString = inputString;
        this.age = age;
    }

    @Override
    public void testConstructorInjection(String inputString) {
        System.out.println("TestConstructorInjection(ConstructorInjectionDAOImpl): inputString = " + inputString);
        System.out.println("TestConstructorInjection(ConstructorInjectionDAOImpl): this.inputString = " + this.inputString);
        System.out.println("TestConstructorInjection(ConstructorInjectionDAOImpl): age = " + age);
    }
}
