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
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.emocoach.data.EmoContract;
import com.example.android.emocoach.data.EmoContract.EmoEntry;
import com.example.android.emocoach.data.EmoDbHelper;

import org.w3c.dom.Text;

import java.io.SyncFailedException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.example.android.emocoach.R.id.action_write_emo_notes;
import static com.example.android.emocoach.R.string.editor_insert_emo_failed;
import static com.example.android.emocoach.R.string.editor_insert_emo_successful;

public class MainActivity extends AppCompatActivity {

    private EmoDbHelper mDbHelper;

    private TextView theDate;
    private TextView tvCurrentDate;
    private TextView emotions;
    private Button btnSave;
    private Button btnDummy;
    private Button btnReport;
    private Button btnChart;
    private int cDay;
    private int cMonth;
    private int cYear;
    private long cTime;
    private String cDate;
    private RadioGridGroup rGroup;
    private RadioButton checkedRadioButton;
    private ListView emoListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        theDate = (TextView) findViewById(R.id.date);
        tvCurrentDate = (TextView) findViewById((R.id.today));
        btnSave = (Button) findViewById(R.id.btnSave);
        btnDummy = (Button) findViewById(R.id.addDummy);
        btnReport = (Button) findViewById(R.id.thirty_days_report);
        btnChart = (Button) findViewById(R.id.thirty_days_swings);

//        Intent incomingIntent = getIntent();
//        String date = incomingIntent.getStringExtra("date");
//        theDate.setText(date);

        Calendar calendar = Calendar.getInstance();
        cDay = calendar.get(Calendar.DAY_OF_MONTH);
        cMonth = calendar.get(Calendar.MONTH) + 1;
        cYear = calendar.get(Calendar.YEAR);


        Date date = new Date();
        cTime = date.getTime();

        long yourmilliseconds = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm:ss");
        Date resultdate = new Date(yourmilliseconds);
        System.out.println(sdf.format(resultdate));



        tvCurrentDate.setText("Today is " + "" + cMonth + "/" + cDay + "/" + cYear);



        btnReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ThirtyDaysReport.class);
                startActivity(intent);
            }
        });

        btnChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ThirtyDaysSwings.class);
                startActivity(intent);
            }
        });


//        rGroup = (RadioGroup) findViewById(R.id.myRadioGroup);
//
//        emotions = (TextView) findViewById(R.id.emotions);
//        //checkedRadioButton = (RadioButton)rGroup.findViewById(rGroup.getCheckedRadioButtonId());
//
//        rGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                RadioButton checkedRadioButton = (RadioButton) group.findViewById(checkedId);
//
//                String radioTextValue = checkedRadioButton.getText().toString();
//                Drawable radioImgValue = checkedRadioButton.getCompoundDrawables()[2];
//
//                emotions.setText(radioTextValue);
//                emotions.setCompoundDrawablesWithIntrinsicBounds(null, null, radioImgValue, null);
//            }
//        });




//rGroup2 = (RadioGridGroup) findViewById(myRadioGroup2);
//
//        rGroup2.setOnClickListener(new RadioGridGroup.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//                int checkedId = rGroup2.getCheckedRadioButtonId();
//                RadioButton checkedRadioButton = (RadioButton) rGroup2.findViewById(checkedId);
//
//                String radioTextValue = checkedRadioButton.getText().toString();
//                Drawable radioImgValue = checkedRadioButton.getCompoundDrawables()[2];
//
//                emotions.setText(radioTextValue);
//                emotions.setCompoundDrawablesWithIntrinsicBounds(null, null, radioImgValue, null);
//
//                System.out.println("RRRRRRRRR====>" + radioTextValue);
//            }
//        });



