package com.example.hauiquiz.entity;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Question_Set implements Serializable {
    private int set_id;
    private String set_name;
    private String set_des;
    private String set_created_at;
    private int set_type;
    private int set_weight;
    private String set_duration;
    private String set_creator;
    private int user_id;

    public static final int TYPE_QUIZ = 1;
    public static final int TYPE_TEST = 2;

    public Question_Set() {
    }

    public Question_Set(int set_id, String set_name, String set_des, String set_created_at, int set_type, int set_weight, String set_duration, String set_creator, int user_id) {
        this.set_id = set_id;
        this.set_name = set_name;
        this.set_des = set_des;
        this.set_created_at = set_created_at;
        this.set_type = set_type;
        this.set_weight = set_weight;
        this.set_duration = set_duration;
        this.set_creator = set_creator;
        this.user_id = user_id;
    }

    public int getSet_id() {
        return set_id;
    }

    public void setSet_id(int set_id) {
        this.set_id = set_id;
    }

    public String getSet_name() {
        return set_name;
    }

    public void setSet_name(String set_name) {
        this.set_name = set_name;
    }

    public String getSet_des() {
        return set_des;
    }

    public void setSet_des(String set_des) {
        this.set_des = set_des;
    }

    public String getSet_created_at() {
        return set_created_at;
    }

    public void setSet_created_at(String set_created_at) {
        this.set_created_at = set_created_at;
    }

    public int getSet_type() {
        return set_type;
    }

    public void setSet_type(int set_type) {
        this.set_type = set_type;
    }

    public int getSet_weight() {
        return set_weight;
    }

    public void setSet_weight(int set_weight) {
        this.set_weight = set_weight;
    }

    public String getSet_duration() {
        return set_duration;
    }

    public void setSet_duration(String set_duration) {
        this.set_duration = set_duration;
    }

    public String getSet_creator() {
        return set_creator;
    }

    public void setSet_creator(String set_creator) {
        this.set_creator = set_creator;
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
        return "QS" + set_id + " - " + set_name;
    }
}
