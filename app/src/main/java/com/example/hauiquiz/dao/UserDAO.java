package com.example.hauiquiz.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.hauiquiz.database.DatabaseUtils;
import com.example.hauiquiz.entity.User;

public class UserDAO {
    private static final String TABLE_NAME = "user";
    private static final String COL_USER_ID = "user_id";
    private static final String COL_USERNAME = "username";
    private static final String COL_PASSWORD = "password";
    private static final String COL_FULLNAME = "user_fullname";
    private static final String COL_ROLE_ID = "role_id";
    private final DatabaseUtils databaseUtils;

    public UserDAO(Context context) {
        databaseUtils = DatabaseUtils.getInstance(context);
    }

    @SuppressLint({"Recycle", "Range"})
    public User checkLogin(String username, String password) {
        SQLiteDatabase db = databaseUtils.getReadableDatabase();
        try (Cursor cursor = db.query(TABLE_NAME, null,
                COL_USERNAME + " = ? AND " + COL_PASSWORD + " = ?",
                new String[]{username, password}, null, null, null)) {
            if (!cursor.moveToFirst()) {
                return null;
            }
            return new User(cursor.getInt(cursor.getColumnIndex(COL_USER_ID)),
                    cursor.getString(cursor.getColumnIndex(COL_USERNAME)),
                    cursor.getString(cursor.getColumnIndex(COL_PASSWORD)),
                    cursor.getString(cursor.getColumnIndex(COL_FULLNAME)),
                    cursor.getInt(cursor.getColumnIndex(COL_ROLE_ID)));
        } catch (Exception e) {
            Log.e("UserDAO", e.getMessage(), e);
            return null;
        }
    }

    public long addUser(User u) {
        SQLiteDatabase db = databaseUtils.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(COL_USERNAME, u.getUsername());
            values.put(COL_PASSWORD, u.getPassword());
            values.put(COL_FULLNAME, u.getUser_fullname());
            values.put(COL_ROLE_ID, u.getRole_id());

            return db.insert(TABLE_NAME, null, values);
        } catch (Exception e) {
            Log.e("UserDAO", e.getMessage(), e);
            return -1;
        }
    }
}
