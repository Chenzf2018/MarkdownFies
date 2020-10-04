package com.example.dao;

import com.example.entity.Employee;

import java.util.List;

public interface EmployeeDAO {

    List<Employee> findAllEmployee();

    void addEmployee(Employee employee);

    void deleteEmployee(String id);

    Employee findEmployee(String id);

    void update(Employee employee);
}
