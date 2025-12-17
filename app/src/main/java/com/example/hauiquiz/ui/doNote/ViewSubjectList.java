package com.example.hauiquiz.ui.doNote;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hauiquiz.R;
import com.example.hauiquiz.dao.SubjectDAO;
import com.example.hauiquiz.entity.Subject;
import com.example.hauiquiz.ui.home.LoginActivity;
import com.example.hauiquiz.utils.DisplayMessageDialog;

import java.util.ArrayList;
import java.util.List;

public class ViewSubjectList extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {
    private ListView lv_subject;
    private EditText view_subject_list_txt_des, view_subject_list_txt_name, view_subject_list_edt_share_id, view_subject_list_txt_id;
    private Button btn_add_share, btn_get_note;
    private ImageButton btn_back, btn_menu;
    private ArrayAdapter<Subject> adapter;
    private List<Subject> list;
    SubjectDAO subjectDAO;

    @Override
    protected void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_subject_list);

        getWidget();
        getData();
        setEvents();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu_crud, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        // Lấy id của item được chọn trong context menu
        int id = item.getItemId();
        // Lấy dữ liệu từ các EditText trên giao diện
        String s_id = view_subject_list_txt_id.getText().toString();
        String name = view_subject_list_txt_name.getText().toString();
        String des  = view_subject_list_txt_des.getText().toString();
        // Kiểm tra dữ liệu đầu vào
        // Không cho phép các trường thông tin bị bỏ trống
        if (s_id.isEmpty() || name.isEmpty() || des.isEmpty()) {
            // Hiển thị thông báo lỗi khi dữ liệu không hợp lệ
            DisplayMessageDialog.displayMessage(
                    ViewSubjectList.this,"Dữ liệu không hợp lệ!",
                    "Hãy nhập đầy đủ các trường"
            );
            // Dừng xử lý sự kiện
            return true;
        }
        // Khởi tạo đối tượng Subject khi dữ liệu hợp lệ
        Subject s = new Subject(s_id, name, des, LoginActivity.USER_ID);
        // Xử lý hành động tương ứng với lựa chọn trong context menu
        if (id == R.id.action_add) {
            // Thực hiện chức năng thêm mới môn học
            addSubject(s);
            return true;
        } else if (id == R.id.action_edit) {
            // Thực hiện chức năng chỉnh sửa thông tin môn học
            editSubject(s);
            return true;
        } else if (id == R.id.action_delete) {
            // Thực hiện chức năng xóa môn học theo mã môn
            delSubject(s_id);
            return true;
        }
        // Gọi xử lý mặc định nếu không có hành động nào phù hợp
        return super.onContextItemSelected(item);
    }

    private void setEvents() {
        lv_subject.setOnItemClickListener(this);
        registerForContextMenu(btn_menu);
        btn_back.setOnClickListener(this);
        btn_add_share.setOnClickListener(this);
        btn_get_note.setOnClickListener(this);
    }

    private void getData() {
        list = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        lv_subject.setAdapter(adapter);

        subjectDAO = new SubjectDAO(this);
        list.clear();
        list.addAll(subjectDAO.getAllSubjects(LoginActivity.USER_ID));
        adapter.notifyDataSetChanged();
    }

    private void getWidget() {
        lv_subject = findViewById(R.id.lv1);
        view_subject_list_txt_des = findViewById(R.id.view_subject_list_txt_des);
        view_subject_list_txt_name = findViewById(R.id.view_subject_list_txt_name);
        view_subject_list_edt_share_id = findViewById(R.id.view_subject_list_edt_share_id);
        view_subject_list_txt_id = findViewById(R.id.view_subject_list_txt_id);
        btn_add_share = findViewById(R.id.view_subject_list_btn_add_share);
        btn_back = findViewById(R.id.view_subject_list_btn_back);
        btn_get_note = findViewById(R.id.view_subject_list_btn_get_note);
        btn_menu = findViewById(R.id.view_subject_list_btn_menu);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Subject s = list.get(position);
        view_subject_list_txt_id.setText(s.getSubject_id());
        view_subject_list_txt_name.setText(s.getSubject_name());
        view_subject_list_txt_des.setText(s.getSubject_des());
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.view_subject_list_btn_back) {
            getBack();
            return;
        }

        String share_id = view_subject_list_edt_share_id.getText().toString();
        if (id == R.id.view_subject_list_btn_add_share) {
            addShareSubject(share_id);
            return;
        }

        String s_id = view_subject_list_txt_id.getText().toString();

        if (s_id.isEmpty()) {
            DisplayMessageDialog.displayMessage(ViewSubjectList.this, "Dữ liệu không hợp lệ!",
                    "Hãy nhập đầy đủ các trường");
            return;
        }

        if (id == R.id.view_subject_list_btn_get_note) {
            getNotes(s_id);
        }
    }

    private void addShareSubject(String shareId) {
        if (shareId.isEmpty()) {
            DisplayMessageDialog.displayMessage(this, "DỮ LIỆU", "Mã chia sẻ chủ đề không được để trống");
            return;
        }
        if (subjectDAO.addSharedSubject(shareId)) {
            refreshListView();
            DisplayMessageDialog.displayMessage(this, "CHIA SẺ", "Thành công");
        } else {
            DisplayMessageDialog.displayMessage(this, "CHIA SẺ", "Thất bại" + LoginActivity.USER_ID);
        }
    }

    private void getBack() {
        finish();
    }

    private void delSubject(String subjectId) {
        if (subjectDAO.delSubject(subjectId)) {
            refreshListView();
        }
    }

    private void editSubject(Subject s) {
        if (subjectDAO.editSubject(s)) {
            refreshListView();
        }
    }

    private void addSubject(Subject s) {
        if (subjectDAO.addSubject(s)) {
            refreshListView();
        } else {
            Toast.makeText(this, "Fail", Toast.LENGTH_SHORT).show();
        }
    }

    private void refreshListView() {
        list.clear();
        list.addAll(subjectDAO.getAllSubjects(LoginActivity.USER_ID));
        adapter.notifyDataSetChanged();
    }

    public static final String DATA_SUBJECT_ID = "data_subject_id";

    private void getNotes(String s_id) {
        Intent intent = new Intent(this, ViewNoteList.class);
        intent.putExtra(DATA_SUBJECT_ID, s_id);
        startActivity(intent);
    }
}