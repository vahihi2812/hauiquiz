package com.example.hauiquiz.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.hauiquiz.R;
import com.example.hauiquiz.entity.StudentResult;

import java.util.List;

public class StudentResultAdapter extends ArrayAdapter<StudentResult> {
    private Context context;
    private int resource;
    private List<StudentResult> list;

    public StudentResultAdapter(@NonNull Context context, int resource, @NonNull List<StudentResult> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.list = objects;
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(resource, parent, false);
        }

        TextView txtName = convertView.findViewById(R.id.txt_student_name);
        TextView txtScore = convertView.findViewById(R.id.txt_student_score);
        TextView txtTime = convertView.findViewById(R.id.txt_submit_time);

        StudentResult result = list.get(position);
        if (result != null) {
            txtName.setText(result.getStudentName());
            txtScore.setText(result.getScore() + " điểm");
            txtTime.setText("Nộp lúc: " + result.getSubmitTime());
        }

        return convertView;
    }
}