package com.example.hauiquiz.ui.createQuiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hauiquiz.R;
import com.example.hauiquiz.adapter.QuestionSetAdapter;
import com.example.hauiquiz.dao.Question_setDAO;
import com.example.hauiquiz.entity.Question_Set;
import com.example.hauiquiz.ui.home.LoginActivity;
import com.example.hauiquiz.utils.DisplayMessageDialog;

import java.util.ArrayList;
import java.util.List;

public class ViewQuestionSet extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {
    private ListView lv;
    private EditText view_question_set_edt_name, view_question_set_edt_des;
    private Button view_question_set_btn_view;
    ImageButton view_question_set_btn_back, view_question_set_btn_menu;
    private List<Question_Set> list;
    private QuestionSetAdapter adapter;
    private int id_selected = -1;
    Question_setDAO setDAO;

    @Override
    protected void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_question_set);

        getWidget();
        getData();
        setEvents();
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        // Lấy id của item được chọn trong context menu
        int id = item.getItemId();
        // Đối tượng lưu thông tin bộ câu hỏi
        Question_Set qs;
        try {
            // Lấy dữ liệu người dùng nhập từ các ô EditText
            String name = view_question_set_edt_name.getText().toString();
            String des = view_question_set_edt_des.getText().toString();
            // Kiểm tra dữ liệu rỗng – đảm bảo người dùng nhập đầy đủ thông tin
            if (name.isEmpty() || des.isEmpty()) {
                // Hiển thị thông báo lỗi nếu dữ liệu không hợp lệ
                DisplayMessageDialog.displayMessage(this, "DỮ LIỆU", "Không được để trống!");
                // Dừng xử lý sự kiện
                return true;
            }
            qs = new Question_Set(0, name, des, LoginActivity.LOGIN_TIME, Question_Set.TYPE_QUIZ,
                    0, "", LoginActivity.USERNAME, LoginActivity.USER_ID);
        } catch (Exception e) {
            // Bắt các lỗi phát sinh trong quá trình xử lý dữ liệu
            DisplayMessageDialog.displayMessage(this, "DỮ LIỆU", "Chưa hợp lệ!");
            return true;
        }
        // Xử lý hành động dựa trên item được chọn trong context menu
        if (id == R.id.action_add) {
            // Thực hiện chức năng thêm bộ câu hỏi mới
            addQuestionSet(qs);
            return true;
        } else if (id == R.id.action_edit) {
            // Thực hiện chức năng chỉnh sửa bộ câu hỏi
            editQuestionSet(qs);
            return true;
        } else if (id == R.id.action_delete) {
            // Thực hiện chức năng xóa bộ câu hỏi
            delQuestionSet();
            return true;
        }
        // Gọi xử lý mặc định nếu không khớp hành động nào
        return super.onContextItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu_crud, menu);
    }

    private void setEvents() {
        // LV
        lv.setOnItemClickListener(this);
        // Button
        view_question_set_btn_view.setOnClickListener(this);
        // IMG Button
        view_question_set_btn_back.setOnClickListener(this);
        // Register context menu
        registerForContextMenu(view_question_set_btn_menu);
    }

    private void getData() {
        list = new ArrayList<>();
        adapter = new QuestionSetAdapter(this, R.layout.item_question_set, list);
        lv.setAdapter(adapter);

        setDAO = new Question_setDAO(this);
        list.clear();
        list.addAll(setDAO.getAllSetByUserId(LoginActivity.USER_ID, Question_Set.TYPE_QUIZ));
        adapter.notifyDataSetChanged();
    }

    private void getWidget() {
        // LV
        lv = findViewById(R.id.lv_question_set);
        // Button
        view_question_set_btn_view = findViewById(R.id.view_question_set_btn_view);
        // IMG BTN
        view_question_set_btn_back = findViewById(R.id.view_question_set_btn_back);
        view_question_set_btn_menu = findViewById(R.id.view_question_set_btn_menu);
        // Edittext
        view_question_set_edt_name = findViewById(R.id.view_question_set_edt_name);
        view_question_set_edt_des = findViewById(R.id.view_question_set_edt_des);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Question_Set qs = list.get(position);
        view_question_set_edt_name.setText(qs.getSet_name());
        view_question_set_edt_des.setText(qs.getSet_des());
        id_selected = qs.getSet_id();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.view_question_set_btn_back) {
            getBack();
            return;
        }

        if (id == R.id.view_question_set_btn_view) {
            viewQuestion();
        }
    }

    public static final String DATA_QS_ID = "data_qs_id";

    private void viewQuestion() {
        if (id_selected != -1) {
            Intent intent = new Intent(this, ViewQuestions.class);
            intent.putExtra(DATA_QS_ID, id_selected);
            startActivity(intent);
        }
    }

    private void delQuestionSet() {
        if (setDAO.delQuestion_set(id_selected)) {
            refreshList();
        }
    }

    private void editQuestionSet(Question_Set qs) {
        if (id_selected != -1) {
            if (setDAO.editQuestion_set(qs, id_selected)) {
                refreshList();
            }
        }
    }

    private void addQuestionSet(Question_Set qs) {
        if (setDAO.addQuestion_set(qs)) {
            Toast.makeText(this, "SUCCESS", Toast.LENGTH_SHORT).show();
            refreshList();
        } else {
            Toast.makeText(this, "FAIL", Toast.LENGTH_SHORT).show();
        }
    }

    private void refreshList() {
        list.clear();
        list.addAll(setDAO.getAllSetByUserId(LoginActivity.USER_ID, Question_Set.TYPE_QUIZ));
        adapter.notifyDataSetChanged();
        id_selected = -1;
    }

    private void getBack() {
        finish();
    }
}