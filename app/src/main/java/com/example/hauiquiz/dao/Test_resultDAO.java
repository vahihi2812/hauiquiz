package com.example.hauiquiz.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.hauiquiz.database.DatabaseUtils;
import com.example.hauiquiz.entity.Test_result;
import com.example.hauiquiz.entity.Test_result_history;

import java.util.ArrayList;
import java.util.List;

public class Test_resultDAO {
    private static final String TABLE_NAME = "test_result";
    private static final String COL_TS_ID = "ts_id";
    private static final String COL_SCORE = "score";
    private static final String COL_COMPETED_TIME = "completed_time";
    private static final String COL_COMMENT = "comment";
    private static final String COL_SET_ID = "set_id";
    private static final String COL_USER_ID = "user_id";
    private static final String COL_SET_NAME = "set_name";

    private final DatabaseUtils databaseUtils;

    public Test_resultDAO(Context context) {
        databaseUtils = DatabaseUtils.getInstance(context);
    }

    public List<Test_result> getAllByUserId(int userId) {
        SQLiteDatabase db = databaseUtils.getReadableDatabase();
        try (Cursor cursor = db.query(TABLE_NAME, null, COL_USER_ID + " = ? ",
                new String[]{userId + ""}, null, null, null)) {
            if (cursor.moveToFirst()) {
                List<Test_result> list = new ArrayList<>();
                while (!cursor.isAfterLast()) {
                    Test_result ts = new Test_result(cursor.getInt(0), cursor.getDouble(1),
                            cursor.getString(2), cursor.getString(3), cursor.getInt(4), userId);

                    list.add(ts);
                    cursor.moveToNext();
                }
                return list;
            }
        } catch (Exception e) {
            return List.of();
        }
        return List.of();
    }

    public long addTest_result(Test_result ts) {
        SQLiteDatabase db = databaseUtils.getWritableDatabase();
        long res;
        try {
            res = db.insert(TABLE_NAME, null, toContentValues(ts));
            if (res > 0) return res;
        } catch (Exception e) {
            return -1;
        }
        return -1;
    }

    public boolean editTest_result(Test_result ts, long ts_id) {
        SQLiteDatabase db = databaseUtils.getWritableDatabase();
        long res;
        try {
            res = db.update(TABLE_NAME, toContentValues(ts),
                    COL_TS_ID + " = ?",
                    new String[]{ts_id + ""});
            if (res > 0) return true;
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    public List<Test_result_history> getAllTestHistoryByUserId(int userId) {
        List<Test_result_history> list = new ArrayList<>();
        SQLiteDatabase db = databaseUtils.getReadableDatabase();

        String sql =
                "SELECT qs.set_name, tr.score, tr.completed_time, tr.comment, tr.set_id " +
                        "FROM question_set qs " +
                        "JOIN test_result tr ON qs.set_id = tr.set_id " +
                        "WHERE tr.user_id = ? " +
                        "ORDER BY tr.ts_id DESC";

        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(userId)});

        if (cursor.moveToFirst()) {
            do {
                String setName = cursor.getString(0);
                double score = cursor.getDouble(1);
                String completedTime = cursor.getString(2);
                String comment = cursor.getString(3);
                int setId = cursor.getInt(4);

                Test_result_history item =
                        new Test_result_history(setName, score, completedTime, comment, setId);

                list.add(item);

            } while (cursor.moveToNext());
        }

        cursor.close();
        return list;
    }

    private ContentValues toContentValues(Test_result ts) {
        ContentValues values = new ContentValues();
        values.put(COL_SCORE, ts.getScore());
        values.put(COL_COMPETED_TIME, ts.getCompleted_time());
        values.put(COL_COMMENT, ts.getComment());
        values.put(COL_SET_ID, ts.getSet_id());
        values.put(COL_USER_ID, ts.getUser_id());
        return values;
    }
}
