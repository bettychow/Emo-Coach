package com.example.android.emocoach;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;

import com.example.android.emocoach.data.EmoContract;
import com.example.android.emocoach.data.EmoDbHelper;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class ThirtyDaysSwings extends AppCompatActivity {

    private EmoDbHelper mDbHelper;
    private ArrayList<String> emosList;
    private ArrayList<Long> timestampList;
    private ArrayList<Integer> emosFigureList;
    private ArrayList<String> labels;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedIntanceState ) {
        super.onCreate(savedIntanceState);
        setContentView(R.layout.thirty_days_swings);

        LineChart chart = (LineChart) findViewById(R.id.line_chart);
        emosList = new ArrayList<>();
        timestampList = new ArrayList<>();

        mDbHelper = new EmoDbHelper(getApplicationContext());
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, -30);
        long thirtyDaysAgo = cal.getTimeInMillis();

         Cursor cursor = db.rawQuery("SELECT * FROM " + EmoContract.EmoEntry.TABLE_EMOS + " WHERE " + EmoContract.EmoEntry.COLUMN_TIMESTAMP + ">=" + thirtyDaysAgo, null);

        try {

            // Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(EmoContract.EmoEntry._ID);
            int emoColumnIndex = cursor.getColumnIndex(EmoContract.EmoEntry.COLUMN_EMO_TYPE);
            int timestampColumnIndex = cursor.getColumnIndex(EmoContract.EmoEntry.COLUMN_TIMESTAMP);

            // Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()) {
                // Use that index to extract the String or Int value of the word
                // at the current row the cursor is on.
                int currentID = cursor.getInt(idColumnIndex);
                String currentEmo = cursor.getString(emoColumnIndex);
                long currentTimestamp = cursor.getLong(timestampColumnIndex);

                // Display the values from each column of the current row in the cursor in the TextView
                //dataView.append("\n" + currentEmo);


                //emosArray.add(currentEmo);

                emosList.add(currentEmo);
                timestampList.add(currentTimestamp);

            }
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }

        System.out.println("emo======>" + emosList);
        System.out.println("timestamp========>" + timestampList);



        Description description = new Description();
        description.setText("30-day Mood Swings");
        chart.setDescription(description);

        List<Entry> entries = new ArrayList<Entry>();

        HashMap<String, Integer> emosMap = new HashMap<>();

        emosMap.put("Joyful", 12);
        emosMap.put("Happy", 11);
        emosMap.put("Excited", 10);
        emosMap.put("Energetic", 9);
        emosMap.put("Satisfied", 8);
        emosMap.put("Peaceful", 7);
        emosMap.put("Tired", 6);
        emosMap.put("Guilty", 5);
        emosMap.put("Sad", 4);
        emosMap.put("Frustrated", 3);
        emosMap.put("Angry", 2);
        emosMap.put("Furious", 1);


        System.out.println("HHHHHHH====>" + emosMap);

        emosFigureList = new ArrayList<>();
        labels = new ArrayList<>();

        for(String emo:emosList) {
            emosFigureList.add(emosMap.get(emo));
        }


        for(int i = 0; i < emosFigureList.size(); i++) {
            entries.add(new Entry( i, emosFigureList.get(i)));
        }




//        for(int j = 0; j < timestampList.size(); j++) {
//            labels.add("" + j);
//        }

        LineDataSet dataset = new LineDataSet(entries, "Emotions");

        LineData data = new LineData(dataset);

        chart.setData(data);

    }




}