rGroup = (RadioGridGroup) findViewById(R.id.myRadioGroup);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance();
                cDay = calendar.get(Calendar.DAY_OF_MONTH);
                cMonth = calendar.get(Calendar.MONTH) + 1;
                cYear = calendar.get(Calendar.YEAR);


                Date date = new Date();
                cTime = date.getTime();

                int selectedRadioButtonID = rGroup.getCheckedRadioButtonId();

                System.out.println("???????=====>" + selectedRadioButtonID);
                if (selectedRadioButtonID != -1) {

                    RadioButton selectedRadioButton = (RadioButton) findViewById(selectedRadioButtonID);
                    String selectedRadioButtonText = selectedRadioButton.getText().toString();
                    cDate = "" + cMonth + "/" + cDay + "/" + cYear;

                    mDbHelper = new EmoDbHelper(getApplicationContext());
                    SQLiteDatabase db = mDbHelper.getWritableDatabase();

                    ContentValues values = new ContentValues();
                    values.put(EmoEntry.COLUMN_EMO_TYPE, selectedRadioButtonText);
                    values.put(EmoEntry.COLUMN_MONTH, cMonth);
                    values.put(EmoEntry.COLUMN_DATE, cDate);
                    values.put(EmoEntry.COLUMN_TIMESTAMP, cTime);
                    long newRowId = db.insert(EmoEntry.TABLE_EMOS, null, values);

                    emotions = (TextView) findViewById(R.id.emotions);
                    emotions.append(selectedRadioButtonText + " selected.");
                    displayDatabaseInfo();
                } else {
                    emotions.append("Nothing selected from Radio Group.");
                    displayDatabaseInfo();
                }

            }


        });

        btnDummy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDbHelper = new EmoDbHelper(getApplicationContext());
                SQLiteDatabase db = mDbHelper.getWritableDatabase();


                ContentValues values = new ContentValues();
                values.put(EmoEntry.COLUMN_EMO_TYPE, "Frustrated");
                values.put(EmoEntry.COLUMN_MONTH, 5);
                values.put(EmoEntry.COLUMN_DATE, "5/27/2018");
                values.put(EmoEntry.COLUMN_TIMESTAMP, 1527404400000L);
                long newRowId = db.insert(EmoEntry.TABLE_EMOS, null, values);
                displayDatabaseInfo();
            }


        });

        mDbHelper = new EmoDbHelper(getApplicationContext());
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                EmoEntry._ID,
                EmoEntry.COLUMN_EMO_TYPE};
        cDate = "" + cMonth + "/" + cDay + "/" + cYear;

        Cursor cursor = db.query(
                EmoEntry.TABLE_EMOS,
                projection,
                EmoEntry.COLUMN_DATE +"=?",
                new String[] {cDate},
                null,
                null,
                null
        );

        TextView feelingsView = (TextView) findViewById(R.id.feelings);

        try {

            // Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(EmoEntry._ID);
            int emoColumnIndex = cursor.getColumnIndex(EmoEntry.COLUMN_EMO_TYPE);

            // Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()) {
                // Use that index to extract the String or Int value of the word
                // at the current row the cursor is on.
                int currentID = cursor.getInt(idColumnIndex);
                String currentEmo = cursor.getString(emoColumnIndex);

                // Display the values from each column of the current row in the cursor in the TextView
                feelingsView.append("\n" + currentEmo);
            }
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }



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
                EmoEntry._ID,
                EmoEntry.COLUMN_EMO_TYPE,
                EmoEntry.COLUMN_MONTH,
                EmoEntry.COLUMN_DATE,
                EmoEntry.COLUMN_TIMESTAMP};

        // Perform a query on the provider using the ContentResolver.
        // Use the {@link PetEntry#CONTENT_URI} to access the pet data.
//        Cursor cursor = getContentResolver().query(
//                EmoContract.EmoEntry.CONTENT_URI,   // The content URI of the words table
//                projection,             // The columns to return for each row
//                null,          // Selection criteria
//                null,       // Selection criteria
//                null);         // The sort order for the returned rows

        cDate = "" + cMonth + "/" + cDay + "/" + cYear;

        Cursor cursor = db.query(
                EmoEntry.TABLE_EMOS,
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
            displayView.append(
                    EmoEntry._ID + " - " + EmoEntry.COLUMN_EMO_TYPE + " - " + EmoEntry.COLUMN_MONTH + " - " + EmoEntry.COLUMN_DATE + " - " + EmoEntry.COLUMN_TIMESTAMP);

            // Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(EmoEntry._ID);
            int emoColumnIndex = cursor.getColumnIndex(EmoEntry.COLUMN_EMO_TYPE);
            int monthColumnIndex = cursor.getColumnIndex((EmoEntry.COLUMN_MONTH));
            int dateColumnIndex = cursor.getColumnIndex(EmoEntry.COLUMN_DATE);
            int timeStampColumnIndex = cursor.getColumnIndex(EmoEntry.COLUMN_TIMESTAMP);

            // Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()) {
                // Use that index to extract the String or Int value of the word
                // at the current row the cursor is on.
                int currentID = cursor.getInt(idColumnIndex);
                String currentEmo = cursor.getString(emoColumnIndex);
                int currentMonth = cursor.getInt(monthColumnIndex);
                String currentDate = cursor.getString(dateColumnIndex);
                long currentTime = cursor.getLong(timeStampColumnIndex);

                // Display the values from each column of the current row in the cursor in the TextView
                displayView.append(("\n" + currentID + " - " + currentEmo + " - " + currentMonth + " - " + currentDate + " - " + currentTime));
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent;
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {

            case R.id.action_go_to_calendar:
                intent = new Intent(MainActivity.this, CalendarActivity.class);
                startActivity(intent);
                //displayDatabaseInfo();
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_write_emo_notes:
                intent = new Intent(MainActivity.this, EditNotesActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
