package com.example.hauiquiz.entity;

public class Question {
    private int question_id;
    private String question_content;
    private String first_choice;
    private String second_choice;
    private String third_choice;
    private String fourth_choice;
    private int correct_answer;
    private int question_level;
    private String question_explain;
    private int set_id;

    public Question(int question_id, String question_content, String first_choice, String second_choice, String third_choice, String fourth_choice, int correct_answer, int question_level, String question_explain, int set_id) {
        this.question_id = question_id;
        this.question_content = question_content;
        this.first_choice = first_choice;
        this.second_choice = second_choice;
        this.third_choice = third_choice;
        this.fourth_choice = fourth_choice;
        this.correct_answer = correct_answer;
        this.question_level = question_level;
        this.question_explain = question_explain;
        this.set_id = set_id;
    }

    public Question(int question_id, String question_content, String first_choice, String second_choice, String third_choice, String fourth_choice, int correct_answer) {
        this.question_id = question_id;
        this.question_content = question_content;
        this.first_choice = first_choice;
        this.second_choice = second_choice;
        this.third_choice = third_choice;
        this.fourth_choice = fourth_choice;
        this.correct_answer = correct_answer;
    }

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public String getQuestion_content() {
        return question_content;
    }

    public void setQuestion_content(String question_content) {
        this.question_content = question_content;
    }

    public String getFirst_choice() {
        return first_choice;
    }

    public void setFirst_choice(String first_choice) {
        this.first_choice = first_choice;
    }

    public String getSecond_choice() {
        return second_choice;
    }

    public void setSecond_choice(String second_choice) {
        this.second_choice = second_choice;
    }

    public String getThird_choice() {
        return third_choice;
    }

    public void setThird_choice(String third_choice) {
        this.third_choice = third_choice;
    }

    public String getFourth_choice() {
        return fourth_choice;
    }

    public void setFourth_choice(String fourth_choice) {
        this.fourth_choice = fourth_choice;
    }

    public int getCorrect_answer() {
        return correct_answer;
    }

    public void setCorrect_answer(int correct_answer) {
        this.correct_answer = correct_answer;
    }

    public int getQuestion_level() {
        return question_level;
    }

    public void setQuestion_level(int question_level) {
        this.question_level = question_level;
    }

    public String getQuestion_explain() {
        return question_explain;
    }

    public void setQuestion_explain(String question_explain) {
        this.question_explain = question_explain;
    }

    public int getSet_id() {
        return set_id;
    }

    public void setSet_id(int set_id) {
        this.set_id = set_id;
    }
}
