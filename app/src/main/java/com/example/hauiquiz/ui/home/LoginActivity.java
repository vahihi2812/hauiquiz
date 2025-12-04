package com.example.hauiquiz.ui.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hauiquiz.R;
import com.example.hauiquiz.dao.UserDAO;
import com.example.hauiquiz.entity.Role;
import com.example.hauiquiz.entity.User;
import com.example.hauiquiz.utils.DisplayMessageDialog;
import com.example.hauiquiz.utils.SystemTime;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edt_username, edt_password;
    Button btn_login;
    UserDAO userDAO;
    public static int USER_ID;
    public static String USERNAME;
    public static String LOGIN_TIME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getWidget();
        getData();
        setEvents();
    }

    @SuppressLint("SetTextI18n")
    private void getData() {
        userDAO = new UserDAO(this);
        // FAKE
        edt_username.setText("user1");
        edt_password.setText("123");
    }

    private void setEvents() {
        edt_username.requestFocus();
        btn_login.setOnClickListener(this);
    }

    private void getWidget() {
        edt_username = findViewById(R.id.edt_username);
        edt_password = findViewById(R.id.edt_password);
        btn_login = findViewById(R.id.btn_login);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_login) {
            doLogin();
        }
    }

    private void doLogin() {
        String username = edt_username.getText().toString().trim();
        String pass = edt_password.getText().toString().trim();

        if (username.isEmpty() || pass.isEmpty()) {
            DisplayMessageDialog.displayMessage(LoginActivity.this, "Đăng nhập không thành công!", "Tên đăng nhập và mật khẩu không được để trống!");
            return;
        }

        User user = userDAO.checkLogin(username, pass);

        if (user == null) {
            DisplayMessageDialog.displayMessage(LoginActivity.this,
                    "Đăng nhập thất bại",
                    "Tên đăng nhập hoặc mật khẩu không chính xác!");
            return;
        }

        USER_ID = user.getUser_id();
        USERNAME = user.getUsername();
        LOGIN_TIME = SystemTime.getDate();

        if (user.getRole_id() == Role.ROLE_TEACHER) {
            startActivity(new Intent(LoginActivity.this, TeacherHome.class));
            finish();
        } else if (user.getRole_id() == Role.ROLE_STUDENT) {
            startActivity(new Intent(LoginActivity.this, StudentHome.class));
            finish();
        } else if (user.getRole_id() == Role.ROLE_ADMIN) {
            DisplayMessageDialog.displayMessage(LoginActivity.this, "Đăng nhập thất bại", "Chức năng ADMIN đang được cập nhật!");
        }
    }


//    private void save(User user) {
//        SharedPreferences preferences = getSharedPreferences(PRE_NAME, MODE_PRIVATE);
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.putInt(USER_ID, user.getUser_id());
//        editor.putString(USERNAME, user.getUsername());
//        editor.apply();
//    }

//    public int getUserId() {
//        SharedPreferences preferences = getSharedPreferences(PRE_NAME, MODE_PRIVATE);
//        return preferences.getInt(USER_ID, 0);
//    }
//
//    public String getUsername() {
//        SharedPreferences preferences = getSharedPreferences(PRE_NAME, MODE_PRIVATE);
//        return preferences.getString(USERNAME, "");
//    }
}