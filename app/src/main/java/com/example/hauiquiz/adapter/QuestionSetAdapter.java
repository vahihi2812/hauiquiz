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
import com.example.hauiquiz.entity.Question_Set;

import java.util.List;

public class QuestionSetAdapter extends ArrayAdapter<Question_Set> {
    private Activity context;
    private int layoutId;
    private List<Question_Set> list;

    public QuestionSetAdapter(Activity context, int layoutId, List<Question_Set> list) {
        super(context, layoutId, list);
        this.context = context;
        this.layoutId = layoutId;
        this.list = list;
    }

    @SuppressLint({"ViewHolder", "SetTextI18n"})
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Question_Set qs = list.get(position);
        LayoutInflater inflater = context.getLayoutInflater();
        convertView = inflater.inflate(layoutId, null);

        TextView item_qs_id = convertView.findViewById(R.id.item_qs_id);
        TextView item_qs_name = convertView.findViewById(R.id.item_qs_name);
        TextView item_qs_time = convertView.findViewById(R.id.item_qs_time);

        item_qs_id.setText("QS" + qs.getSet_id());
        item_qs_name.setText(qs.getSet_name());
        item_qs_time.setText("Cập nhật gần đây nhất: " + qs.getSet_created_at());

        return convertView;
    }
}
