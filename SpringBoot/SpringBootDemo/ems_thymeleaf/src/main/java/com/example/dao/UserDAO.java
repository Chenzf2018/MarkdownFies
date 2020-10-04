package com.example.dao;

import com.example.entity.User;
import org.apache.ibatis.annotations.Param;

public interface UserDAO {
    void saveUser(User user);

    // 在mybatis中传递多个参数，需要参数绑定
    User login(@Param("username") String username, @Param("userpassword") String userpassword);
}
