package com.example.merve.notepad_sqlite;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.merve.notepad_sqlite.adapter.MyNoteAdapter;
import com.example.merve.notepad_sqlite.helper.DBHelper;
import com.example.merve.notepad_sqlite.model.Note;

import java.util.List;

public class NoteListActivity extends AppCompatActivity {
    int id;
    ListView lvNotes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        id=getIntent().getIntExtra("id",0);//eğer yoksa 0 veriyoruz.
        setSupportActionBar(toolbar);

        FloatingActionButton yeniedit = (FloatingActionButton) findViewById(R.id.yeniedit);

        yeniedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NoteListActivity.this, EditActivity.class));

            }
        });

        lvNotes=(ListView) findViewById(R.id.lvNote);

        registerForContextMenu(lvNotes);

        loadNotes();
    }

    private void loadNotes() {
        final List<Note> notes= DBHelper.getNotes(this);
        MyNoteAdapter adapter = new MyNoteAdapter(this, DBHelper.getNotes(this));
        lvNotes.setAdapter(adapter);
        lvNotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent ıntent=new Intent(NoteListActivity.this,EditActivity.class);
                ıntent.putExtra("id",notes.get(position).getId());
                startActivityForResult(ıntent,1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode== RESULT_OK)
        {
            if(requestCode == 1)
            {
                loadNotes();
            }
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("İşlem Seçiniz");
        menu.add(0,1,0,"Notu Sil");
        menu.add(0,2,1,"Notu Güncelle");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info= (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Note note=(Note) lvNotes.getItemAtPosition(info.position);
        final List<Note> notes=DBHelper.getNotes(this);
        if(item.getItemId()==1)
        {
            //silme işlemi
            if(DBHelper.deleteNote(this,note.getId())>0)
            {
                loadNotes(); //silmeden sonra listenin güncellemesi
                Toast.makeText(NoteListActivity.this,note.getTitle()+"silindi",Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(NoteListActivity.this,"hata oluştu",Toast.LENGTH_SHORT).show();
        }
        else
        {
            //güncelleme
            Intent intent = new Intent(this, EditActivity.class);
            intent.putExtra("id", note.getId());
            startActivityForResult(intent , 1);
        }
        return true;
    }
}
