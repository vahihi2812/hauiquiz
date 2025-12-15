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
import com.example.hauiquiz.entity.Question_Set;
import com.example.hauiquiz.utils.DisplayMessageDialog;
import com.example.hauiquiz.utils.SystemTime;

public class TestDetail extends AppCompatActivity implements View.OnClickListener {
    private ImageButton btn_back;
    private Button btn_enter;
    TextView txt_id, txt_name, txt_des, txt_updated_time, txt_creator;
    EditText edt_hour, edt_min;
    private Question_Set qs;

    @Override
    protected void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_detail);

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
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(ChooseTest.DATA_TEST_BUNDLE);
        if (bundle != null) {
            qs = (Question_Set) bundle.getSerializable(ChooseTest.DATA_TEST);
            if (qs != null) {
                txt_id.setText(qs.getSet_id() + "");
                txt_name.setText(qs.getSet_name());
                txt_des.setText(qs.getSet_des());
                txt_updated_time.setText(qs.getSet_created_at());
                txt_creator.setText(qs.getSet_creator());
                // NOT FAKE
                String[] arr = SystemTime.getHourAndMin(qs.getSet_duration());
                edt_hour.setText(arr[0]);
                edt_min.setText(arr[1]);
                edt_hour.setEnabled(false);
                edt_min.setEnabled(false);
            }
        }
    }

    private void getWidget() {
        // IMG BTN
        btn_back = findViewById(R.id.test_detail_btn_back);
        // BTN
        btn_enter = findViewById(R.id.test_detail_btn_enter);
        // TXT
        txt_id = findViewById(R.id.test_detail_txt_id);
        txt_name = findViewById(R.id.test_detail_txt_name);
        txt_des = findViewById(R.id.test_detail_txt_des);
        txt_updated_time = findViewById(R.id.test_detail_txt_updated_time);
        txt_creator = findViewById(R.id.test_detail_txt_creator);
        // EDT
        edt_hour = findViewById(R.id.test_detail_edt_hour);
        edt_min = findViewById(R.id.test_detail_edt_minute);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.test_detail_btn_back) {
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

        if (id == R.id.test_detail_btn_enter) {
            enterTest(hour, min);
        }
    }
    public static final int PLAY_TEST_CODE_VALUE = 1;
    public static final String TD_DATA_QS_ID = "td_data_qs_id";
    public static final String TD_DATA_HOUR = "td_data_hour";
    public static final String TD_DATA_MIN = "td_data_min";

    private void enterTest(int hour, int min) {
        // gui ques_set_id
        Intent intent = new Intent(this, TestMode.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra(TestMode.PLAY_CODE_KEY, PLAY_TEST_CODE_VALUE);
        intent.putExtra(TD_DATA_QS_ID, qs.getSet_id());
        intent.putExtra(TD_DATA_HOUR, hour);
        intent.putExtra(TD_DATA_MIN, min);
        startActivity(intent);
    }

    private void getBack() {
        finish();
    }
}