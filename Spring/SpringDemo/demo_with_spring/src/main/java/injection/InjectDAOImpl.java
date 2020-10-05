package injection;

public class InjectDAOImpl implements InjectDAO {
    @Override
    public void testInject(String string) {
        System.out.println("Test Injection(InjectDAO): " + string);
    }
}
