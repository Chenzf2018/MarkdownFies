package com.chenzf.dao;

public class UserDAOImpl implements UserDAO {
    @Override
    public void save(String name) {
        System.out.println("UserDAO接口实现类UserDAOImpl: " + name);
    }
}
