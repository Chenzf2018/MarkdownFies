package com.chenzf.service;

import com.chenzf.dao.UserDAO;
import com.chenzf.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Service("userService") 在工厂创建对象userService
 * Transactional 设置类中所有方法都需要事务
 * @author Chenzf
 */

@Service("userService")
@Transactional

public class UserServiceImpl implements UserService {

    /**
     * 业务层需要DAO，因此将其注入
     */
    @Autowired
    private UserDAO userDAO;

    /**
     * 处理业务
     * @param user 传入的对象
     */
    @Override
    public void saveUser(User user) {
        user.setId(UUID.randomUUID().toString());
        userDAO.saveUser(user);
    }

    /**
     * 查询没必要创建事务，仅需支持即可
     * @return 遍历的对象
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<User> findAllUser() {
        return userDAO.findAllUser();
    }
}
