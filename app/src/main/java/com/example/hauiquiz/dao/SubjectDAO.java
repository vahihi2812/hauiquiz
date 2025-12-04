package com.example.hauiquiz.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.hauiquiz.database.DatabaseUtils;
import com.example.hauiquiz.entity.Subject;
import com.example.hauiquiz.ui.home.LoginActivity;

import java.util.ArrayList;
import java.util.List;

public class SubjectDAO {
    private static final String TABLE_NAME = "subject";
    private static final String COL_SUBJECT_ID = "subject_id";
    private static final String COL_SUBJECT_NAME = "subject_name";
    private static final String COL_SUBJECT_DES = "subject_des";
    private static final String COL_USER_ID = "user_id";

    private final DatabaseUtils databaseUtils;

    public SubjectDAO(Context context) {
        databaseUtils = DatabaseUtils.getInstance(context);
    }

    public List<Subject> getAllSubjects(int userId) {
        SQLiteDatabase db = databaseUtils.getReadableDatabase();
        try (Cursor cursor = db.query(TABLE_NAME, null, COL_USER_ID + " = ?", new String[]{userId + ""}, null, null, null)) {
            cursor.moveToFirst();
            List<Subject> list = new ArrayList<>();
            while (!cursor.isAfterLast()) {
                Subject subject = new Subject(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3));
                list.add(subject);
                cursor.moveToNext();
            }
            return list;
        } catch (Exception e) {
            return List.of();
        }
    }

    public boolean addSubject(Subject s) {
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

    public boolean delSubject(String subjectId) {
        SQLiteDatabase db = databaseUtils.getWritableDatabase();
        long res;
        try {
            res = db.delete(TABLE_NAME, COL_SUBJECT_ID + " = ? AND " + COL_USER_ID + " = ?", new String[]{subjectId, LoginActivity.USER_ID + ""});
            if (res > 0) return true;
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    public boolean editSubject(Subject s) {
        SQLiteDatabase db = databaseUtils.getWritableDatabase();
        long res;
        try {
            res = db.update(TABLE_NAME, toContentValues(s),
                    COL_SUBJECT_ID + " = ? AND " + COL_USER_ID + " = ?",
                    new String[]{s.getSubject_id(), LoginActivity.USER_ID + ""});
            if (res > 0) return true;
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    public boolean addSharedSubject(String shareId) {
        Subject s = getSubjectById(shareId);
        if (s == null)
            return false;
        SQLiteDatabase db = databaseUtils.getWritableDatabase();
        s.setUser_id(LoginActivity.USER_ID);
        long res = db.insert(TABLE_NAME, null, toContentValues(s));
        return res > 0;
    }

    public Subject getSubjectById(String subjectId) {
        SQLiteDatabase db = databaseUtils.getReadableDatabase();
        try (Cursor cursor = db.query(TABLE_NAME, null, COL_SUBJECT_ID + " = ?", new String[]{subjectId}, null, null, null)) {
            if (!cursor.moveToFirst()) {
                return null;
            } else {
                return new Subject(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3));
            }
        } catch (Exception e) {
            return null;
        }
    }

    private ContentValues toContentValues(Subject s) {
        ContentValues values = new ContentValues();
        values.put(COL_SUBJECT_ID, s.getSubject_id());
        values.put(COL_SUBJECT_NAME, s.getSubject_name());
        values.put(COL_SUBJECT_DES, s.getSubject_des());
        values.put(COL_USER_ID, s.getUser_id());
        return values;
    }
}
