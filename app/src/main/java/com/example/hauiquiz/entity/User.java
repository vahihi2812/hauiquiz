package com.example.hauiquiz.entity;

public class User {
    private int user_id;
    private String username;
    private String password;
    private String user_fullname;
    private int role_id;

    public User(int user_id, String username, String password, String user_fullname, int role_id) {
        this.user_id = user_id;
        this.username = username;
        this.password = password;
        this.user_fullname = user_fullname;
        this.role_id = role_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUser_fullname() {
        return user_fullname;
    }

    public void setUser_fullname(String user_fullname) {
        this.user_fullname = user_fullname;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }
}
