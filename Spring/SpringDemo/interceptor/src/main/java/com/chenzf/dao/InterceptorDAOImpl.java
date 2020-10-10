package com.chenzf.dao;

public class InterceptorDAOImpl implements InterceptorDAO {
    @Override
    public void save(String name) {
        System.out.println("InterceptorDAOImpl(saveDAO)：" + name);
    }

    @Override
    public void update(String name) {
        System.out.println("InterceptorDAOImpl(updateDAO)：" + name);
    }

    @Override
    public void delete(Integer id) {
        System.out.println("InterceptorDAOImpl(deleteDAO)：" + id);
    }

    @Override
    public String find(String name) {
        System.out.println("InterceptorDAOImpl(findDAO)：" + name);
        return name;
    }
}
