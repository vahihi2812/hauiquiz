package com.example.hauiquiz.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.hauiquiz.R;
import com.example.hauiquiz.ui.forum.ViewProblems;
import com.example.hauiquiz.ui.chart.ShowScoreChart;
import com.example.hauiquiz.ui.viewHistory.ViewResultHistory;
import com.example.hauiquiz.ui.doTest.ChooseQuizz;
import com.example.hauiquiz.ui.doTest.ChooseTest;
import com.example.hauiquiz.ui.createQuiz.ViewQuestionSet;
import com.example.hauiquiz.ui.doNote.ViewSubjectList;

public class StudentHome extends AppCompatActivity implements View.OnClickListener {
    CardView card_test, card_revise, card_question, card_create, card_history, card_result, card_note, card_logout;

    @Override
    protected void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);

        getWidget();
        setEvents();
    }

    private void setEvents() {
        card_test.setOnClickListener(this);
        card_revise.setOnClickListener(this);
        card_question.setOnClickListener(this);
        card_create.setOnClickListener(this);
        card_history.setOnClickListener(this);
        card_result.setOnClickListener(this);
        card_note.setOnClickListener(this);
        card_logout.setOnClickListener(this);
    }

    private void getWidget() {
        card_test = findViewById(R.id.card_test);
        card_revise = findViewById(R.id.card_revise);
        card_question = findViewById(R.id.card_question);
        card_create = findViewById(R.id.card_create);
        card_history = findViewById(R.id.card_history);
        card_result = findViewById(R.id.card_result);
        card_note = findViewById(R.id.card_note);
        card_logout = findViewById(R.id.card_logout);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.card_test) {
            moveToTestScreen();
        } else if (id == R.id.card_revise) {
            moveToReviseScreen();
        } else if (id == R.id.card_question) {
            moveToQuestionScreen();
        } else if (id == R.id.card_create) {
            moveToCreateScreen();
        } else if (id == R.id.card_history) {
            moveToHistoryScreen();
        } else if (id == R.id.card_result) {
            moveToResultScreen();
        } else if (id == R.id.card_note) {
            moveToNoteScreen();
        } else if (id == R.id.card_logout) {
            doLogout();
        }
    }

    private void moveToResultScreen() {
        // View Ket qua hoc tap
        startActivity(new Intent(this, ShowScoreChart.class));
    }

    private void moveToHistoryScreen() {
        // View result history
        startActivity(new Intent(StudentHome.this, ViewResultHistory.class));
    }

    private void moveToCreateScreen() {
        // CreateQuizz
        startActivity(new Intent(StudentHome.this, ViewQuestionSet.class));
    }

    private void moveToQuestionScreen() {
        // Forum
        startActivity(new Intent(StudentHome.this, ViewProblems.class));
    }

    private void moveToReviseScreen() {
        // do Quizz
        startActivity(new Intent(StudentHome.this, ChooseQuizz.class));
    }

    private void moveToTestScreen() {
        // do Test
        startActivity(new Intent(StudentHome.this, ChooseTest.class));
    }

    private void moveToNoteScreen() {
//        Toast.makeText(this, "Note", Toast.LENGTH_SHORT).show();
//        Intent intent = new Intent(new Intent(StudentHome.this, PracticeMode.class));
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        startActivity(intent);
        startActivity(new Intent(StudentHome.this, ViewSubjectList.class));
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