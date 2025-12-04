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
import com.example.hauiquiz.entity.Question;
import com.example.hauiquiz.utils.WorkWithString;

import java.util.List;

public class QuestionAdapter extends ArrayAdapter<Question> {
    private Activity context;
    private int layoutId;
    private List<Question> list;
    public QuestionAdapter(Activity context, int layoutId, List<Question> list) {
        super(context, layoutId, list);
        this.context = context;
        this.layoutId = layoutId;
        this.list = list;
    }

    @SuppressLint({"ViewHolder", "SetTextI18n"})
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Question q = list.get(position);
        LayoutInflater inflater = context.getLayoutInflater();
        convertView = inflater.inflate(layoutId, null);

        TextView item_question_txt_stt = convertView.findViewById(R.id.item_question_txt_stt);
        TextView item_question_txt_content = convertView.findViewById(R.id.item_question_txt_content);

        item_question_txt_stt.setText("Câu " + (position + 1));
        item_question_txt_content.setText(WorkWithString.shortenString(q.getQuestion_content()));

        return convertView;
    }
}
