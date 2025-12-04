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
import com.example.hauiquiz.entity.Solution;
import com.example.hauiquiz.ui.home.LoginActivity;

import java.util.List;

public class SolutionAdapter extends ArrayAdapter<Solution> {
    private Activity context;
    private int layoutId;
    private List<Solution> list;

    public SolutionAdapter(Activity context, int layoutId, List<Solution> list) {
        super(context, layoutId, list);
        this.context = context;
        this.layoutId = layoutId;
        this.list = list;
    }

    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Solution s = list.get(position);
        LayoutInflater inflater = context.getLayoutInflater();
        convertView = inflater.inflate(layoutId, null);

        TextView item_solution_txt_username = convertView.findViewById(R.id.view_solution_txt_username);
        TextView item_solution_txt_content = convertView.findViewById(R.id.view_solution_txt_content);
        TextView item_solution_txt_time = convertView.findViewById(R.id.view_solution_txt_time);

        String us = s.getUsername();
        item_solution_txt_username.setText(us.equals(LoginActivity.USERNAME) ? "Bạn" : us);
        item_solution_txt_content.setText(s.getSolution_content());
        item_solution_txt_time.setText(s.getSolution_upload_time());

        return convertView;
    }
}
