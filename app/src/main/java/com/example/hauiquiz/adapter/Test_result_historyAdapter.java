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
import com.example.hauiquiz.entity.Test_result_history;

import java.util.List;

public class Test_result_historyAdapter extends ArrayAdapter<Test_result_history> {
    private Activity context;
    private int layoutId;
    private List<Test_result_history> list;

    public Test_result_historyAdapter(Activity context, int layoutId, List<Test_result_history> list) {
        super(context, layoutId, list);
        this.context = context;
        this.layoutId = layoutId;
        this.list = list;
    }

    @SuppressLint({"ViewHolder", "SetTextI18n"})
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Test_result_history t = list.get(position);
        LayoutInflater inflater = context.getLayoutInflater();
        convertView = inflater.inflate(layoutId, null);

        TextView txt_set_id = convertView.findViewById(R.id.item_test_result_history_txt_set_id);
        TextView txt_set_name = convertView.findViewById(R.id.item_test_result_history_txt_set_name);
        TextView txt_score = convertView.findViewById(R.id.item_test_result_history_txt_score);
        TextView txt_comment = convertView.findViewById(R.id.item_test_result_history_txt_comment);
        TextView txt_time = convertView.findViewById(R.id.item_test_result_history_txt_time);

        txt_set_id.setText("QUIZZ" + t.getSet_id());
        txt_set_name.setText(t.getSet_name());
        txt_score.setText(t.getScore() + "");
        txt_comment.setText(t.getComment());
        txt_time.setText(t.getCompleted_time());

        return convertView;
    }
}
