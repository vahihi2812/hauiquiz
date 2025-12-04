package com.example.hauiquiz.ui.doTest;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hauiquiz.R;
import com.example.hauiquiz.dao.Test_resultDAO;
import com.example.hauiquiz.entity.Test_result;
import com.example.hauiquiz.ui.home.LoginActivity;
import com.example.hauiquiz.ui.home.StudentHome;
import com.example.hauiquiz.utils.DisplayMessageDialog;
import com.example.hauiquiz.utils.SystemTime;

public class ResultView extends AppCompatActivity implements View.OnClickListener {
    private TextView txt_result;
    private ImageButton btn_home;
    private Button btn_send;
    EditText edt_comment;
    public static final String RS_QS_ID = "rs_qs_id";
    private Test_resultDAO tsDAO;
    private long ts_id = -1;
    private Test_result ts;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_view);

        getWidget();
        getData();
        setEvents();
    }

    private void setEvents() {
        btn_send.setOnClickListener(this);
        btn_home.setOnClickListener(this);
    }

    @SuppressLint("SetTextI18n")
    private void getData() {
        Intent intent = getIntent();
        // score
        Double kq = intent.getDoubleExtra("kq", 0.0);
        txt_result.setText(kq + "/" + "10.0");

        // qs_id
        int qs_id = intent.getIntExtra(RS_QS_ID, 0);

        ts = new Test_result(0, kq,
                SystemTime.getTime() + " - " + LoginActivity.LOGIN_TIME,
                "", qs_id, LoginActivity.USER_ID);

        tsDAO = new Test_resultDAO(this);
        ts_id = tsDAO.addTest_result(ts);
    }

    private void getWidget() {
        // TXT
        txt_result = findViewById(R.id.txt_result);
        // IMG BTN
        btn_home = findViewById(R.id.btn_home);
        // EDT
        edt_comment = findViewById(R.id.result_view_edt_comment);
        // BTN
        btn_send = findViewById(R.id.result_view_btn_send);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_home) {
            getBack();
            return;
        }

        try {
            String comment = edt_comment.getText().toString();
            if (comment.isEmpty()) {
                DisplayMessageDialog.displayMessage(this, "COMMENT", "EMPTY");
                return;
            }
            ts.setComment(comment);
        } catch (Exception e) {
            DisplayMessageDialog.displayMessage(this, "COMMENT", "ERROR");
            return;
        }
        if (id == R.id.result_view_btn_send) {
            sendComment();
        }
    }

    private void sendComment() {
        if (ts_id != -1 && !ts.getComment().isEmpty()) {
            if (tsDAO.editTest_result(ts, ts_id)) {
                DisplayMessageDialog.displayMessage(this, "GỬI ĐÁNH GIÁ",
                        "Gửi đánh giá thành công! Cảm ơn vì đánh giá của bạn");
            }
        }
    }

    private void getBack() {
        Intent intent_back_to_home = new Intent(ResultView.this, StudentHome.class);
        intent_back_to_home.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent_back_to_home);
        finish();
    }
}