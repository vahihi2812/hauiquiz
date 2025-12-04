package com.example.hauiquiz.entity;

import androidx.annotation.NonNull;

public class Solution {
    private int solution_id;
    private String solution_content;
    private String solution_upload_time;
    private String username;
    private int problem_id;

    public Solution(int solution_id, String solution_content, String solution_upload_time, String username, int problem_id) {
        this.solution_id = solution_id;
        this.solution_content = solution_content;
        this.solution_upload_time = solution_upload_time;
        this.username = username;
        this.problem_id = problem_id;
    }

    @NonNull
    @Override
    public String toString() {
        return solution_id + " - " + solution_content;
    }

    public int getSolution_id() {
        return solution_id;
    }

    public void setSolution_id(int solution_id) {
        this.solution_id = solution_id;
    }

    public String getSolution_content() {
        return solution_content;
    }

    public void setSolution_content(String solution_content) {
        this.solution_content = solution_content;
    }

    public String getSolution_upload_time() {
        return solution_upload_time;
    }

    public void setSolution_upload_time(String solution_upload_time) {
        this.solution_upload_time = solution_upload_time;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getProblem_id() {
        return problem_id;
    }

    public void setProblem_id(int problem_id) {
        this.problem_id = problem_id;
    }
}
