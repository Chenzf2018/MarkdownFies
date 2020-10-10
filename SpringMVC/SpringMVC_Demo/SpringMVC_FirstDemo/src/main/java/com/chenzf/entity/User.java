package com.chenzf.entity;

import java.util.Date;

public class User {
    private String name;
    private Integer age;
    private Double salary;
    private Boolean sexual;
    private Date birth;

    public User() {}

    public User(String name, Integer age, Double salary, Boolean sexual, Date birth) {
        this.name = name;
        this.age = age;
        this.salary = salary;
        this.sexual = sexual;
        this.birth = birth;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", salary=" + salary +
                ", sexual=" + sexual +
                ", birth=" + birth +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Boolean getSexual() {
        return sexual;
    }

    public void setSexual(Boolean sexual) {
        this.sexual = sexual;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }
}
