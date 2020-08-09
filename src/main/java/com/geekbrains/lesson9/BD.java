package com.geekbrains.lesson9;

@Table(title="bd1")
public class BD {

    @Column
    int id;
    @Column
    String name;
    @Column
    int age;

    public BD(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }
}
