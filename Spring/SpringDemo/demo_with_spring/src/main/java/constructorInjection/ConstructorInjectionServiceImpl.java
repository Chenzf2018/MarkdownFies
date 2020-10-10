package constructorInjection;

public class ConstructorInjectionServiceImpl implements ConstructorInjectionService {
    @Override
    public void testConstructorInjection(String inputString) {
        System.out.println("TestConstructorInjection(ConstructorInjectionService): " + inputString);
    }
}
