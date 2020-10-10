package com.chenzf.service;

import com.chenzf.entity.User;

import java.util.List;

public interface UserService {

    void saveUser(User user);

    List<User> findAllUser();
}
