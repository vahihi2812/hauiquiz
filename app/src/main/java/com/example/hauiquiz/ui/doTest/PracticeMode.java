package com.example.hauiquiz.ui.doTest;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.window.OnBackInvokedDispatcher;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hauiquiz.R;
import com.example.hauiquiz.dao.QuestionDAO;
import com.example.hauiquiz.entity.Question;
import com.example.hauiquiz.utils.DisplayMessageDialog;
import com.example.hauiquiz.utils.WorkWithNumber;

import java.util.ArrayList;
import java.util.List;

public class PracticeMode extends AppCompatActivity implements View.OnClickListener {
    private TextView txt_content, txt_time, txt_question;
    private CountDownTimer countDownTimer;
    private Button btn_check, btn_submit;
    private ImageButton btn_next, btn_prv;
    private RadioGroup rg;
    private RadioButton r1, r2, r3, r4;
    private int i = 0;
    private List<Question> list;
    private int[] arr;
    private int[] status;
    private RadioGroup.OnCheckedChangeListener radioListener;
    private long count_time;
    private int qs_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice_mode);

        getWidget();
        getData();
        setEvents();
        startCountdown(count_time);
        setBackPressed();
    }

    private void getData() {
        Intent intent = getIntent();
        qs_id = intent.getIntExtra(QuizDetail.QD_DATA_QS_ID, 0);
        int hour = intent.getIntExtra(QuizDetail.QD_DATA_HOUR, 0);
        int min = intent.getIntExtra(QuizDetail.QD_DATA_MIN, 0);
        count_time = (hour * 60L + min) * 60 * 1000;
        if (qs_id == 0 || count_time == 0) {
            DisplayMessageDialog.displayMessage(this, "Time = 0 OR QS_ID not existed", "ERROR");
            return;
        }

        list = new ArrayList<>();

        QuestionDAO questionDAO = new QuestionDAO(this);
        list.addAll(questionDAO.getQuestionsBySetId(qs_id));

        arr = new int[list.size()];
        status = new int[list.size()];
        for (int j = 0; j < list.size(); j++) {
            arr[j] = 0;
            status[j] = 0; // 0 - Has not been answered 1 - Answered
        }
        setWork();
    }

    private void setEvents() {
        btn_prv.setOnClickListener(this);
        btn_next.setOnClickListener(this);
        btn_check.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
        radioListener = (group, checkedId) -> {
            if (checkedId == R.id.practice_mode_r1) arr[i] = 1;
            else if (checkedId == R.id.practice_mode_r2) arr[i] = 2;
            else if (checkedId == R.id.practice_mode_r3) arr[i] = 3;
            else if (checkedId == R.id.practice_mode_r4) arr[i] = 4;
        };
        rg.setOnCheckedChangeListener(radioListener);
    }

    private void getWidget() {
        // TXT
        txt_content = findViewById(R.id.practice_mode_txt_content);
        txt_time = findViewById(R.id.practice_mode_txt_time);
        txt_question = findViewById(R.id.practice_mode_txt_question);
        // IMG BTN
        btn_next = findViewById(R.id.practice_mode_btn_next);
        btn_prv = findViewById(R.id.practice_mode_btn_prv);
        // BTN
        btn_check = findViewById(R.id.practice_mode_btn_check);
        btn_submit = findViewById(R.id.practice_mode_btn_submit);
        // SCROLL
        txt_content.setMovementMethod(new ScrollingMovementMethod());
        // RG RB
        rg = findViewById(R.id.practice_mode_rg);
        r1 = findViewById(R.id.practice_mode_r1);
        r2 = findViewById(R.id.practice_mode_r2);
        r3 = findViewById(R.id.practice_mode_r3);
        r4 = findViewById(R.id.practice_mode_r4);
    }

    private void startCountdown(long timeInMillis) {
        countDownTimer = new CountDownTimer(timeInMillis, 1000) {

            @SuppressLint("DefaultLocale")
            @Override
            public void onTick(long millisUntilFinished) {

                int minutes = (int) (millisUntilFinished / 1000) / 60;
                int seconds = (int) (millisUntilFinished / 1000) % 60;

                txt_time.setText(String.format("%02d:%02d", minutes, seconds));
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onFinish() {
                txt_time.setText("00:00");
                moveToResultScreen();
            }
        }.start();
    }

    private void finishTest() {
        finish();
    }

    private void moveToResultScreen() {
        AlertDialog.Builder builder = new AlertDialog.Builder(PracticeMode.this);
        builder.setTitle("HẾT GIỜ!");
        builder.setMessage("Đã hết giờ. Tự động nộp bài!");
        builder.setPositiveButton("OK", (dialog, which) -> {
            Intent intent = new Intent(PracticeMode.this, ResultView.class);
            intent.putExtra("kq", WorkWithNumber.roundOneDecimal(tinhDiem()));
            intent.putExtra(ResultView.RS_QS_ID, qs_id);
            startActivity(intent);
            finishTest();
        });
        builder.setCancelable(false);
        builder.create().show();
    }

    private Double tinhDiem() {
        int c = 0;
        for (int j = 0; j < list.size(); j++) {
            if (list.get(j).getCorrect_answer() == arr[j]) {
                c = c + 1;
            }
        }
        return 10.0 * c / list.size();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.practice_mode_btn_next) {
            if (i < list.size() - 1) {
                i = i + 1;
                setWork();
            }
        } else if (id == R.id.practice_mode_btn_prv) {
            if (i > 0) {
                i = i - 1;
                setWork();
            }
        } else if (id == R.id.practice_mode_btn_check) {
            checkAnswer();
        } else if (id == R.id.practice_mode_btn_submit) {
            doSubmitTest();
        }
    }

    private void doSubmitTest() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("NỘP BÀI");
        builder.setMessage("Vẫn còn thời gian làm bài. Bạn có chắc chắn nộp bài không?");
        builder.setPositiveButton("Có", (dialog, which) -> moveToResultScreen1());
        builder.setNegativeButton("Không", (dialog, which) -> dialog.cancel());
        builder.create().show();
    }

    private void checkAnswer() {
        if (arr[i] == 0) {
            DisplayMessageDialog.displayMessage(PracticeMode.this, "Chua chon dap an", "Hay chon dap an cua ban!");
            return;
        }
        displayAnswer();
    }

    // Checked arr[i] != 0
    private void displayAnswer() {
        RadioButton r_selected = getRadioButton(arr[i]);
        RadioButton r_correct = getRadioButton(list.get(i).getCorrect_answer());

        assert r_correct != null;
        r_correct.setTextColor(Color.GREEN);

        if (arr[i] != list.get(i).getCorrect_answer()) {
            assert r_selected != null;
            r_selected.setTextColor(Color.RED);
        }

        disableRadioGroup();
        status[i] = 1;
    }

    @SuppressLint("SetTextI18n")
    private void setWork() {
        rg.setOnCheckedChangeListener(null);

        txt_question.setText("Cau " + (i + 1));
        Question q = list.get(i);
        txt_content.setText(q.getQuestion_content());
        r1.setText(q.getFirst_choice());
        r2.setText(q.getSecond_choice());
        r3.setText(q.getThird_choice());
        r4.setText(q.getFourth_choice());

        setDefaultQuestion();

        if (arr[i] != 0) {
            RadioButton r_selected = getRadioButton(arr[i]);
            if (r_selected != null) r_selected.setChecked(true);
        }

        if (status[i] == 1) {
            getCurrentAnswer();
        }

        rg.setOnCheckedChangeListener(radioListener);
    }

    private void getCurrentAnswer() {
        RadioButton r_selected = getRadioButton(arr[i]);
        RadioButton r_correct = getRadioButton(list.get(i).getCorrect_answer());

        // Tô màu đúng sai
        assert r_correct != null;
        r_correct.setTextColor(Color.GREEN);

        if (arr[i] != list.get(i).getCorrect_answer()) {
            assert r_selected != null;
            r_selected.setTextColor(Color.RED);
        }

        // Hiển thị lựa chọn đã chọn
        assert r_selected != null;
        r_selected.setChecked(true);

        // Disable
        disableRadioGroup();
    }

    private void setDefaultQuestion() {
        rg.setEnabled(true);
        for (int j = 0; j < rg.getChildCount(); j++) {
            RadioButton r = (RadioButton) rg.getChildAt(j);
            r.setEnabled(true);
            r.setTextColor(Color.BLACK);
        }
        rg.clearCheck();
    }

    private RadioButton getRadioButton(int index) {
        switch (index) {
            case 1:
                return r1;
            case 2:
                return r2;
            case 3:
                return r3;
            case 4:
                return r4;
            default:
                return null;
        }
    }

    private void disableRadioGroup() {
        rg.setEnabled(false);
        for (int j = 0; j < rg.getChildCount(); j++) {
            View v = rg.getChildAt(j);
            v.setEnabled(false);
        }
    }

    private void setBackPressed() {
        // Chặn nút back cũ (Android < 13)
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                DisplayMessageDialog.displayMessage(PracticeMode.this, "ĐANG THI", "Vui lòng không thoát ra trong quá trình làm bài!");
            }
        });

        // Chặn gesture back Android 13+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) { // API 33+
            getOnBackInvokedDispatcher().registerOnBackInvokedCallback(
                    OnBackInvokedDispatcher.PRIORITY_DEFAULT,
                    () -> DisplayMessageDialog.displayMessage(PracticeMode.this, "ĐANG THI", "Vui lòng không thoát ra trong quá trình làm bài!"));
        }
    }

    private void moveToResultScreen1() {
        Intent intent = new Intent(PracticeMode.this, ResultView.class);
        intent.putExtra("kq", WorkWithNumber.roundOneDecimal(tinhDiem()));
        intent.putExtra(ResultView.RS_QS_ID, qs_id);
        startActivity(intent);
        finishTest();
    }
}