package com.example.hauiquiz.ui.viewHistory;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hauiquiz.R;
import com.example.hauiquiz.adapter.Test_result_historyAdapter;
import com.example.hauiquiz.dao.Test_resultDAO;
import com.example.hauiquiz.entity.Test_result_history;
import com.example.hauiquiz.ui.home.LoginActivity;

import java.util.ArrayList;
import java.util.List;

public class ViewResultHistory extends AppCompatActivity implements View.OnClickListener {
    private ListView lv;
    private List<Test_result_history> list;
    private Test_result_historyAdapter adapter;
    private ImageButton btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_result_history);

        getWidget();
        getData();
        setEvents();
    }

    private void setEvents() {
        btn_back.setOnClickListener(this);
    }

    private void getData() {
        Test_resultDAO tsDAO = new Test_resultDAO(this);
        list = new ArrayList<>(tsDAO.getAllTestHistoryByUserId(LoginActivity.USER_ID));
        adapter = new Test_result_historyAdapter(this, R.layout.item_test_result_history, list);
        lv.setAdapter(adapter);
    }

    private void getWidget() {
        lv = findViewById(R.id.view_result_history_lv);
        btn_back = findViewById(R.id.view_result_history_btn_back);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.view_result_history_btn_back) {
            getBack();
        }
    }

    private void getBack() {
        finish();
    }
}