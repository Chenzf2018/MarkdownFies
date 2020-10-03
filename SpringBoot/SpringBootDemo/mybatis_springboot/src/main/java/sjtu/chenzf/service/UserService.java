package sjtu.chenzf.service;

import sjtu.chenzf.entity.User;

import java.util.List;

public interface UserService {
    void saveUser(User user);
    List<User> findAllUser();
}
