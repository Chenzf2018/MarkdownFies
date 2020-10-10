package com.chenzf.service;

import com.chenzf.dao.InterceptorDAO;

/**
 * 被代理对象
 * 业务层在方法中需要调用DAO
 */
public class InterceptorServiceImpl implements InterceptorService {

    /**
     * 依赖DAO组件
     */
    private InterceptorDAO interceptorDAO;

    public void setInterceptorDAO(InterceptorDAO interceptorDAO) {
        this.interceptorDAO = interceptorDAO;
    }

    @Override
    public void save(String name) {
        System.out.println("被代理对象（InterceptorServiceImpl）处理save业务逻辑，调用DAO：" + name);
        interceptorDAO.save(name);
    }

    @Override
    public void update(String name) {
        System.out.println("被代理对象（InterceptorServiceImpl）处理update业务逻辑，调用DAO：" + name);
        interceptorDAO.update(name);
    }

    @Override
    public void delete(Integer id) {
        System.out.println("被代理对象（InterceptorServiceImpl）处理delete业务逻辑，调用DAO：" + id);
        interceptorDAO.delete(id);
    }

    @Override
    public String find(String name) {
        System.out.println("被代理对象（InterceptorServiceImpl）处理find业务逻辑，调用DAO：" + name);
        return interceptorDAO.find(name);
    }
}
