package com.example.android.emocoach;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.android.emocoach.data.EmoContract;
import com.example.android.emocoach.data.EmoDbHelper;

public class SelectedDateActivity extends AppCompatActivity {

    private TextView theDate;
    private TextView feelingsView;
    private TextView notesView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selected_date);

        theDate = (TextView) findViewById(R.id.date);

        Intent incomingIntent = getIntent();
        String date = incomingIntent.getStringExtra("date");
        theDate.setText(date);

        EmoDbHelper mDbHelper = new EmoDbHelper(getApplicationContext());
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                EmoContract.EmoEntry._ID,
                EmoContract.EmoEntry.COLUMN_EMO_TYPE};


        Cursor cursor = db.query(
                EmoContract.EmoEntry.TABLE_EMOS,
                projection,
                EmoContract.EmoEntry.COLUMN_DATE +"=?",
                new String[] {date},
                null,
                null,
                null
        );

        feelingsView = (TextView) findViewById(R.id.selected_date_feelings);

        try {

            // Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(EmoContract.EmoEntry._ID);
            int emoColumnIndex = cursor.getColumnIndex(EmoContract.EmoEntry.COLUMN_EMO_TYPE);

            // Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()) {
                // Use that index to extract the String or Int value of the word
                // at the current row the cursor is on.
                int id = cursor.getInt(idColumnIndex);
                String emo = cursor.getString(emoColumnIndex);

                System.out.println("EEEEEEMO ====> " + emo);

                // Display the values from each column of the current row in the cursor in the TextView
                feelingsView.append("\n" + emo);
            }
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }


        EmoDbHelper mDbHelper2 = new EmoDbHelper(getApplicationContext());
        SQLiteDatabase db2 = mDbHelper2.getReadableDatabase();

        String[] projection2 = {
                EmoContract.EmoEntry.COLUMN_NOTES};


        Cursor cursor2 = db2.query(
                EmoContract.EmoEntry.TABLE_NOTES,
                projection2,
                EmoContract.EmoEntry.COLUMN_DATE +"=?",
                new String[] {date},
                null,
                null,
                null
        );

        notesView = (TextView) findViewById(R.id.selected_date_notes);

        try {

            // Figure out the index of each column
            int notesColumnIndex = cursor2.getColumnIndex(EmoContract.EmoEntry.COLUMN_NOTES);

            // Iterate through all the returned rows in the cursor
            while (cursor2.moveToNext()) {
                // Use that index to extract the String or Int value of the word
                // at the current row the cursor is on.
                String notes = cursor2.getString(notesColumnIndex);

                System.out.println("Notes in selecteddate activity =====> " + notes);

                // Display the values from each column of the current row in the cursor in the TextView
                notesView.append("\n" + notes);
            }
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor2.close();
        }

    }



}