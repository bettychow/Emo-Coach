package com.example.android.emocoach.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.emocoach.data.EmoContract.EmoEntry;

public class EmoDbHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "emocoach.db";
    private static final int DATABASE_VERSION = 2;

    public EmoDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_EMOS_TABLE = "CREATE TABLE " + EmoEntry.TABLE_NAME + " ("
                + EmoEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + EmoEntry.COLUMN_EMO_TYPE + " TEXT NOT NULL, "
                + EmoEntry.COLUMN_DATE + " TEXT NOT NULL );";

        db.execSQL(SQL_CREATE_EMOS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + EmoEntry.TABLE_NAME);
        onCreate(db);
    }
}
