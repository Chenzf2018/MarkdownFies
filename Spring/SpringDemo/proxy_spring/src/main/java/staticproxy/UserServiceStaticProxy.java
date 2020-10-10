package staticproxy;

// 代理对象

public class UserServiceStaticProxy implements UserService {

    // 代理类依赖于目标类对象、原始业务逻辑对象

    private UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    // 代理类和目标类功能一致且实现相同的接口

    @Override
    public void save(String name) {
        try {
            System.out.println("开启事务");
            // System.out.println("处理业务逻辑,调用DAO~~~");

            // 调用原始业务逻辑对象的方法
            userService.save(name);

            System.out.println("提交事务");
        } catch (Exception e) {
            System.out.println("回滚事务");
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String id) {
        try {
            System.out.println("开启事务");
            // System.out.println("处理业务逻辑,调用DAO~~~");

            // 调用原始业务逻辑对象的方法
            userService.delete(id);

            System.out.println("提交事务");
        } catch (Exception e) {
            System.out.println("回滚事务");
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
        try {
            System.out.println("开启事务");
            // System.out.println("处理业务逻辑,调用DAO~~~");

            // 调用原始业务逻辑对象的方法
            userService.update();

            System.out.println("提交事务");
        } catch (Exception e) {
            System.out.println("回滚事务");
            e.printStackTrace();
        }
    }

    @Override
    public String findAll(String name) {
        try {
            System.out.println("开启事务");
            // System.out.println("处理业务逻辑,调用DAO~~~");

            // 调用原始业务逻辑对象的方法
            String all = userService.findAll(name);

            System.out.println("提交事务");

            return all;
        } catch (Exception e) {
            System.out.println("回滚事务");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String findOne(String id) {
        try {
            System.out.println("开启事务");
            // System.out.println("处理业务逻辑,调用DAO~~~");

            // 调用原始业务逻辑对象的方法
            String one = userService.findOne(id);

            System.out.println("提交事务");

            return one;
        } catch (Exception e) {
            System.out.println("回滚事务");
            e.printStackTrace();
        }
        return null;
    }
}
