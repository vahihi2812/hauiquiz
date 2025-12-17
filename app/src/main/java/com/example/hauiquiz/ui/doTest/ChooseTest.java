package com.example.hauiquiz.ui.doTest;

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

public class ChooseTest extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {
    private ListView lv;
    private ImageButton btn_back;
    private List<Question_Set> list;
    private QuestionSetAdapter adapter;
    private Question_setDAO qsDAO;

    @Override
    protected void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_test);

        getWidget();
        getData();
        setEvents();
    }

    private void setEvents() {
        lv.setOnItemClickListener(this);
        btn_back.setOnClickListener(this);
    }

    private void getData() {
        qsDAO = new Question_setDAO(this);
        //list = new ArrayList<>(qsDAO.getAllSetByUserType(Question_Set.TYPE_TEST));
        list = new ArrayList<>(qsDAO.getAllSetByUserTypeAndCompletedStatus(Question_Set.TYPE_TEST, LoginActivity.USER_ID));

        adapter = new QuestionSetAdapter(this, R.layout.item_question_set, list);
        lv.setAdapter(adapter);
    }

    private void getWidget() {
        lv = findViewById(R.id.choose_test_lv);
        btn_back = findViewById(R.id.choose_test_btn_back);
    }

    public static final String DATA_TEST = "data_test";
    public static final String DATA_TEST_BUNDLE = "data_test_bundle";

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Question_Set qs = list.get(position);
        Intent intent = new Intent(this, TestDetail.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(DATA_TEST, qs);
        intent.putExtra(DATA_TEST_BUNDLE, bundle);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.choose_test_btn_back) {
            getBack();
        }
    }

    private void getBack() {
        finish();
    }
}