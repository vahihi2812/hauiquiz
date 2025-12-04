package com.example.hauiquiz.ui.forum;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hauiquiz.R;
import com.example.hauiquiz.adapter.ProblemAdapter;
import com.example.hauiquiz.dao.ProblemDAO;
import com.example.hauiquiz.entity.Problem;
import com.example.hauiquiz.utils.DisplayMessageDialog;

import java.util.ArrayList;
import java.util.List;

public class ViewProblems extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private ListView lv;
    private ImageButton btn_back, btn_upload;
    private List<Problem> list;
    private ProblemAdapter adapter;
    private ProblemDAO problemDAO;
    private ActivityResultLauncher<Intent> launcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_problems);

        getWidget();
        getData();
        setLauncher();
        setEvents();
    }

    public static final int CODE_UPLOAD_POST = 1;
    public static final String DATA_UPLOAD_BUNDLE = "data_upload_bundle";
    public static final String DATA_UPLOAD_PROBLEM = "data_upload_problem";

    private void setLauncher() {
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), o -> {
            if (o.getResultCode() == CODE_UPLOAD_POST) {
                try {
                    Intent data = o.getData();
                    assert data != null;
                    Bundle bundle = data.getBundleExtra(DATA_UPLOAD_BUNDLE);
                    assert bundle != null;
                    Problem p = (Problem) bundle.getSerializable(DATA_UPLOAD_PROBLEM);
                    assert p != null;
                    addProblem(p);
                } catch (Exception e) {
                    DisplayMessageDialog.displayMessage(ViewProblems.this, "DATA", "ERROR");
                }
            }
        });
    }

    private void addProblem(Problem p) {
        if (problemDAO.addProblem(p)) {
            refreshList();
            DisplayMessageDialog.displayMessage(this, "Đăng bài", "Đăng bài thành công!");
        }
    }

    private void setEvents() {
        btn_back.setOnClickListener(this);
        btn_upload.setOnClickListener(this);
        lv.setOnItemClickListener(this);
    }

    private void getData() {
        problemDAO = new ProblemDAO(this);
        list = new ArrayList<>(problemDAO.getAllProblems());
        adapter = new ProblemAdapter(this, R.layout.item_problem, list);
        lv.setAdapter(adapter);
    }

    private void getWidget() {
        // LV
        lv = findViewById(R.id.view_problems_lv);
        // IMG BTN
        btn_back = findViewById(R.id.view_problems_btn_back);
        btn_upload = findViewById(R.id.view_problems_btn_upload_post);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.view_problems_btn_back) {
            getBack();
        } else if (id == R.id.view_problems_btn_upload_post) {
            uploadPost();
        }
    }

    private void uploadPost() {
        launcher.launch(new Intent(this, UploadProblem.class));
    }

    private void getBack() {
        finish();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        moveToSolutionsScreen(list.get(position));
    }

    public static final String VIEW_PROBLEMS_PROBLEM_BUNDLE = "view_problems_problem_bundle";
    public static final String VIEW_PROBLEMS_PROBLEM_OBJECT = "view_problems_problem_object";

    private void moveToSolutionsScreen(Problem p) {
        Intent intent = new Intent(this, ViewSolutions.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(VIEW_PROBLEMS_PROBLEM_OBJECT, p);
        intent.putExtra(VIEW_PROBLEMS_PROBLEM_BUNDLE, bundle);
        startActivity(intent);
    }

    private void refreshList() {
        list.clear();
        list.addAll(problemDAO.getAllProblems());
        adapter.notifyDataSetChanged();
    }
}