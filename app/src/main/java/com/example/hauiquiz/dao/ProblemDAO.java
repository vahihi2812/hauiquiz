package com.example.hauiquiz.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.hauiquiz.database.DatabaseUtils;
import com.example.hauiquiz.entity.Problem;

import java.util.ArrayList;
import java.util.List;

public class ProblemDAO {
    private static final String TABLE_NAME = "problem";
    private static final String COL_PROBLEM_ID = "problem_id";
    private static final String COL_PROBLEM_NAME = "problem_name";
    private static final String COL_PROBLEM_CONTENT = "problem_content";
    private static final String COL_PROBLEM_UPLOAD_TIME = "problem_upload_time";
    private static final String COL_USERNAME = "username";

    private final DatabaseUtils databaseUtils;

    public ProblemDAO(Context context) {
        databaseUtils = DatabaseUtils.getInstance(context);
    }

    public List<Problem> getAllProblems() {
        SQLiteDatabase db = databaseUtils.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + COL_PROBLEM_ID + " DESC";
        try (Cursor cursor = db.rawQuery(query, null)) {
            if (cursor.moveToFirst()) {
                List<Problem> list = new ArrayList<>();
                while (!cursor.isAfterLast()) {
                    Problem p = new Problem(cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3),
                            cursor.getString(4));
                    list.add(p);
                    cursor.moveToNext();
                }
                return list;
            }
        } catch (Exception e) {
            return List.of();
        }
        return List.of();
    }

    public boolean addProblem(Problem p) {
        SQLiteDatabase db = databaseUtils.getWritableDatabase();
        long res;
        try {
            res = db.insert(TABLE_NAME, null, toContentValues(p));
            if (res > 0) return true;
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    private ContentValues toContentValues(Problem p) {
        ContentValues values = new ContentValues();
        values.put(COL_PROBLEM_NAME, p.getProblem_name());
        values.put(COL_PROBLEM_CONTENT, p.getProblem_content());
        values.put(COL_PROBLEM_UPLOAD_TIME, p.getProblem_upload_time());
        values.put(COL_USERNAME, p.getUsername());
        return values;
    }
}
