package com.chenzf.service;

import com.chenzf.entity.User;

import java.util.List;

public interface UserService {
    List<User> findAllUser();

    void saveUser(User user);
}
