package com.example.merve.notepad_sqlite.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.merve.notepad_sqlite.model.Note;

import java.util.ArrayList;
import java.util.List;


public class DBHelper {
    public static long saveNote(Context context , Note note){

        MySQLiteHelper helper = new MySQLiteHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("TITLE", note.getTitle().equals("")? null :note.getTitle() );
        contentValues.put("NOTE", note.getNote().equals("")? null: note.getNote());

        return  note.getId()> 0 ? db.update("NOTE", contentValues, "id=?", new String[]{ String.valueOf(note.getId())})
                : db.insertOrThrow("NOTE", null, contentValues);
    }
    public static int deleteNote(Context context,int id)
    {
        MySQLiteHelper helper = new MySQLiteHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        return db.delete("NOTE","id=?",new String[] {String.valueOf(id)});
    }


    public static List<Note> getNotes(Context context){

        MySQLiteHelper helper=new MySQLiteHelper(context);
        SQLiteDatabase db=helper.getReadableDatabase();

        Cursor cursor=  db.rawQuery("Select * from NOTE", null);

        List<Note> notes=new ArrayList<>();

        while (cursor.moveToNext())
        {
            Note note=new Note();
            note.setId(cursor.getInt(0));
            note.setTitle(cursor.getString(1));
            note.setNote(cursor.getString(2));
            notes.add(note);
        }
        cursor.close();
        return  notes;
    }

    public static Note getNote(Context context, int id){
        MySQLiteHelper helper=new MySQLiteHelper(context);
        SQLiteDatabase db=helper.getReadableDatabase();

        Cursor cursor=   db.rawQuery("SELECT * from " + "NOTE "
                + "WHERE id="+id , null);
        Note note=new Note();

        while (cursor.moveToNext())
        {

            note.setId(cursor.getInt(0));
            note.setTitle(cursor.getString(1));
            note.setNote(cursor.getString(2));
        }
        cursor.close();

        return  note;

    }
}
