package com.example.hauiquiz.ui.testlinhtinh;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hauiquiz.R;
import com.example.hauiquiz.entity.Subject;

import java.util.ArrayList;
import java.util.List;

public class Test1Activity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ArrayAdapter<Subject> adapter;
    EditText test_edt1, test_edt2;
    ListView lv;
    List<Subject> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test1);

        getWidget();
        getData();
        setEvents();
    }

    private void setEvents() {
        lv.setOnItemClickListener(this);
    }

    private void getData() {
        list = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        lv.setAdapter(adapter);
        list.add(new Subject("1", "Sub 1", "Des", 1));
        list.add(new Subject("2", "Sub 2", "Des", 1));
        list.add(new Subject("3", "Sub 3", "Des", 1));
        adapter.notifyDataSetChanged();
    }

    private void getWidget() {
        lv = findViewById(R.id.lv_test);
        test_edt1 = findViewById(R.id.test_edt1);
        test_edt2 = findViewById(R.id.test_edt2);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Subject s = list.get(position);
        test_edt1.setText(s.getSubject_name());
        test_edt2.setText(s.getSubject_des());
    }
}