package com.example.hauiquiz.ui.doNote;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hauiquiz.R;
import com.example.hauiquiz.dao.NoteDAO;
import com.example.hauiquiz.entity.Note;
import com.example.hauiquiz.utils.DisplayMessageDialog;

import java.util.ArrayList;
import java.util.List;

public class ViewNoteList extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private ListView lv_note;
    private EditText view_note_list_edt_content;
    private ImageButton btn_back, btn_menu;
    private ArrayAdapter<Note> adapter;
    private List<Note> list;
    NoteDAO noteDAO;
    private int id_selected = -1;
    private String subjectId;

    @Override
    protected void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_note_list);

        getWidget();
        getData();
        setEvents();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu_crud, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        String content = view_note_list_edt_content.getText().toString();
        if (content.isEmpty()) {
            DisplayMessageDialog.displayMessage(this, "DỮ LIỆU", "TRỐNG");
            return true;
        }

        Note note = new Note(0, content, subjectId);
        if (id == R.id.action_add) {
            addNote(note);
        } else if (id == R.id.action_edit) {
            editNote(note);
        } else if (id == R.id.action_delete) {
            delNote();
        }

        return super.onContextItemSelected(item);
    }

    private void setEvents() {
        btn_back.setOnClickListener(this);
        lv_note.setOnItemClickListener(this);
        registerForContextMenu(btn_menu);
    }

    private void getData() {
        list = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        lv_note.setAdapter(adapter);

        noteDAO = new NoteDAO(this);
        list.clear();

        Intent intent = getIntent();
        subjectId = intent.getStringExtra(ViewSubjectList.DATA_SUBJECT_ID);
        list.addAll(noteDAO.getAllNotes(subjectId));
        adapter.notifyDataSetChanged();
    }

    private void getWidget() {
        lv_note = findViewById(R.id.lv_note1);
        view_note_list_edt_content = findViewById(R.id.view_note_list_edt_content);
        btn_menu = findViewById(R.id.view_note_list_btn_menu);
        btn_back = findViewById(R.id.view_note_list_btn_back);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.view_note_list_btn_back) {
            getBack();
        }
    }

    private void delNote() {
        if (id_selected == -1) {
            DisplayMessageDialog.displayMessage(this, "XÓA", "Thất bại");
            return;
        }
        if (noteDAO.delNote(id_selected)) {
            refreshList();
        }
    }

    private void editNote(Note n) {
        if (noteDAO.editNote(n, id_selected)) {
            refreshList();
        }
    }

    private void addNote(Note n) {
        if (noteDAO.addNote(n)) {
            refreshList();
        }
    }

    private void getBack() {
        finish();
    }

    private void refreshList() {
        list.clear();
        list.addAll(noteDAO.getAllNotes(subjectId));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Note note = list.get(position);
        id_selected = note.getNote_id();
        view_note_list_edt_content.setText(note.getNote_content());
    }
}