package com.example.service;

import com.example.entity.Employee;

import java.util.List;

public interface EmployeeService {

    List<Employee> findAllEmployee();

    void addEmployee(Employee employee);

    void deleteEmployee(String id);

    Employee findEmployee(String id);

    void update(Employee employee);
}
