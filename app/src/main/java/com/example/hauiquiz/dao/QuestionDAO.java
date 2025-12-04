package com.example.hauiquiz.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.hauiquiz.database.DatabaseUtils;
import com.example.hauiquiz.entity.Question;

import java.util.ArrayList;
import java.util.List;

public class QuestionDAO {
    private static final String TABLE_NAME = "question";
    private static final String COL_QUESTION_ID = "question_id";
    private static final String COL_QUESTION_CONTENT = "question_content";
    private static final String COL_FIRST_CHOICE = "first_choice";
    private static final String COL_SECOND_CHOICE = "second_choice";
    private static final String COL_THIRD_CHOICE = "third_choice";
    private static final String COL_FOURTH_CHOICE = "fourth_choice";
    private static final String COL_CORRECT_ANSWER = "correct_answer";
    private static final String COL_QUESTION_LEVEL = "question_level";
    private static final String COL_QUESTION_EXPLAIN = "question_explain";
    private static final String COL_SET_ID = "set_id";
    private final DatabaseUtils databaseUtils;

    public QuestionDAO(Context context) {
        databaseUtils = DatabaseUtils.getInstance(context);
    }

    public List<Question> getQuestionsBySetId(int setId) {
        SQLiteDatabase db = databaseUtils.getReadableDatabase();
        try (Cursor cursor = db.query(TABLE_NAME, null, COL_SET_ID + " = ?", new String[]{setId + ""}, null, null, null)) {
            if (cursor.moveToFirst()) {
                List<Question> list = new ArrayList<>();
                while (!cursor.isAfterLast()) {
                    Question q = new Question(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                            cursor.getString(3), cursor.getString(4), cursor.getString(5),
                            cursor.getInt(6), cursor.getInt(7), cursor.getString(8), setId);
                    list.add(q);
                    cursor.moveToNext();
                }
                return list;
            }
        } catch (Exception e) {
            return List.of();
        }
        return List.of();
    }

    public boolean addQuestion(Question q) {
        SQLiteDatabase db = databaseUtils.getWritableDatabase();
        long res;
        try {
            res = db.insert(TABLE_NAME, null, toContentValues(q));
            if (res > 0) return true;
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    public boolean delQuestion(int q_id) {
        SQLiteDatabase db = databaseUtils.getWritableDatabase();
        long res;
        try {
            res = db.delete(TABLE_NAME, COL_QUESTION_ID + " = ?", new String[]{q_id + ""});
            if (res > 0) return true;
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    public boolean editQuestion(Question q, int q_id) {
        SQLiteDatabase db = databaseUtils.getWritableDatabase();
        long res;
        try {
            res = db.update(TABLE_NAME, toContentValues(q),
                    COL_QUESTION_ID + " = ?",
                    new String[]{q_id + ""});
            if (res > 0) return true;
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    private ContentValues toContentValues(Question q) {
        ContentValues values = new ContentValues();
        values.put(COL_QUESTION_CONTENT, q.getQuestion_content());
        values.put(COL_FIRST_CHOICE, q.getFirst_choice());
        values.put(COL_SECOND_CHOICE, q.getSecond_choice());
        values.put(COL_THIRD_CHOICE, q.getThird_choice());
        values.put(COL_FOURTH_CHOICE, q.getFourth_choice());
        values.put(COL_QUESTION_EXPLAIN, q.getQuestion_explain());
        values.put(COL_CORRECT_ANSWER, q.getCorrect_answer());
        values.put(COL_QUESTION_LEVEL, q.getQuestion_level());
        values.put(COL_SET_ID, q.getSet_id());
        return values;
    }
}
