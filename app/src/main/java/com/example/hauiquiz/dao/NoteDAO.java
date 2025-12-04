package com.example.hauiquiz.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.hauiquiz.database.DatabaseUtils;
import com.example.hauiquiz.entity.Note;

import java.util.ArrayList;
import java.util.List;

public class NoteDAO {
    private static final String TABLE_NAME = "note";
    private static final String COL_NOTE_ID = "note_id";
    private static final String COL_NOTE_CONTENT = "note_content";
    private static final String COL_SUBJECT_ID = "subject_id";

    private final DatabaseUtils databaseUtils;

    public NoteDAO(Context context) {
        databaseUtils = DatabaseUtils.getInstance(context);
    }

    public List<Note> getAllNotes(String subjectId) {
        SQLiteDatabase db = databaseUtils.getReadableDatabase();
        try (Cursor cursor = db.query(TABLE_NAME, null, COL_SUBJECT_ID + " = ?", new String[]{subjectId}, null, null, null)) {
            if (cursor.moveToFirst()) {
                List<Note> list = new ArrayList<>();
                while (!cursor.isAfterLast()) {
                    Note note = new Note(cursor.getInt(0), cursor.getString(1), subjectId);
                    list.add(note);
                    cursor.moveToNext();
                }
                return list;
            }
        } catch (Exception e) {
            return List.of();
        }
        return List.of();
    }

    public boolean addNote(Note n) {
        SQLiteDatabase db = databaseUtils.getWritableDatabase();
        long res;
        try {
            res = db.insert(TABLE_NAME, null, toContentValues(n));
            if (res > 0) return true;
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    public boolean delNote(int noteId) {
        SQLiteDatabase db = databaseUtils.getWritableDatabase();
        long res;
        try {
            res = db.delete(TABLE_NAME, COL_NOTE_ID + " = ?", new String[]{noteId + ""});
            if (res > 0) return true;
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    public boolean editNote(Note n, int note_id) {
        SQLiteDatabase db = databaseUtils.getWritableDatabase();
        long res;
        try {
            res = db.update(TABLE_NAME, toContentValues(n),
                    COL_NOTE_ID + " = ?",
                    new String[]{note_id + ""});
            if (res > 0) return true;
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    private ContentValues toContentValues(Note n) {
        ContentValues values = new ContentValues();
        values.put(COL_NOTE_CONTENT, n.getNote_content());
        values.put(COL_SUBJECT_ID, n.getSubject_id());
        return values;
    }
}
