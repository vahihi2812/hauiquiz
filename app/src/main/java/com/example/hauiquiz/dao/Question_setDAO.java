package com.example.hauiquiz.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.hauiquiz.database.DatabaseUtils;
import com.example.hauiquiz.entity.Question_Set;

import java.util.ArrayList;
import java.util.List;

public class Question_setDAO {
    private static final String TABLE_NAME = "question_set";
    private static final String COL_SET_ID = "set_id";
    private static final String COL_SET_NAME = "set_name";
    private static final String COL_SET_DES = "set_des";
    private static final String COL_SET_CREATED_AT = "set_created_at";
    private static final String COL_SET_TYPE = "set_type";
    private static final String COL_SET_WEIGHT = "set_weight";
    private static final String COL_SET_DURATION = "set_duration";
    private static final String COL_SET_CREATOR = "set_creator";
    private static final String COL_USER_ID = "user_id";
    private final DatabaseUtils databaseUtils;

    public Question_setDAO(Context context) {
        databaseUtils = DatabaseUtils.getInstance(context);
    }

    //get byId
    public List<Question_Set> getAllSetByUserId(int userId, int setType) {
        SQLiteDatabase db = databaseUtils.getReadableDatabase();
        try (Cursor cursor = db.query(TABLE_NAME, null, COL_USER_ID + " = ? AND " + COL_SET_TYPE + " = ?",
                new String[]{userId + "", setType + ""}, null, null, null)) {
            if (cursor.moveToFirst()) {
                List<Question_Set> list = new ArrayList<>();
                while (!cursor.isAfterLast()) {
                    Question_Set qs = new Question_Set(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                            cursor.getString(3), cursor.getInt(4), cursor.getInt(5),
                            cursor.getString(6), cursor.getString(7), userId);

                    list.add(qs);
                    cursor.moveToNext();
                }
                return list;
            }
        } catch (Exception e) {
            return List.of();
        }
        return List.of();
    }

    public List<Question_Set> getAllSetByUserType(int setType) {
        SQLiteDatabase db = databaseUtils.getReadableDatabase();
        try (Cursor cursor = db.query(TABLE_NAME, null, COL_SET_TYPE + " = ?",
                new String[]{setType + ""}, null, null, null)) {
            if (cursor.moveToFirst()) {
                List<Question_Set> list = new ArrayList<>();
                while (!cursor.isAfterLast()) {
                    Question_Set qs = new Question_Set(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                            cursor.getString(3), cursor.getInt(4), cursor.getInt(5),
                            cursor.getString(6), cursor.getString(7), cursor.getInt(8));

                    list.add(qs);
                    cursor.moveToNext();
                }
                return list;
            }
        } catch (Exception e) {
            return List.of();
        }
        return List.of();
    }

    public boolean addQuestion_set(Question_Set qs) {
        SQLiteDatabase db = databaseUtils.getWritableDatabase();
        long res;
        try {
            res = db.insert(TABLE_NAME, null, toContentValues(qs));
            if (res > 0) return true;
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    public boolean editQuestion_set(Question_Set qs, int qs_id) {
        SQLiteDatabase db = databaseUtils.getWritableDatabase();
        long res;
        try {
            res = db.update(TABLE_NAME, toContentValues(qs),
                    COL_SET_ID + " = ?",
                    new String[]{qs_id + ""});
            if (res > 0) return true;
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    public boolean delQuestion_set(int qs_id) {
        SQLiteDatabase db = databaseUtils.getWritableDatabase();
        long res;
        try {
            res = db.delete(TABLE_NAME, COL_SET_ID + " = ?", new String[]{qs_id + ""});
            if (res > 0) return true;
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    private ContentValues toContentValues(Question_Set qs) {
        ContentValues values = new ContentValues();
        values.put(COL_SET_NAME, qs.getSet_name());
        values.put(COL_SET_DES, qs.getSet_des());
        values.put(COL_SET_CREATED_AT, qs.getSet_created_at());
        values.put(COL_SET_TYPE, qs.getSet_type());
        values.put(COL_SET_WEIGHT, qs.getSet_weight());
        values.put(COL_SET_DURATION, qs.getSet_duration());
        values.put(COL_SET_CREATOR, qs.getSet_creator());
        values.put(COL_USER_ID, qs.getUser_id());
        return values;
    }
}
