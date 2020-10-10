package com.chenzf.dao;

public interface InterceptorDAO {
    void save(String name);
    void update(String name);
    void delete(Integer id);
    String find(String name);
}
