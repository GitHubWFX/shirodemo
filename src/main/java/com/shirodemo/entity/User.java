package com.shirodemo.entity;

public class User {
    String id;
    String name;
    String password;
    String perms;

    public User(String id, String name, String password, String perms) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.perms = perms;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPerms() {
        return perms;
    }

    public void setPerms(String perms) {
        this.perms = perms;
    }
}
