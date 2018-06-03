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
import java.util.List;

import static com.example.android.emocoach.data.EmoContract.EmoEntry.TABLE_NAME;

public class ThirtyDaysReport extends AppCompatActivity{

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



    String[] projection = {
            EmoContract.EmoEntry._ID,
            EmoContract.EmoEntry.COLUMN_EMO_TYPE};

    Cursor cursor = db.rawQuery("SELECT * FROM " + EmoContract.EmoEntry.TABLE_NAME + " WHERE " + EmoContract.EmoEntry.COLUMN_TIMESTAMP + ">=" + thirtyDaysAgo, null);



    }

}
