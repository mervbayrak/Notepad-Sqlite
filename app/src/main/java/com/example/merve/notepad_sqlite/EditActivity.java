package com.example.merve.notepad_sqlite;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.merve.notepad_sqlite.helper.DBHelper;
import com.example.merve.notepad_sqlite.model.Note;

public class EditActivity extends AppCompatActivity {
    Note note;
    EditText etTitle,etNote;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        id=getIntent().getIntExtra("id",0);//eğer yoksa 0 veriyoruz.

        etTitle=(EditText) findViewById(R.id.etTitle);
        etNote=(EditText) findViewById(R.id.etNote);

        if(id>0)
        {
            note= DBHelper.getNote(this,id);
            etTitle.setText(note.getTitle());
            etNote.setText(note.getNote());
        }
        else
        {
            note=new Note();
        }
    }

    public void saveNote(View view) {
        note.setTitle(etTitle.getText().toString());
        note.setNote(etNote.getText().toString());
        try{
            if (DBHelper.saveNote(this, note) > 0) {
                Toast.makeText(EditActivity.this, "Kaydınız yapıldı", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
            } else {
                Toast.makeText(EditActivity.this, "Kaydınız yapılamadı", Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception ex)
        {
            Toast.makeText(EditActivity.this,"Hata"+ex.getMessage(),Toast.LENGTH_SHORT).show();
        }
        finish();
    }

}

