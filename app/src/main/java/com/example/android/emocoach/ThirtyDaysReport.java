package com.example.android.emocoach;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.android.emocoach.data.EmoContract;
import com.example.android.emocoach.data.EmoDbHelper;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.android.emocoach.data.EmoContract.EmoEntry.TABLE_EMOS;

public class ThirtyDaysReport extends AppCompatActivity{

    private ArrayList<String> emosArray;
    private HashMap<String, Integer> emosMap;
    private ArrayList<String> dedupEmosList;
    private ArrayList<Integer> emosCountsList;


    private EmoDbHelper mDbHelper;
    private long cTime;



    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thirty_days_report);



    mDbHelper = new EmoDbHelper(getApplicationContext());
    SQLiteDatabase db = mDbHelper.getReadableDatabase();

    cTime = System.currentTimeMillis();

    Calendar cal = Calendar.getInstance();
    cal.add(Calendar.DAY_OF_YEAR, -30);
    long thirtyDaysAgo = cal.getTimeInMillis();
    emosArray = new ArrayList<>();
    dedupEmosList = new ArrayList<>();
    emosCountsList = new ArrayList<>();

    String[] projection = {
            EmoContract.EmoEntry._ID,
            EmoContract.EmoEntry.COLUMN_EMO_TYPE};

    Cursor cursor = db.rawQuery("SELECT * FROM " + EmoContract.EmoEntry.TABLE_EMOS + " WHERE " + EmoContract.EmoEntry.COLUMN_TIMESTAMP + ">=" + thirtyDaysAgo, null);

        //TextView dataView = (TextView) findViewById(R.id.report_data);


        try {

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
                //dataView.append("\n" + currentEmo);


            emosArray.add(currentEmo);

            }
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }

        System.out.println("emosArray ======>" + emosArray);



        emosMap = buildCount(emosArray);

        //setupPieChart();

        emosMap.forEach((k, v) -> {
            dedupEmosList.add(k);
            emosCountsList.add(v);
    });



        String [] dedupEmosArr = dedupEmosList.toArray(new String[dedupEmosList.size()]);
        Integer [] emosCountsArr = emosCountsList.toArray(new Integer[emosCountsList.size()]);


//        System.out.println("dddeeeeeeee=====>" + dedupEmosArr);
//        System.out.println("EmmmmossssArrr ======>" + emosCountsArr);

        List<PieEntry> pieEntries = new ArrayList<>();

        List<String> list = new ArrayList<String>();

        for(int i = 0; i < dedupEmosArr.length; i++) {
            pieEntries.add(new PieEntry(emosCountsArr[i], dedupEmosArr[i]));
        }

        PieDataSet dataset = new PieDataSet((pieEntries), "Emotions in past 30 days");
        dataset.setColors(ColorTemplate.JOYFUL_COLORS);
        PieData data = new PieData(dataset);

        PieChart chart = (PieChart) findViewById(R.id.chart);
        chart.setData(data);
        chart.animateY(1000);
        chart.invalidate();

//    private void setupPieChart() {
//      //  Populating a list of PieEntries
//        List<PieEntry> pieEntires = new ArrayList<>();
//        for(int i = 0; i < rainfall.length; i++) {
//            pieEntires.add(new PieEntry(rainfall[i], monthNames[i]));
//        }
//
//        PieDataSet dataset = new PieDataSet(pieEntires, "Rainfall for Vancouver" );
//        dataset.setColors(ColorTemplate.JOYFUL_COLORS);
//        PieData data = new PieData(dataset);
//
//        //Get the chart
//        PieChart chart = (PieChart) findViewById(R.id.chart);
//        chart.setData(data);
//        chart.animateY(1000);
//        chart.invalidate();
//    }

}




    private HashMap<String, Integer> buildCount(ArrayList<String> input) {
        HashMap<String, Integer> result = new HashMap<>();

        for(String emo : input) {
            if(result.containsKey(emo)) {
                result.put(emo, result.get(emo) + 1);

            } else {
                result.put(emo, 1);
            }

        }

        return result;

    }

}
