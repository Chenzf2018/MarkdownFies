package init;

public class TestUserDAO {
    public static void main(String[] args) {
        // 创建组件对象
        UserDAOImpl user = new UserDAOImpl();

        // 使用组件
        user.save("chenzufeng");

        // 由JVM来销毁对象
    }
}
