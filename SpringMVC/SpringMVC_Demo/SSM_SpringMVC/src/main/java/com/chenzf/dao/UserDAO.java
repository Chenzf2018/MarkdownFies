package com.chenzf.dao;

import com.chenzf.entity.User;

import java.util.List;

public interface UserDAO {

    void saveUser(User user);

    List<User> findAllUser();
}
