package com.example.merve.notepad_sqlite.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.merve.notepad_sqlite.R;
import com.example.merve.notepad_sqlite.model.Note;

import java.util.List;


public class MyNoteAdapter extends ArrayAdapter<Note> {

    List<Note> mNotes;
    Context mContext;
    public MyNoteAdapter(@NonNull Context context, @NonNull List<Note> objects) {
        super(context, R.layout.note_list_item, objects);
        mNotes=objects;
        mContext=context;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView==null)
        {
            convertView= LayoutInflater.from(mContext).inflate(R.layout.note_list_item,null);
        }
        TextView tvTitle= (TextView) convertView.findViewById(R.id.tvTitle);
        TextView tvID = (TextView) convertView.findViewById(R.id.tvID);
        Note note=mNotes.get(position);
        tvTitle.setText(note.getTitle());
        tvID.setText( String.valueOf( position+1 ));


        return convertView;
    }

    @Override
    public int getCount() {
        return mNotes.size();
    }

    @Nullable
    @Override
    public Note getItem(int position) {
        return mNotes.get(position);
    }



}
