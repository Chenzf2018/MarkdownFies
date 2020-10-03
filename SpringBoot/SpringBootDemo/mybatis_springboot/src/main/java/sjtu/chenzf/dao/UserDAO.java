package sjtu.chenzf.dao;

import sjtu.chenzf.entity.User;

import java.util.List;

public interface UserDAO {
    void saveUser(User user);
    List<User> findAllUser();
}
