package com.example.hauiquiz.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.hauiquiz.R;
import com.example.hauiquiz.entity.Problem;
import com.example.hauiquiz.ui.home.LoginActivity;
import com.example.hauiquiz.utils.WorkWithString;

import java.util.List;

public class ProblemAdapter extends ArrayAdapter<Problem> {
    private Activity context;
    private int layoutId;
    private List<Problem> list;

    public ProblemAdapter(Activity context, int layoutId, List<Problem> list) {
        super(context, layoutId, list);
        this.context = context;
        this.layoutId = layoutId;
        this.list = list;
    }

    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Problem p = list.get(position);
        LayoutInflater inflater = context.getLayoutInflater();
        convertView = inflater.inflate(layoutId, null);

        TextView item_problem_txt_username = convertView.findViewById(R.id.item_problem_txt_username);
        TextView item_problem_txt_upload_time = convertView.findViewById(R.id.item_problem_txt_upload_time);
        TextView item_problem_txt_name = convertView.findViewById(R.id.item_problem_txt_name);
        TextView item_problem_txt_content = convertView.findViewById(R.id.item_problem_txt_content);

        String us = p.getUsername();
        item_problem_txt_username.setText(us.equals(LoginActivity.USERNAME) ? "Bạn" : us);
        item_problem_txt_upload_time.setText(p.getProblem_upload_time());
        item_problem_txt_name.setText(p.getProblem_name());
        item_problem_txt_content.setText(WorkWithString.shortenString(p.getProblem_content()));

        return convertView;
    }
}
