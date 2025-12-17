package com.example.hauiquiz.ui.chart;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hauiquiz.R;
import com.example.hauiquiz.dao.QuestionDAO;
import com.example.hauiquiz.dao.Test_resultDAO;
import com.example.hauiquiz.dao.UserDAO;
import com.example.hauiquiz.entity.Test_result;
import com.example.hauiquiz.entity.User;
import com.example.hauiquiz.utils.WorkWithNumber;
import com.example.hauiquiz.utils.WorkWithString;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;

public class UploadQuestionExcelFile extends AppCompatActivity implements View.OnClickListener {
    private Button btn_upload;
    private QuestionDAO questionDAO;

    @Override
    protected void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test1);

        getWidget();
        getData();
        setEvents();
    }

    private void setEvents() {
        btn_upload.setOnClickListener(this);
    }

    private void getData() {
        questionDAO = new QuestionDAO(this);
    }

    private void getWidget() {
        btn_upload = findViewById(R.id.btn_upload_file);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_upload_file) {
            //uploadFile();
            //insertRecords();
            insertTS();
        }
    }

    private void insertTS() {
        Test_resultDAO tsDAO = new Test_resultDAO(this);
        Test_result ts;
        for (int i = 4; i <= 55; i++) {
            ts = new Test_result(0, WorkWithNumber.randomScore(), "8:40 - 14/12/2025", "", 9, i);
            tsDAO.addTest_result(ts);
        }
    }

    private void insertRecords() {
        // 2022601218
        String fullname;
        String username;
        UserDAO userDAO = new UserDAO(this);
        for (int i = 0; i < 20; i++) {
            username = "gv0" + i;
            fullname = WorkWithString.randomName();
            userDAO.addUser(new User(0, username, "123", fullname, 2));
        }
    }

    private void uploadFile() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType(
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
        );
        startActivityForResult(intent, 101);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101 && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            readExcelAndInsert(uri);
        }
    }

    private void readExcelAndInsert(Uri uri) {
        new Thread(() -> {
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                assert inputStream != null;
                Workbook workbook = new XSSFWorkbook(inputStream);
                Sheet sheet = workbook.getSheetAt(0);

                for (int i = 1; i <= sheet.getLastRowNum(); i++) { // bo co header
                    Row row = sheet.getRow(i);
                    if (row == null) continue;

//                    String content = row.getCell(0).getStringCellValue();
//                    int md = (int) row.getCell(6).getNumericCellValue();
                }
                workbook.close();
                inputStream.close();
            } catch (Exception ignored) {
            }
        }).start();
    }
}