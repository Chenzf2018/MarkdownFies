package com.chenzf.service;

import com.chenzf.dao.UserDAO;
import com.chenzf.entity.User;

import java.util.List;
import java.util.UUID;

/**
 * 处理业务逻辑，调用DAO
 */

public class UserServiceImpl implements UserService {

    /**
     * 引入依赖
     */
    private UserDAO userDAO;

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public void saveUser(User user) {
        user.setId(UUID.randomUUID().toString());
        userDAO.saveUser(user);
        // 测试事务是否回滚
        //int i = 1 / 0;
    }

    @Override
    public List<User> findAllUser() {
        return userDAO.findAllUser();
    }
}
