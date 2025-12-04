package com.example.hauiquiz.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.hauiquiz.database.DatabaseUtils;
import com.example.hauiquiz.entity.Solution;

import java.util.ArrayList;
import java.util.List;

public class SolutionDAO {
    private static final String TABLE_NAME = "solution";
    private static final String COL_SOLUTION_ID = "solution_id";
    private static final String COL_SOLUTION_CONTENT = "solution_content";
    private static final String COL_SOLUTION_UPLOAD_TIME = "solution_upload_time";
    private static final String COL_USERNAME = "username";
    private static final String COL_PROBLEM_ID = "problem_id";

    private final DatabaseUtils databaseUtils;

    public SolutionDAO(Context context) {
        databaseUtils = DatabaseUtils.getInstance(context);
    }

    public List<Solution> getSolutionsByProblemId(int problemId) {
        SQLiteDatabase db = databaseUtils.getReadableDatabase();
        try (Cursor cursor = db.query(TABLE_NAME, null,
                COL_PROBLEM_ID + " = ? ORDER BY " + COL_SOLUTION_ID + " DESC",
                new String[]{problemId + ""}, null, null, null)) {
            if (cursor.moveToFirst()) {
                List<Solution> list = new ArrayList<>();
                while (!cursor.isAfterLast()) {
                    Solution p = new Solution(
                            cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3),
                            problemId);
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

    public boolean addSolution(Solution s) {
        SQLiteDatabase db = databaseUtils.getWritableDatabase();
        long res;
        try {
            res = db.insert(TABLE_NAME, null, toContentValues(s));
            if (res > 0) return true;
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    private ContentValues toContentValues(Solution s) {
        ContentValues values = new ContentValues();
        values.put(COL_SOLUTION_CONTENT, s.getSolution_content());
        values.put(COL_SOLUTION_UPLOAD_TIME, s.getSolution_upload_time());
        values.put(COL_PROBLEM_ID, s.getProblem_id());
        values.put(COL_USERNAME, s.getUsername());
        return values;
    }
}
