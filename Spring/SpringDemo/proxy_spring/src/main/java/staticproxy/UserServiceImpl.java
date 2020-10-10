package staticproxy;

// 业务逻辑对象

public class UserServiceImpl implements UserService {
    @Override
    public void save(String name) {
        System.out.println("处理业务逻辑,调用saveDAO");
    }

    @Override
    public void delete(String id) {
        System.out.println("处理业务逻辑,调用deleteDAO");
    }

    @Override
    public void update() {
        System.out.println("处理业务逻辑,调用updateDAO");
    }

    @Override
    public String findAll(String name) {
        System.out.println("处理业务逻辑,调用findAllDAO");
        return name;
    }

    @Override
    public String findOne(String id) {
        System.out.println("处理业务逻辑,调用findOneDAO");
        return id;
    }
}