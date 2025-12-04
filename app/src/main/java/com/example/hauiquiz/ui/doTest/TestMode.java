package com.example.hauiquiz.ui.doTest;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hauiquiz.R;
import com.example.hauiquiz.dao.QuestionDAO;
import com.example.hauiquiz.entity.Question;
import com.example.hauiquiz.utils.DisplayMessageDialog;
import com.example.hauiquiz.utils.WorkWithNumber;

import java.util.ArrayList;
import java.util.List;

public class TestMode extends AppCompatActivity implements View.OnClickListener {
    private TextView txt_content, txt_time, txt_question;
    private CountDownTimer countDownTimer;
    private ImageButton btn_next, btn_prv;
    private Button btn_submit;
    private RadioGroup rg;
    private RadioButton r1, r2, r3, r4;
    private int i = 0;
    private List<Question> list;
    private int[] arr;
    private long count_time;
    private int qs_id;

    @Override
    protected void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_mode);

        getWidget();
        getData();
        setEvents();
        startCountdown(count_time);
        setBackPressed();
    }

    public static final String PLAY_CODE_KEY = "code_let_play";

    private void getData() {
        Intent intent = getIntent();
        int let_play_code = intent.getIntExtra(PLAY_CODE_KEY, 0);
        int hour, min;
        qs_id = hour = min = 0;
        if (let_play_code == TestDetail.PLAY_TEST_CODE_VALUE) {
            qs_id = intent.getIntExtra(TestDetail.TD_DATA_QS_ID, 0);
            hour = intent.getIntExtra(TestDetail.TD_DATA_HOUR, 0);
            min = intent.getIntExtra(TestDetail.TD_DATA_MIN, 0);
        } else if (let_play_code == QuizDetail.PLAY_QUIZ_CODE_VALUE) {
            qs_id = intent.getIntExtra(QuizDetail.QD_DATA_QS_ID, 0);
            hour = intent.getIntExtra(QuizDetail.QD_DATA_HOUR, 0);
            min = intent.getIntExtra(QuizDetail.QD_DATA_MIN, 0);
        }

        count_time = (hour * 60L + min) * 60 * 1000;
        if (qs_id == 0 || count_time == 0) {
            DisplayMessageDialog.displayMessage(this, "Time = 0 OR QS_ID not existed", "ERROR");
            return;
        }

        list = new ArrayList<>();

        QuestionDAO questionDAO = new QuestionDAO(this);
        list.addAll(questionDAO.getQuestionsBySetId(qs_id));

        arr = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            arr[i] = 0;
        }
        setWork();
    }

    private void setEvents() {
        btn_prv.setOnClickListener(this);
        btn_next.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
        rg.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.r1) {
                arr[i] = 1;
            } else if (checkedId == R.id.r2) {
                arr[i] = 2;
            } else if (checkedId == R.id.r3) {
                arr[i] = 3;
            } else if (checkedId == R.id.r4) {
                arr[i] = 4;
            }
        });
    }

    private void getWidget() {
        // TXT
        txt_content = findViewById(R.id.txt_content);
        txt_time = findViewById(R.id.txt_time);
        txt_question = findViewById(R.id.txt_question);
        // BTN
        btn_next = findViewById(R.id.btn_next);
        btn_prv = findViewById(R.id.btn_prv);
        btn_submit = findViewById(R.id.test_mode_btn_submit);
        // SCROLL
        txt_content.setMovementMethod(new ScrollingMovementMethod());
        // RG && RB
        rg = findViewById(R.id.rg);
        r1 = findViewById(R.id.r1);
        r2 = findViewById(R.id.r2);
        r3 = findViewById(R.id.r3);
        r4 = findViewById(R.id.r4);
    }

    private void startCountdown(long timeInMillis) {
        // Khởi tạo CountDownTimer: đếm ngược từ timeInMillis, mỗi 1000ms (1 giây) gọi onTick()
        countDownTimer = new CountDownTimer(timeInMillis, 1000) {
            @SuppressLint({"DefaultLocale", "SyntheticAccessor"})
            @Override
            public void onTick(long millisUntilFinished) {
                // Tính số phút và giây còn lại
                int minutes = (int) (millisUntilFinished / 1000) / 60;
                int seconds = (int) (millisUntilFinished / 1000) % 60;
                // Hiển thị thời gian lên TextView theo định dạng mm:ss
                txt_time.setText(String.format("%02d:%02d", minutes, seconds));
            }
            @SuppressLint({"SetTextI18n", "SyntheticAccessor"})
            @Override
            public void onFinish() {
                // Khi thời gian kết thúc, hiển thị 00:00
                txt_time.setText("00:00");
                // Chuyển sang màn hình kết quả làm bài
                moveToResultScreen();
            }
        }.start(); // Bắt đầu chạy CountDownTimer
    }

    private void finishTest() {
        finish();
    }

    private void moveToResultScreen() {
        AlertDialog.Builder builder = new AlertDialog.Builder(TestMode.this);
        builder.setTitle("HẾT GIỜ!");
        builder.setMessage("Đã hết giờ. Tự động nộp bài!");
        builder.setPositiveButton("OK", (dialog, which) -> {
            Intent intent = new Intent(TestMode.this, ResultView.class);
            intent.putExtra("kq", WorkWithNumber.roundOneDecimal(tinhDiem()));
            intent.putExtra(ResultView.RS_QS_ID, qs_id);
            startActivity(intent);
            finishTest();
        });
        builder.setCancelable(false);
        builder.create().show();
    }

    private void moveToResultScreen1() {
        Intent intent = new Intent(TestMode.this, ResultView.class);
        intent.putExtra("kq", WorkWithNumber.roundOneDecimal(tinhDiem()));
        intent.putExtra(ResultView.RS_QS_ID, qs_id);
        startActivity(intent);
        finishTest();
    }

    private Double tinhDiem() {
        int c = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getCorrect_answer() == arr[i]) {
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
        if (id == R.id.btn_next) {
            if (i < list.size() - 1) {
                i = i + 1;
                setWork();
            }
        } else if (id == R.id.btn_prv) {
            if (i > 0) {
                i = i - 1;
                setWork();
            }
        } else if (id == R.id.test_mode_btn_submit) {
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

    @SuppressLint("SetTextI18n")
    private void setWork() {
        // Hiển thị số thứ tự câu hỏi (cộng 1 vì i bắt đầu từ 0)
        txt_question.setText("Câu " + (i + 1));
        // Lấy câu hỏi hiện tại từ danh sách
        Question q = list.get(i);
        // Hiển thị nội dung câu hỏi
        txt_content.setText(q.getQuestion_content());
        // Gán nội dung 4 lựa chọn vào 4 RadioButton
        r1.setText(q.getFirst_choice());
        r2.setText(q.getSecond_choice());
        r3.setText(q.getThird_choice());
        r4.setText(q.getFourth_choice());
        // Kiểm tra xem thí sinh đã chọn đáp án nào cho câu này chưa
        // arr[i] = 0 nghĩa là chưa chọn, 1–4 nghĩa là lựa chọn tương ứng
        if (arr[i] != 0) {
            // Lấy RadioButton theo chỉ số đã lưu (trừ 1 vì mảng bắt đầu từ 0)
            RadioButton r_selected = (RadioButton) rg.getChildAt(arr[i] - 1);
            // Đánh dấu RadioButton đó là đã được chọn
            r_selected.setChecked(true);
        } else {
            // Nếu chưa chọn đáp án, xóa mọi lựa chọn trong RadioGroup
            rg.clearCheck();
        }
    }

    private void setBackPressed() {
        // Chặn nút back cũ (Android < 13)
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                DisplayMessageDialog.displayMessage(TestMode.this, "ĐANG THI",
                        "Vui lòng không thoát ra trong quá trình làm bài!");
            }
        });
        // Chặn gesture back Android 13+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) { // API 33+
            getOnBackInvokedDispatcher().registerOnBackInvokedCallback(
                    OnBackInvokedDispatcher.PRIORITY_DEFAULT,
                    () -> DisplayMessageDialog.displayMessage(TestMode.this, "ĐANG THI",
                            "Vui lòng không thoát ra trong quá trình làm bài!"));
        }
    }
}
