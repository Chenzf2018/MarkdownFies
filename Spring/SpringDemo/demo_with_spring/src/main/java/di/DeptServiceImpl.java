package di;

public class DeptServiceImpl implements DeptService {

    // 依赖DAO组件
    private DeptDAO deptDAO;

    // 公开的Set方法
    public void setDeptDAO(DeptDAO deptDAO) {
        this.deptDAO = deptDAO;
    }

    @Override
    public void save(String name) {
        System.out.println("验证依赖注入(DeptServiceImpl)：name = " + name);
        deptDAO.save(name);
    }
}
