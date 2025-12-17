package com.example.hauiquiz.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseUtils extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseUtils";
    private static final String DB_NAME = "test13.db";
    private static final int DB_VERSION = 1;
    private static DatabaseUtils instance;
    private final Context context;
    private final String dbPath;

    public DatabaseUtils(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
        this.dbPath = context.getDatabasePath(DB_NAME).getPath();

        try {
            createDatabaseIfNeeded();
        } catch (IOException e) {
            throw new RuntimeException("Lỗi không thể sao chép database", e);
        }
    }

    public static synchronized DatabaseUtils getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseUtils(context.getApplicationContext());
        }
        return instance;
    }

    private void createDatabaseIfNeeded() throws IOException {
        File dbFile = new File(dbPath);

        if (!dbFile.exists()) {
            this.getReadableDatabase();
            this.close();

            try {
                copyDatabaseFromAssets();
                Log.d(TAG, "Đã sao chép database từ assets!");
            } catch (IOException e) {
                Log.e(TAG, "Lỗi khi sao chép database", e);
                if (dbFile.exists()) {
                    dbFile.delete();
                }
                throw e;
            }
        } else {
            Log.d(TAG, "Database đã tồn tại, không sao chép lại.");
        }
    }

    private void copyDatabaseFromAssets() throws IOException {
        try (InputStream input = context.getAssets().open(DB_NAME);
             OutputStream output = new FileOutputStream(dbPath)) {

            Toast.makeText(context, "Copyy", Toast.LENGTH_SHORT).show();

            byte[] buffer = new byte[4096];
            int length;
            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            output.flush();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG, "Nâng cấp database từ v" + oldVersion + " lên v" + newVersion);
        File dbFile = new File(dbPath);
        if (dbFile.exists()) {
            dbFile.delete();
        }
        try {
            copyDatabaseFromAssets();
        } catch (IOException e) {
            throw new RuntimeException("Lỗi sao chép database khi nâng cấp", e);
        }
    }
}