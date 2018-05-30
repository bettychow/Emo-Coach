package com.example.android.emocoach;


import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.emocoach.data.EmoContract;
import com.example.android.emocoach.data.EmoDbHelper;

import org.w3c.dom.Text;

import java.io.SyncFailedException;
import java.util.Calendar;

import static com.example.android.emocoach.R.string.editor_insert_emo_failed;
import static com.example.android.emocoach.R.string.editor_insert_emo_successful;

public class MainActivity extends AppCompatActivity {

    private EmoDbHelper mDbHelper;

    private TextView theDate;
    private TextView currentDate;
    private TextView emotions;
    private Button btnGoCalendar;
    private Button btnSave;
    private int cDay;
    private int cMonth;
    private int cYear;
    private RadioGroup rGroup;
    private RadioButton checkedRadioButton;
    private ListView myListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        theDate = (TextView) findViewById(R.id.date);
        currentDate = (TextView) findViewById((R.id.today));
        btnGoCalendar = (Button) findViewById(R.id.btnGoCalendar);
        btnSave = (Button) findViewById(R.id.btnSave);

//        Intent incomingIntent = getIntent();
//        String date = incomingIntent.getStringExtra("date");
//        theDate.setText(date);

        Calendar calender = Calendar.getInstance();
        cDay = calender.get(Calendar.DAY_OF_MONTH);
        cMonth = calender.get(Calendar.MONTH) + 1;
        cYear = calender.get(Calendar.YEAR);

        currentDate.setText("Today is " + "" + cMonth + "/" + cDay + "/" + cYear);


        btnGoCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CalendarActivity.class);
                startActivity(intent);
            }
        });


        rGroup = (RadioGroup) findViewById(R.id.myRadioGroup);

        emotions = (TextView) findViewById(R.id.emotions);
        //checkedRadioButton = (RadioButton)rGroup.findViewById(rGroup.getCheckedRadioButtonId());

//        btnSave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                int selectedRadioButtonID = rGroup.getCheckedRadioButtonId();
//
//                if (selectedRadioButtonID != -1) {
//
//                    RadioButton selectedRadioButton = (RadioButton) findViewById(selectedRadioButtonID);
//                    String selectedRadioButtonText = selectedRadioButton.getText().toString();
//
//                    emotions.setText(selectedRadioButtonText + " selected.");
//                }
//                else{
//                    emotions.setText("Nothing selected from Radio Group.");
//                }
//
//            }
//        });


        rGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton checkedRadioButton = (RadioButton) group.findViewById(checkedId);

                String radioTextValue = checkedRadioButton.getText().toString();
                Drawable radioImgValue = checkedRadioButton.getCompoundDrawables()[2];

                emotions.setText(radioTextValue);
                emotions.setCompoundDrawablesWithIntrinsicBounds(null, null, radioImgValue, null);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedRadioButtonID = rGroup.getCheckedRadioButtonId();
                if (selectedRadioButtonID != -1) {

                    RadioButton selectedRadioButton = (RadioButton) findViewById(selectedRadioButtonID);
                    String selectedRadioButtonText = selectedRadioButton.getText().toString();

//  This will be a custom object that you write to wrap up your insert, read, etc functionality
//  You can define some nice functions to wrap up the ContentValues stuff and just return true or false if its successfully written
//                    EmotionsDatabase edb = new EmotionsDatabase();
//                    boolean success = ebd.insert(selectedRadioButtonText)


                    mDbHelper = new EmoDbHelper(getApplicationContext());
                    SQLiteDatabase db = mDbHelper.getWritableDatabase();

                    ContentValues values = new ContentValues();
                    values.put(EmoContract.EmoEntry.COLUMN_EMO_TYPE, selectedRadioButtonText);
                    long newRowId = db.insert(EmoContract.EmoEntry.TABLE_NAME, null, values);

                    //Uri newUri = getContentResolver().insert(EmoContract.EmoEntry.CONTENT_URI, values);

//                    if (newUri == null) {
//                        // If the new content URI is null, then there was an error with insertion.
//                        Toast.makeText(this, getString(R.string.editor_insert_emo_failed),
//                                Toast.LENGTH_SHORT).show();
//                    } else {
//                        // Otherwise, the insertion was successful and we can display a toast.
//                        Toast.makeText(this, getString(R.string.editor_insert_emo_successful),
//                                Toast.LENGTH_SHORT).show();
//                    }

                    emotions.setText(selectedRadioButtonText + " selected.");
                    displayDatabaseInfo();
                } else {
                    emotions.setText("Nothing selected from Radio Group.");
                    displayDatabaseInfo();
                }
            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();

    }

    private void displayDatabaseInfo() {
        mDbHelper = new EmoDbHelper(getApplicationContext());

        // Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                EmoContract.EmoEntry._ID,
                EmoContract.EmoEntry.COLUMN_EMO_TYPE};

        // Perform a query on the provider using the ContentResolver.
        // Use the {@link PetEntry#CONTENT_URI} to access the pet data.
//        Cursor cursor = getContentResolver().query(
//                EmoContract.EmoEntry.CONTENT_URI,   // The content URI of the words table
//                projection,             // The columns to return for each row
//                null,          // Selection criteria
//                null,       // Selection criteria
//                null);         // The sort order for the returned rows

        Cursor cursor = db.query(
                EmoContract.EmoEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        //myListView = (ListView) findViewById(R.id.list);

        // Setup an Adapter to create a list item for each row of pet data in the Cursor.
//        EmoCursorAdaptor adapter = new EmoCursorAdaptor(this, cursor);
//        myListView.setAdapter(adapter);
//
        TextView displayView = (TextView) findViewById(R.id.text_view_emo);
//
        try {
            // Create a header in the Text View that looks like this:
            //
            // The pets table contains <number of rows in Cursor> pets.
            // _id - name - breed - gender - weight
            //
            // In the while loop below, iterate through the rows of the cursor and display
            // the information from each column in this order.
            displayView.setText("The emos table contains " + cursor.getCount() + " emos.\n\n");
            displayView.append(EmoContract.EmoEntry._ID + " - " +
                    EmoContract.EmoEntry.COLUMN_EMO_TYPE + " - ");

            // Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(EmoContract.EmoEntry._ID);
            int emoColumnIndex = cursor.getColumnIndex(EmoContract.EmoEntry.COLUMN_EMO_TYPE);

            // Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()) {
                // Use that index to extract the String or Int value of the word
                // at the current row the cursor is on.
                int currentID = cursor.getInt(idColumnIndex);
                String currentEmo = cursor.getString(emoColumnIndex);

                // Display the values from each column of the current row in the cursor in the TextView
                displayView.append(("\n" + currentID + " - " +
                        currentEmo));
            }
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_currentdate, menu);
        return true;
    }
}
