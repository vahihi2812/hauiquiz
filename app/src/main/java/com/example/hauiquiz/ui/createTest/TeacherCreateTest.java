package com.example.hauiquiz.ui.createTest;

import android.annotation.SuppressLint;
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
import com.example.hauiquiz.ui.createQuiz.ViewQuestions;
import com.example.hauiquiz.ui.home.LoginActivity;
import com.example.hauiquiz.utils.DisplayMessageDialog;
import com.example.hauiquiz.utils.SystemTime;

import java.util.ArrayList;
import java.util.List;

public class TeacherCreateTest extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {
    private EditText edt_name, edt_des, edt_weight, edt_hour, edt_min;
    private Button btn_view;
    private ImageButton btn_back, btn_menu;
    private ListView lv;
    private QuestionSetAdapter adapter;
    private List<Question_Set> list;
    private Question_setDAO qsDAO;
    private int id_selected = -1;

    @Override
    protected void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_create_test);

        getWidget();
        getData();
        setEvents();
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        Question_Set qs;
        try {
            String name = edt_name.getText().toString();
            String des = edt_des.getText().toString();
            String dur = edt_hour.getText().toString() + ":" + edt_min.getText().toString();
            int weight = Integer.parseInt(edt_weight.getText().toString());
            if (name.isEmpty() || des.isEmpty() || dur.equals(":")) {
                DisplayMessageDialog.displayMessage(this, "DỮ LIỆU", "Không được để trống!");
                return true;
            }
            qs = new Question_Set(0, name, des, LoginActivity.LOGIN_TIME, Question_Set.TYPE_TEST,
                    weight, dur, LoginActivity.USERNAME, LoginActivity.USER_ID);
        } catch (Exception e) {
            DisplayMessageDialog.displayMessage(this, "DỮ LIỆU", "Chưa hợp lệ!");
            return true;
        }

        if (id == R.id.action_add) {
            addQuestionSet(qs);
        } else if (id == R.id.action_edit) {
            editQuestionSet(qs);
        } else if (id == R.id.action_delete) {
            delQuestionSet();
        } else if (id == R.id.teacher_create_test_btn_view) {
            viewQuestion();
        }

        return super.onContextItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu_crud, menu);
    }

    private void setEvents() {
        lv.setOnItemClickListener(this);
        btn_back.setOnClickListener(this);
        btn_view.setOnClickListener(this);
        registerForContextMenu(btn_menu);
    }

    private void getData() {
        list = new ArrayList<>();
        adapter = new QuestionSetAdapter(this, R.layout.item_question_set, list);
        lv.setAdapter(adapter);

        //DAO
        qsDAO = new Question_setDAO(this);

        list.clear();
        list.addAll(qsDAO.getAllSetByUserId(LoginActivity.USER_ID, Question_Set.TYPE_TEST));
        adapter.notifyDataSetChanged();
    }

    private void getWidget() {
        //LV
        lv = findViewById(R.id.teacher_create_test_lv);
        //EDT
        edt_name = findViewById(R.id.teacher_create_test_edt_name);
        edt_des = findViewById(R.id.teacher_create_test_edt_des);
        edt_weight = findViewById(R.id.teacher_create_test_edt_weight);
        edt_hour = findViewById(R.id.teacher_create_test_edt_hour);
        edt_min = findViewById(R.id.teacher_create_test_edt_minute);
        // BTN
        btn_back = findViewById(R.id.teacher_create_test_btn_back);
        btn_view = findViewById(R.id.teacher_create_test_btn_view);
        btn_menu = findViewById(R.id.teacher_create_test_btn_menu);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Question_Set qs = list.get(position);
        edt_name.setText(qs.getSet_name());
        edt_des.setText(qs.getSet_des());
        //Duration
        String[] arr = SystemTime.getHourAndMin(qs.getSet_duration());
        edt_hour.setText(arr[0]);
        edt_min.setText(arr[1]);
        // Weight
        edt_weight.setText(qs.getSet_weight() + "");
        id_selected = qs.getSet_id();
    }

    private void refreshList() {
        list.clear();
        list.addAll(qsDAO.getAllSetByUserId(LoginActivity.USER_ID, Question_Set.TYPE_TEST));
        adapter.notifyDataSetChanged();
        id_selected = -1;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.teacher_create_test_btn_back) {
            getBack();
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
        if (qsDAO.delQuestion_set(id_selected)) {
            refreshList();
        }
    }

    private void editQuestionSet(Question_Set qs) {
        if (id_selected != -1) {
            if (qsDAO.editQuestion_set(qs, id_selected)) {
                refreshList();
            }
        }
    }

    private void addQuestionSet(Question_Set qs) {
        if (qsDAO.addQuestion_set(qs)) {
            Toast.makeText(this, "SUCCESS", Toast.LENGTH_SHORT).show();
            refreshList();
        } else {
            Toast.makeText(this, "FAIL", Toast.LENGTH_SHORT).show();
        }
    }

    private void getBack() {
        finish();
    }
}