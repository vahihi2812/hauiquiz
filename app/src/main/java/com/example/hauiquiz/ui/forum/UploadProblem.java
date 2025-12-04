package com.example.hauiquiz.ui.forum;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hauiquiz.R;
import com.example.hauiquiz.entity.Problem;
import com.example.hauiquiz.ui.home.LoginActivity;
import com.example.hauiquiz.utils.DisplayMessageDialog;
import com.example.hauiquiz.utils.SystemTime;

public class UploadProblem extends AppCompatActivity implements View.OnClickListener {
    private ImageButton btn_back;
    private Button btn_add;
    private EditText edt_name, edt_content;

    @Override
    protected void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_problem);

        getWidget();
        getData();
        setEvents();
    }

    private void setEvents() {
        btn_back.setOnClickListener(this);
        btn_add.setOnClickListener(this);
    }

    private void getData() {
        edt_name.requestFocus();
    }

    private void getWidget() {
        // BTN IMG BTN
        btn_back = findViewById(R.id.upload_problem_btn_back);
        btn_add = findViewById(R.id.upload_problem_btn_add);
        // EDT
        edt_name = findViewById(R.id.upload_problem_edt_name);
        edt_content = findViewById(R.id.upload_problem_edt_content);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.upload_problem_btn_back) {
            getBack();
            return;
        }

        Problem p;
        try {
            String name = edt_name.getText().toString();
            String content = edt_content.getText().toString();
            if (name.isEmpty() || content.isEmpty()) {
                DisplayMessageDialog.displayMessage(this, "DATA", "EMPTY");
                return;
            }
            p = new Problem(0, name, content,
                    SystemTime.getTime() + " - " + LoginActivity.LOGIN_TIME,
                    LoginActivity.USERNAME);
        } catch (Exception e) {
            DisplayMessageDialog.displayMessage(this, "DATA", "ERROR");
            return;
        }

        if (id == R.id.upload_problem_btn_add) {
            uploadProblem(p);
        }
    }

    private void uploadProblem(Problem p) {
        if (p != null) {
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putSerializable(ViewProblems.DATA_UPLOAD_PROBLEM, p);
            intent.putExtra(ViewProblems.DATA_UPLOAD_BUNDLE, bundle);
            setResult(ViewProblems.CODE_UPLOAD_POST, intent);
            finish();
        }
    }

    private void getBack() {
        finish();
    }
}