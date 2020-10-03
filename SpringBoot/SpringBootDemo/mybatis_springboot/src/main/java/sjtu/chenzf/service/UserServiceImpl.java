package sjtu.chenzf.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sjtu.chenzf.dao.UserDAO;
import sjtu.chenzf.entity.User;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(rollbackFor = Exception.class)

public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    @Override
    public void saveUser(User user) {
        user.setId(UUID.randomUUID().toString());
        userDAO.saveUser(user);
    }

    @Override
    public List<User> findAllUser() {
        return userDAO.findAllUser();
    }
}
