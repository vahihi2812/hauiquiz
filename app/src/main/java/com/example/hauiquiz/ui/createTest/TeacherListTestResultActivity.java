package com.example.hauiquiz.ui.createTest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hauiquiz.R;
import com.example.hauiquiz.adapter.QuestionSetAdapter;
import com.example.hauiquiz.dao.Question_setDAO;
import com.example.hauiquiz.entity.Question_Set;
import com.example.hauiquiz.ui.home.LoginActivity;

import java.util.ArrayList;
import java.util.List;

public class TeacherListTestResultActivity extends AppCompatActivity {

    private ListView lv;
    private ImageButton btnBack;
    private QuestionSetAdapter adapter;
    private List<Question_Set> list;
    private Question_setDAO dao;

    @Override
    protected void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_list_test_result);

        initView();
        loadData();
        setEvents();
    }

    private void initView() {
        lv = findViewById(R.id.lv_test_list_result);
        btnBack = findViewById(R.id.btn_back_list_result);
    }

    private void loadData() {
        dao = new Question_setDAO(this);
        list = new ArrayList<>();

        // Lấy tất cả bài thi do Teacher hiện tại tạo
        // (Hoặc có thể lấy tất cả nếu muốn Admin xem hết)
        list.addAll(dao.getAllSetsByUserId(LoginActivity.USER_ID));

        // Tái sử dụng QuestionSetAdapter bạn đã có
        adapter = new QuestionSetAdapter(this, R.layout.item_question_set, list);
        lv.setAdapter(adapter);
    }

    private void setEvents() {
        // Nút Back
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // SỰ KIỆN QUAN TRỌNG: Click vào bài thi -> Xem bảng điểm
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Question_Set selectedSet = list.get(position);

                // Chuyển sang màn hình TeacherViewResultActivity (Màn hình xem điểm)
                Intent intent = new Intent(TeacherListTestResultActivity.this, TeacherViewResultActivity.class);
                intent.putExtra("SET_ID", selectedSet.getSet_id());     // Truyền ID bài thi
                intent.putExtra("SET_NAME", selectedSet.getSet_name()); // Truyền Tên bài thi
                startActivity(intent);
            }
        });
    }
}