package com.example.hauiquiz.ui.createQuiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hauiquiz.R;
import com.example.hauiquiz.adapter.QuestionAdapter;
import com.example.hauiquiz.dao.QuestionDAO;
import com.example.hauiquiz.entity.Question;
import com.example.hauiquiz.entity.Question_level;
import com.example.hauiquiz.utils.DisplayMessageDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ViewQuestions extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {
    private ListView lv;
    private Spinner spn_choice, spn_level;
    private EditText view_questions_edt_content, view_questions_edt_first_choice, view_questions_edt_second_choice,
            view_questions_edt_third_choice, view_questions_edt_fourth_choice, view_questions_edt_explain;
    private Button view_questions_btn_add, view_questions_btn_edit, view_questions_btn_del, view_questions_btn_back;
    private QuestionAdapter adapter;
    private List<Question> list;
    QuestionDAO questionDAO;
    private int id_selected = -1;
    private int qs_id;
    ArrayAdapter<String> choiceAdapter, levelAdapter;
    List<String> choices, levels;

    @Override
    protected void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_questions);

        getWidget();
        getData();
        setEvents();
    }

    private void setEvents() {
        lv.setOnItemClickListener(this);
        view_questions_btn_add.setOnClickListener(this);
        view_questions_btn_edit.setOnClickListener(this);
        view_questions_btn_del.setOnClickListener(this);
        view_questions_btn_back.setOnClickListener(this);
    }

    private void getWidget() {
        // LV
        lv = findViewById(R.id.lv_question);
        // SPN
        spn_choice = findViewById(R.id.view_questions_spn_choice);
        spn_level = findViewById(R.id.view_questions_spn_level);
        // EDT
        view_questions_edt_content = findViewById(R.id.view_questions_edt_content);
        view_questions_edt_first_choice = findViewById(R.id.view_questions_edt_first_choice);
        view_questions_edt_second_choice = findViewById(R.id.view_questions_edt_second_choice);
        view_questions_edt_third_choice = findViewById(R.id.view_questions_edt_third_choice);
        view_questions_edt_fourth_choice = findViewById(R.id.view_questions_edt_fourth_choice);
        view_questions_edt_explain = findViewById(R.id.view_questions_edt_explain);
        //BTN
        view_questions_btn_add = findViewById(R.id.view_questions_btn_add);
        view_questions_btn_edit = findViewById(R.id.view_questions_btn_edit);
        view_questions_btn_del = findViewById(R.id.view_questions_btn_del);
        view_questions_btn_back = findViewById(R.id.view_questions_btn_back);
    }

    private void getData() {
        list = new ArrayList<>();
        adapter = new QuestionAdapter(this, R.layout.item_question, list);
        lv.setAdapter(adapter);

        //setup Spinner
        choices = new ArrayList<>();
        choices.add("1");
        choices.add("2");
        choices.add("3");
        choices.add("4");
        levels = new ArrayList<>();
        levels.addAll(Arrays.asList(Question_level.level));

        // adapter
        choiceAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, choices);
        choiceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        levelAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, levels);
        levelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_choice.setAdapter(choiceAdapter);
        spn_level.setAdapter(levelAdapter);

        questionDAO = new QuestionDAO(this);

        // get Intent
        Intent intent = getIntent();
        qs_id = intent.getIntExtra(ViewQuestionSet.DATA_QS_ID, 0);
        Toast.makeText(this, "id = " + qs_id, Toast.LENGTH_SHORT).show();
        if (qs_id != 0) {
            list.clear();
            list.addAll(questionDAO.getQuestionsBySetId(qs_id));
            adapter.notifyDataSetChanged();
        }
    }

    private void refreshList() {
        list.clear();
        list.addAll(questionDAO.getQuestionsBySetId(qs_id));
        adapter.notifyDataSetChanged();
        id_selected = -1;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Question q = list.get(position);

        //edt
        view_questions_edt_content.setText(q.getQuestion_content());
        view_questions_edt_first_choice.setText(q.getFirst_choice());
        view_questions_edt_second_choice.setText(q.getSecond_choice());
        view_questions_edt_third_choice.setText(q.getThird_choice());
        view_questions_edt_fourth_choice.setText(q.getFourth_choice());
        view_questions_edt_explain.setText(q.getQuestion_explain());

        // spn
        spn_choice.setSelection(q.getCorrect_answer() - 1);
        spn_level.setSelection(q.getQuestion_level() - 1);

        id_selected = q.getQuestion_id();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.view_questions_btn_back) {
            getBack();
            return;
        }

        Question q;
        try {
            String content = view_questions_edt_content.getText().toString();
            String first_choice = view_questions_edt_first_choice.getText().toString();
            String second_choice = view_questions_edt_second_choice.getText().toString();
            String third_choice = view_questions_edt_third_choice.getText().toString();
            String fourth_choice = view_questions_edt_fourth_choice.getText().toString();
            String explain = view_questions_edt_explain.getText().toString();
            int choice = spn_choice.getSelectedItemPosition() + 1;
            int level = spn_level.getSelectedItemPosition() + 1;

            if (content.isEmpty() || first_choice.isEmpty() || second_choice.isEmpty() || third_choice.isEmpty() ||
                    fourth_choice.isEmpty() || explain.isEmpty()) {
                DisplayMessageDialog.displayMessage(this, "DATA", "EMPTY!");
                return;
            }

            q = new Question(0, content, first_choice, second_choice, third_choice, fourth_choice, choice, level, explain, qs_id);

        } catch (Exception e) {
            DisplayMessageDialog.displayMessage(this, "DATA", "ERROR!");
            return;
        }

        if (id == R.id.view_questions_btn_add) {
            addQuestion(q);
        } else if (id == R.id.view_questions_btn_edit) {
            editQuestion(q);
        } else if (id == R.id.view_questions_btn_del) {
            delQuestion();
        }
    }

    private void delQuestion() {
        if (id_selected != -1) {
            if (questionDAO.delQuestion(id_selected)) {
                Toast.makeText(this, "SUCC", Toast.LENGTH_SHORT).show();
                refreshList();
            }
        }
    }

    private void editQuestion(Question q) {
        if (id_selected != -1) {
            if (questionDAO.editQuestion(q, id_selected)) {
                Toast.makeText(this, "SUCC", Toast.LENGTH_SHORT).show();
                refreshList();
            }
        }
    }

    private void addQuestion(Question q) {
        if (questionDAO.addQuestion(q)) {
            Toast.makeText(this, "SUCC", Toast.LENGTH_SHORT).show();
            refreshList();
        }
    }

    private void getBack() {
        finish();
    }
}