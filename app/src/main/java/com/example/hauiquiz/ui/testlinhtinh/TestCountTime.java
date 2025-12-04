package com.example.hauiquiz.ui.testlinhtinh;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hauiquiz.R;

public class TestCountTime extends AppCompatActivity {
    private CountDownTimer countDownTimer;
    TextView tvTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_count_time);

        tvTime = findViewById(R.id.tvTime);

        startCountdown(5 * 60 * 1000);
    }

    private void startCountdown(long timeInMillis) {
        countDownTimer = new CountDownTimer(timeInMillis, 1000) {

            @SuppressLint("DefaultLocale")
            @Override
            public void onTick(long millisUntilFinished) {

                int minutes = (int) (millisUntilFinished / 1000) / 60;
                int seconds = (int) (millisUntilFinished / 1000) % 60;

                tvTime.setText(String.format("%02d:%02d", minutes, seconds));
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onFinish() {
                tvTime.setText("00:00");
            }
        }.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}