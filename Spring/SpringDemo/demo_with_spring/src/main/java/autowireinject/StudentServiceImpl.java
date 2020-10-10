package autowireinject;

public class StudentServiceImpl implements StudentService {

    /**
     * 自动注入
     */
    private StudentDAO studentDAO;

    public void setStudentDAO(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }

    @Override
    public void testAutowire(String name) {
        System.out.println("Test Autowired(StudentServiceImpl): " + name);
        studentDAO.testAutowire(name);
    }
}
