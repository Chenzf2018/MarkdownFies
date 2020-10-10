package autowireinject;

public class StudentDAOImpl implements StudentDAO {
    @Override
    public void testAutowire(String name) {
        System.out.println("Test Autowired(StudentDAOImpl): " + name);
    }
}
