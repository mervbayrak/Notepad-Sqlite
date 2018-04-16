package com.example.merve.notepad_sqlite.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class MySQLiteHelper extends SQLiteOpenHelper{
    static String DATABASE_NAME="mynotes.db";
    static int DB_VERSİON=1;



    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE NOTE (" +
                    "ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    "TITLE TEXT NOT NULL," +
                    "NOTE TEXT NOT NULL)" ;



    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DB_VERSİON);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion == 1){
            if(newVersion == 2)
            {
                db.execSQL("Delete from Note Where Title is null or Note is null");
                //db.execSQL("Alter Table Note Alter Column Title TEXT NOT NULL");
            }

        }

    }
}
