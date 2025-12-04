package com.example.hauiquiz.entity;

import androidx.annotation.NonNull;

public class Test_result {
    private int ts_id;
    private Double score;
    private String completed_time;
    private String comment;
    private int set_id;
    private int user_id;

    public Test_result(int ts_id, Double score, String completed_time, String comment, int set_id, int user_id) {
        this.ts_id = ts_id;
        this.score = score;
        this.completed_time = completed_time;
        this.comment = comment;
        this.set_id = set_id;
        this.user_id = user_id;
    }

    @NonNull
    @Override
    public String toString() {
        return "QUIZ" + set_id + " - " + score + " - " + completed_time;
    }

    public int getTs_id() {
        return ts_id;
    }

    public void setTs_id(int ts_id) {
        this.ts_id = ts_id;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getCompleted_time() {
        return completed_time;
    }

    public void setCompleted_time(String completed_time) {
        this.completed_time = completed_time;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getSet_id() {
        return set_id;
    }

    public void setSet_id(int set_id) {
        this.set_id = set_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
