package com.example.hauiquiz.entity;

import androidx.annotation.NonNull;

public class Subject {
    private String subject_id;
    private String subject_name;
    private String subject_des;
    private int user_id;

    public Subject(String subject_id, String subject_name, String subject_des, int user_id) {
        this.subject_id = subject_id;
        this.subject_name = subject_name;
        this.subject_des = subject_des;
        this.user_id = user_id;
    }

    public String getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(String subject_id) {
        this.subject_id = subject_id;
    }

    public String getSubject_name() {
        return subject_name;
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }

    public String getSubject_des() {
        return subject_des;
    }

    public void setSubject_des(String subject_des) {
        this.subject_des = subject_des;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    @NonNull
    @Override
    public String toString() {
        return subject_id + " - " + subject_name;
    }
}
