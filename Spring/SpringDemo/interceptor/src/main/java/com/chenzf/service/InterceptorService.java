package com.chenzf.service;

public interface InterceptorService {
    void save(String name);
    void update(String name);
    void delete(Integer id);
    String find(String name);
}
