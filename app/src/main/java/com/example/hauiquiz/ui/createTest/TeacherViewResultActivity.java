package com.example.hauiquiz.ui.createTest; // Sửa package cho đúng với dự án của bạn

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageButton; // Import ImageButton
import android.view.View; // Import View

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hauiquiz.R;
import com.example.hauiquiz.adapter.StudentResultAdapter;
import com.example.hauiquiz.dao.Test_resultDAO;
import com.example.hauiquiz.entity.StudentResult;

import java.util.List;

public class TeacherViewResultActivity extends AppCompatActivity {
    private ListView lv;
    private TextView tvTitle;
    private ImageButton btnBack; // Thêm nút back
    private Test_resultDAO dao;

    @Override
    protected void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_view_result); // Đảm bảo file layout này đã tạo

        lv = findViewById(R.id.lv_student_results);
        tvTitle = findViewById(R.id.tv_title_test); // ID trong layout bạn gửi
        btnBack = findViewById(R.id.btn_back_result); // ID trong layout bạn gửi

        dao = new Test_resultDAO(this);

        // Lấy dữ liệu từ màn hình trước truyền sang
        int setId = getIntent().getIntExtra("SET_ID", -1);
        String setName = getIntent().getStringExtra("SET_NAME");

        if (setId != -1) {
            tvTitle.setText("KẾT QUẢ: " + setName);
            loadData(setId);
        }

        // Sự kiện nút back
        if(btnBack != null) {
            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }

    private void loadData(int setId) {
        List<StudentResult> list = dao.getListStudentResultBySetId(setId);
        if (list.isEmpty()) {
            Toast.makeText(this, "Chưa có sinh viên nào làm bài này", Toast.LENGTH_SHORT).show();
        }
        // Đảm bảo bạn đã có StudentResultAdapter
        StudentResultAdapter adapter = new StudentResultAdapter(this, R.layout.item_student_result, list);
        lv.setAdapter(adapter);
    }
}