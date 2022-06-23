package com.csu.algorithm.Sort;

public class Emp {

     String name; //姓名
     Integer age; //年龄

    public Emp(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Emp{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
