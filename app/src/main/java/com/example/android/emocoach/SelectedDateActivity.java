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

        TextView feelingsView = (TextView) findViewById(R.id.selected_date_feelings);

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

                // Display the values from each column of the current row in the cursor in the TextView
                feelingsView.append("\n" + emo);
            }
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }

    }



}