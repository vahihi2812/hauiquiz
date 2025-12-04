package com.example.hauiquiz.ui.forum;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hauiquiz.R;
import com.example.hauiquiz.adapter.SolutionAdapter;
import com.example.hauiquiz.dao.SolutionDAO;
import com.example.hauiquiz.entity.Problem;
import com.example.hauiquiz.entity.Solution;
import com.example.hauiquiz.ui.home.LoginActivity;
import com.example.hauiquiz.utils.DisplayMessageDialog;
import com.example.hauiquiz.utils.SystemTime;

import java.util.ArrayList;
import java.util.List;

public class ViewSolutions extends AppCompatActivity implements View.OnClickListener {
    private ListView lv;
    private ImageButton btn_back, btn_send;
    private EditText edt_content;
    private List<Solution> list;
    private SolutionAdapter adapter;
    private SolutionDAO solutionDAO;
    private int problem_id = -1;

    @Override
    protected void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_solutions);

        getWidget();
        getData();
        setEvents();
    }

    private void setEvents() {
        btn_send.setOnClickListener(this);
        btn_back.setOnClickListener(this);
    }

    private void getData() {
        solutionDAO = new SolutionDAO(this);
        try {
            Intent intent = getIntent();
            assert intent != null;
            Bundle bundle = intent.getBundleExtra(ViewProblems.VIEW_PROBLEMS_PROBLEM_BUNDLE);
            assert bundle != null;
            Problem p = (Problem) bundle.getSerializable(ViewProblems.VIEW_PROBLEMS_PROBLEM_OBJECT);
            assert p != null;
            problem_id = p.getProblem_id();
        } catch (Exception e) {
            DisplayMessageDialog.displayMessage(this, "ERROR", "ERROR");
            return;
        }
        if (problem_id != -1) {
            list = new ArrayList<>(solutionDAO.getSolutionsByProblemId(problem_id));
            adapter = new SolutionAdapter(this, R.layout.item_solution, list);
            lv.setAdapter(adapter);
        }
    }

    private void getWidget() {
        lv = findViewById(R.id.view_solutions_lv);
        btn_back = findViewById(R.id.view_solutions_btn_back);
        btn_send = findViewById(R.id.view_solutions_btn_send);
        edt_content = findViewById(R.id.view_solutions_edt_content);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.view_solutions_btn_back) {
            getBack();
            return;
        }

        Solution s;

        try {
            String content = edt_content.getText().toString();
            if (content.isEmpty()) {
                DisplayMessageDialog.displayMessage(this, "COMMENT", "EMPTY");
                return;
            }
            s = new Solution(0, content,
                    SystemTime.getTime() + " - " + LoginActivity.LOGIN_TIME,
                    LoginActivity.USERNAME,
                    problem_id);
        } catch (Exception e) {
            DisplayMessageDialog.displayMessage(this, "COMMENT", "ERROR");
            return;
        }

        if (id == R.id.view_solutions_btn_send) {
            addNewSolution(s);
        }
    }

    private void addNewSolution(Solution s) {
        if (solutionDAO.addSolution(s)) {
            refreshList();
            DisplayMessageDialog.displayMessage(this, "UPLOAD", "SUCCESS");
        }
    }

    private void refreshList() {
        if (problem_id != -1) {
            list.clear();
            list.addAll(solutionDAO.getSolutionsByProblemId(problem_id));
            adapter.notifyDataSetChanged();
        }
    }

    private void getBack() {
        finish();
    }
}