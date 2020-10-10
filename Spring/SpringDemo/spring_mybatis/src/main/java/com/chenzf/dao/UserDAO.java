package com.chenzf.dao;

import com.chenzf.entity.User;

import java.util.List;

public interface UserDAO {

    /**
     * 查询所有User
     * @return User列表
     */
    List<User> findAllUser();

    /**
     * 保存User
     * 需要控制事务
     * @param user
     */
    void saveUser(User user);
}
