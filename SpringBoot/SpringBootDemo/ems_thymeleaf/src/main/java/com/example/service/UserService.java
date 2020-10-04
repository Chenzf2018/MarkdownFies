package com.example.service;

import com.example.entity.User;

public interface UserService {

    void regist(User user);

    User login(String username, String userpassword);
}
