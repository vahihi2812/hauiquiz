package com.example.hauiquiz.entity;

import androidx.annotation.NonNull;

public class Note {
    private int note_id;
    private String note_content;
    private String subject_id;

    public Note(int note_id, String note_content, String subject_id) {
        this.note_id = note_id;
        this.note_content = note_content;
        this.subject_id = subject_id;
    }

    public int getNote_id() {
        return note_id;
    }

    public void setNote_id(int note_id) {
        this.note_id = note_id;
    }

    public String getNote_content() {
        return note_content;
    }

    public void setNote_content(String note_content) {
        this.note_content = note_content;
    }

    public String getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(String subject_id) {
        this.subject_id = subject_id;
    }

    @NonNull
    @Override
    public String toString() {
        return note_content;
    }
}
