package com.example.android.emocoach;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.android.emocoach.data.EmoContract;
import com.example.android.emocoach.data.EmoDbHelper;
import com.github.mikephil.charting.renderer.scatter.SquareShapeRenderer;

import java.security.PrivateKey;
import java.util.Calendar;

public class EditNotesActivity extends AppCompatActivity{

    private Button saveNotesBtn;
    private EditText editNotes;
    private String content;
    private EmoDbHelper mDbHelper;
    private int cDay;
    private int cMonth;
    private int cYear;
    private String cDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Calendar calendar = Calendar.getInstance();
        cDay = calendar.get(Calendar.DAY_OF_MONTH);
        cMonth = calendar.get(Calendar.MONTH) + 1;
        cYear = calendar.get(Calendar.YEAR);
        cDate = "" + cMonth + "/" + cDay + "/" + cYear;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_notes);

        saveNotesBtn = (Button) findViewById(R.id.btn_save_notes);
        editNotes = (EditText) findViewById(R.id.edit_notes);

        mDbHelper = new EmoDbHelper(getApplicationContext());
        SQLiteDatabase dbR1 = mDbHelper.getReadableDatabase();

        String[] projection = {EmoContract.EmoEntry.COLUMN_NOTES};

        Cursor cursor1 = dbR1.query(
                EmoContract.EmoEntry.TABLE_NOTES,
                projection,
                EmoContract.EmoEntry.COLUMN_DATE +"=?",
                new String[] {cDate},
                null,
                null,
                null
        );

        if(cursor1.getCount() == 1) {
            cursor1.moveToNext();
            int notesColumnIndex1 = cursor1.getColumnIndex(EmoContract.EmoEntry.COLUMN_NOTES);

            String currentNotes1 = cursor1.getString(notesColumnIndex1);

            editNotes.setText(currentNotes1);

        }

        cursor1.close();

        saveNotesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                content = editNotes.getText().toString();

                System.out.println("Content=====>" + content);

                mDbHelper = new EmoDbHelper(getApplicationContext());
                SQLiteDatabase dbR = mDbHelper.getReadableDatabase();

                String[] projection = {
                        EmoContract.EmoEntry.COLUMN_NOTES};

                cDate = "" + cMonth + "/" + cDay + "/" + cYear;

                Cursor cursor = dbR.query(
                        EmoContract.EmoEntry.TABLE_NOTES,
                        projection,
                        EmoContract.EmoEntry.COLUMN_DATE +"=?",
                        new String[] {cDate},
                        null,
                        null,
                        null
                );

                System.out.println("CCCCCCC====>" + cursor.getCount());

                try{

                    editNotes = (EditText) findViewById(R.id.edit_notes);


                    if(cursor.getCount() == 0) {
                        System.out.println("XXXXXXXCCCCCCC====>" + cursor.getCount());

                        cursor.close();

                        mDbHelper = new EmoDbHelper(getApplicationContext());
                        SQLiteDatabase dbW = mDbHelper.getWritableDatabase();

                        ContentValues values = new ContentValues();
                        values.put(EmoContract.EmoEntry.COLUMN_NOTES, content);
                        values.put(EmoContract.EmoEntry.COLUMN_DATE, cDate);
                        long newRowId = dbW.insert(EmoContract.EmoEntry.TABLE_NOTES, null, values);

                        mDbHelper = new EmoDbHelper(getApplicationContext());
                        SQLiteDatabase dbR2 = mDbHelper.getWritableDatabase();

                        Cursor cursor2 = dbR.query(
                                EmoContract.EmoEntry.TABLE_NOTES,
                                projection,
                                EmoContract.EmoEntry.COLUMN_DATE +"=?",
                                new String[] {cDate},
                                null,
                                null,
                                null
                        );

                        cursor2.moveToNext();

                        int notesColumnIndex = cursor2.getColumnIndex(EmoContract.EmoEntry.COLUMN_NOTES);

                        String currentNotes = cursor2.getString(notesColumnIndex);

                        System.out.println("final=====>" + currentNotes);

                        editNotes.setText(currentNotes);

                        cursor2.close();


                    } else {

                        System.out.println("LLLLLLL====>" + cursor.getCount());
                        cursor.close();

                        mDbHelper = new EmoDbHelper(getApplicationContext());
                        SQLiteDatabase dbW = mDbHelper.getWritableDatabase();
//                        Cursor cursorN = dbW.rawQuery("UPDATE " + EmoContract.EmoEntry.TABLE_NOTES + " SET notes = '"+ content + "' WHERE cDate= " + cDate, null);

                        ContentValues values = new ContentValues();
                        values.put(EmoContract.EmoEntry.COLUMN_NOTES, content);

                        dbW.update(
                                EmoContract.EmoEntry.TABLE_NOTES,
                                values,
                                EmoContract.EmoEntry.COLUMN_DATE +"=?",
                                new String[] {cDate}
                        );


                        mDbHelper = new EmoDbHelper(getApplicationContext());
                        SQLiteDatabase dbR3 = mDbHelper.getReadableDatabase();

                        Cursor cursor3 = dbR3.query(
                                EmoContract.EmoEntry.TABLE_NOTES,
                                projection,
                                EmoContract.EmoEntry.COLUMN_DATE + "=?",
                                new String[] {cDate},
                                null,
                                null,
                                null
                        );

                        cursor3.moveToNext();

                        int notesColumnIndex = cursor3.getColumnIndex(EmoContract.EmoEntry.COLUMN_NOTES);
                        String currentNotes = cursor3.getString(notesColumnIndex);

                        System.out.println("final=====>" + currentNotes);

                        editNotes.setText(currentNotes);

                        cursor3.close();



                    }

                } finally {

                    cursor.close();
                }




            }
        });
    }
}
