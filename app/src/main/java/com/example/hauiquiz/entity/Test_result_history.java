package com.example.hauiquiz.entity;

import androidx.annotation.NonNull;

public class Test_result_history {
    private String set_name;
    private Double score;
    private String completed_time;
    private String comment;
    private int set_id;

    public Test_result_history(String set_name, Double score, String completed_time, String comment, int set_id) {
        this.set_name = set_name;
        this.score = score;
        this.completed_time = completed_time;
        this.comment = comment;
        this.set_id = set_id;
    }

    public String getSet_name() {
        return set_name;
    }

    public Double getScore() {
        return score;
    }

    public String getCompleted_time() {
        return completed_time;
    }

    public String getComment() {
        return comment;
    }

    public int getSet_id() {
        return set_id;
    }

    @NonNull
    @Override
    public String toString() {
        return "QUIZ" + set_id + " - " + set_name + " - " + score + " - " + completed_time + " - " + comment;
    }
}
