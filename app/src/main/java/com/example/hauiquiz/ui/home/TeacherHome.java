package com.example.hauiquiz.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.hauiquiz.R;
import com.example.hauiquiz.ui.createTest.TeacherCreateTest;
import com.example.hauiquiz.ui.createQuiz.ViewQuestionSet;
import com.example.hauiquiz.ui.forum.ViewProblems;

public class TeacherHome extends AppCompatActivity implements View.OnClickListener {
    CardView card_teacher_test, card_teacher_result, card_teacher_question, card_teacher_create, card_teacher_history, card_teacher_logout;

    @Override
    protected void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_home);
        getWidget();
        setEvents();
    }

    private void setEvents() {
        card_teacher_test.setOnClickListener(this);
        card_teacher_result.setOnClickListener(this);
        card_teacher_question.setOnClickListener(this);
        card_teacher_create.setOnClickListener(this);
        card_teacher_history.setOnClickListener(this);
        card_teacher_logout.setOnClickListener(this);
    }

    private void getWidget() {
        card_teacher_test = findViewById(R.id.card_teacher_test);
        card_teacher_result = findViewById(R.id.card_teacher_result);
        card_teacher_question = findViewById(R.id.card_teacher_question);
        card_teacher_result = findViewById(R.id.card_teacher_result);
        card_teacher_create = findViewById(R.id.card_teacher_create);
        card_teacher_history = findViewById(R.id.card_teacher_history);
        card_teacher_logout = findViewById(R.id.card_teacher_logout);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.card_teacher_test) {
            moveToTeacherCreate();
        } else if (id == R.id.card_teacher_result) {
            moveToTeacherResult();
        } else if (id == R.id.card_teacher_question) {
            moveToTeacherQuestion();
        } else if (id == R.id.card_teacher_create) {
            moveToTeacherQuiz();
        } else if (id == R.id.card_teacher_history) {
            moveToTeacherHistory();
        } else if (id == R.id.card_teacher_logout) {
            doLogout();
        }
    }

    private void moveToTeacherQuestion() {
        startActivity(new Intent(this, ViewProblems.class));
    }

    private void moveToTeacherResult() {
        Intent intent = new Intent(TeacherHome.this, com.example.hauiquiz.ui.createTest.TeacherListTestResultActivity.class);
        startActivity(intent);
    }

    private void moveToTeacherCreate() {
        startActivity(new Intent(this, TeacherCreateTest.class));
    }

    private void moveToTeacherQuiz() {
        startActivity(new Intent(this, ViewQuestionSet.class));
    }

    private void moveToTeacherHistory() {
        // UPDATE
    }

    private void doLogout() {
        finish();
    }

//    private void clearPreferences() {
//        SharedPreferences preferences = getSharedPreferences(LoginActivity.PRE_NAME, MODE_PRIVATE);
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.clear();
//        editor.apply();
//    }
}