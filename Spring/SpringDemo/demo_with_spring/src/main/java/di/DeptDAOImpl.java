package di;

public class DeptDAOImpl implements DeptDAO {
    @Override
    public void save(String name) {
        System.out.println("验证依赖注入(DeptDAOImpl)：name = " + name);
    }
}
