package com.example.firstwebapp.entity;

import java.io.Serializable;

public class Beer implements Serializable {
    private static final long serialVersionUID = 1L;
    Integer id;
    String name;

    public Beer(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}