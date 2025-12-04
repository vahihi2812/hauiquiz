package com.example.hauiquiz.entity;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Problem implements Serializable {
    private int problem_id;
    private String problem_name;
    private String problem_content;
    private String problem_upload_time;
    private String username;

    public Problem(int problem_id, String problem_name, String problem_content, String problem_upload_time, String username) {
        this.problem_id = problem_id;
        this.problem_name = problem_name;
        this.problem_content = problem_content;
        this.problem_upload_time = problem_upload_time;
        this.username = username;
    }

    @NonNull
    @Override
    public String toString() {
        return problem_id + " - " + problem_name;
    }

    public int getProblem_id() {
        return problem_id;
    }

    public void setProblem_id(int problem_id) {
        this.problem_id = problem_id;
    }

    public String getProblem_name() {
        return problem_name;
    }

    public void setProblem_name(String problem_name) {
        this.problem_name = problem_name;
    }

    public String getProblem_content() {
        return problem_content;
    }

    public void setProblem_content(String problem_content) {
        this.problem_content = problem_content;
    }

    public String getProblem_upload_time() {
        return problem_upload_time;
    }

    public void setProblem_upload_time(String problem_upload_time) {
        this.problem_upload_time = problem_upload_time;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
