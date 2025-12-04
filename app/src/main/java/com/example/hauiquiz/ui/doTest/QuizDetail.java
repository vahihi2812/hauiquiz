package com.example.hauiquiz.ui.doTest;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hauiquiz.R;
import com.example.hauiquiz.entity.Question_Set;
import com.example.hauiquiz.utils.DisplayMessageDialog;

import java.util.ArrayList;
import java.util.List;

public class QuizDetail extends AppCompatActivity implements View.OnClickListener {
    private ImageButton btn_back;
    private Button btn_enter;
    private TextView txt_id, txt_name, txt_des, txt_updated_time, txt_creator;
    private EditText edt_hour, edt_min;
    private Spinner spn_play_mode;
    private Question_Set qs;

    @Override
    protected void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_detail);

        getWidget();
        getData();
        setEvents();
    }

    private void setEvents() {
        btn_back.setOnClickListener(this);
        btn_enter.setOnClickListener(this);
    }

    @SuppressLint("SetTextI18n")
    private void getData() {
        setPlayModeSPN();

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(ChooseQuizz.DATA_QUIZ_BUNDLE);
        if (bundle != null) {
            qs = (Question_Set) bundle.getSerializable(ChooseQuizz.DATA_QUIZ);
            if (qs != null) {
                txt_id.setText(qs.getSet_id() + "");
                txt_name.setText(qs.getSet_name());
                txt_des.setText(qs.getSet_des());
                txt_updated_time.setText(qs.getSet_created_at());
                txt_creator.setText(qs.getSet_creator());
                // FAKE
                edt_hour.setText("0");
                edt_min.setText("1");
            }
        }
    }

    private void setPlayModeSPN() {
        List<String> list_play_mode = new ArrayList<>();
        list_play_mode.add("Thi thử");
        list_play_mode.add("Luyện tập");
        ArrayAdapter<String> adapter_play_mode = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, list_play_mode);
        adapter_play_mode.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_play_mode.setAdapter(adapter_play_mode);
        spn_play_mode.setSelection(0);
    }

    private void getWidget() {
        // IMG BTN
        btn_back = findViewById(R.id.quiz_detail_btn_back);
        // BTN
        btn_enter = findViewById(R.id.quiz_detail_btn_enter);
        // TXT
        txt_id = findViewById(R.id.quiz_detail_txt_id);
        txt_name = findViewById(R.id.quiz_detail_txt_name);
        txt_des = findViewById(R.id.quiz_detail_txt_des);
        txt_updated_time = findViewById(R.id.quiz_detail_txt_updated_time);
        txt_creator = findViewById(R.id.quiz_detail_txt_creator);
        // EDT
        edt_hour = findViewById(R.id.quiz_detail_edt_hour);
        edt_min = findViewById(R.id.quiz_detail_edt_minute);
        // SPN
        spn_play_mode = findViewById(R.id.quiz_detail_spn_play_mode);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.quiz_detail_btn_back) {
            getBack();
            return;
        }

        int hour, min;
        try {
            hour = Integer.parseInt(edt_hour.getText().toString());
            min = Integer.parseInt(edt_min.getText().toString());
            if (hour == 0 && min == 0) {
                DisplayMessageDialog.displayMessage(this, "00:00", "ERROR");
                return;
            }
        } catch (Exception e) {
            DisplayMessageDialog.displayMessage(this, "DATA", "ERROR");
            return;
        }

        if (id == R.id.quiz_detail_btn_enter) {
            enterTest(hour, min, spn_play_mode.getSelectedItemPosition());
        }
    }

    public static final int TEST_PLAY_MODE = 0;
    public static final int PRACTICE_PLAY_MODE = 1;

    private void enterTest(int hour, int min, int playMode) {
        if (playMode == TEST_PLAY_MODE) {
            enterTestMode(hour, min);
        } else if (playMode == PRACTICE_PLAY_MODE) {
            enterPracticeMode(hour, min);
        }
    }
    public static final int PLAY_QUIZ_CODE_VALUE = 2;
    public static final String QD_DATA_QS_ID = "qd_data_qs_id";
    public static final String QD_DATA_HOUR = "qd_data_hour";
    public static final String QD_DATA_MIN = "qd_data_min";

    private void enterTestMode(int hour, int min) {
        // gui ques_set_id
        Intent intent = new Intent(this, TestMode.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra(TestMode.PLAY_CODE_KEY, PLAY_QUIZ_CODE_VALUE);
        intent.putExtra(QD_DATA_QS_ID, qs.getSet_id());
        intent.putExtra(QD_DATA_HOUR, hour);
        intent.putExtra(QD_DATA_MIN, min);
        startActivity(intent);
    }

    private void enterPracticeMode(int hour, int min) {
        Intent intent = new Intent(this, PracticeMode.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra(QD_DATA_QS_ID, qs.getSet_id());
        intent.putExtra(QD_DATA_HOUR, hour);
        intent.putExtra(QD_DATA_MIN, min);
        startActivity(intent);
    }

    private void getBack() {
        finish();
    }
}