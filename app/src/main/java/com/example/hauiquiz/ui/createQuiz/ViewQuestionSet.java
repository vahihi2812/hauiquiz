package com.example.hauiquiz.ui.createQuiz;

import android.content.Intent;
import android.os.Bundle;
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
    private Button view_question_set_btn_add, view_question_set_btn_edit, view_question_set_btn_del, view_question_set_btn_view;
    ImageButton view_question_set_btn_back;
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

    private void setEvents() {
        // LV
        lv.setOnItemClickListener(this);
        // Button
        view_question_set_btn_add.setOnClickListener(this);
        view_question_set_btn_edit.setOnClickListener(this);
        view_question_set_btn_del.setOnClickListener(this);
        view_question_set_btn_view.setOnClickListener(this);
        view_question_set_btn_back.setOnClickListener(this);
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
        view_question_set_btn_add = findViewById(R.id.view_question_set_btn_add);
        view_question_set_btn_edit = findViewById(R.id.view_question_set_btn_edit);
        view_question_set_btn_del = findViewById(R.id.view_question_set_btn_del);
        view_question_set_btn_view = findViewById(R.id.view_question_set_btn_view);
        // IMG BTN
        view_question_set_btn_back = findViewById(R.id.view_question_set_btn_back);
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

        Question_Set qs;
        try {
            String name = view_question_set_edt_name.getText().toString();
            String des = view_question_set_edt_des.getText().toString();
            if (name.isEmpty() || des.isEmpty()) {
                DisplayMessageDialog.displayMessage(this, "DỮ LIỆU", "Không được để trống!");
                return;
            }
            qs = new Question_Set(0, name, des, LoginActivity.LOGIN_TIME, Question_Set.TYPE_QUIZ,
                    0, "", LoginActivity.USERNAME, LoginActivity.USER_ID);
        } catch (Exception e) {
            DisplayMessageDialog.displayMessage(this, "DỮ LIỆU", "Chưa hợp lệ!");
            return;
        }

        if (id == R.id.view_question_set_btn_add) {
            addQuestionSet(qs);
        } else if (id == R.id.view_question_set_btn_edit) {
            editQuestionSet(qs);
        } else if (id == R.id.view_question_set_btn_del) {
            delQuestionSet();
        } else if (id == R.id.view_question_set_btn_view) {
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