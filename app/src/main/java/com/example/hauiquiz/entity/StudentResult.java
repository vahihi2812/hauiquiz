package com.example.hauiquiz.entity;

public class StudentResult {
    private String studentName;
    private double score;
    private String submitTime;

    public StudentResult(String studentName, double score, String submitTime) {
        this.studentName = studentName;
        this.score = score;
        this.submitTime = submitTime;
    }

    public String getStudentName() { return studentName; }
    public double getScore() { return score; }
    public String getSubmitTime() { return submitTime; }
}
