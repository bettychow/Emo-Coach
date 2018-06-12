
package com.example.android.emocoach;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;

import java.util.Calendar;

public class CalendarActivity extends AppCompatActivity {

    private static final String TAG = "CalendarActivity";
    private CalendarView mCalendarView;
    private int cMonth;
    private int cYear;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_layout);
        mCalendarView = (CalendarView) findViewById(R.id.calendarView);

        Calendar calendar = Calendar.getInstance();
        cMonth = calendar.get(Calendar.MONTH) + 1;
        cYear = calendar.get(Calendar.YEAR);

        System.out.println("????????????" + mCalendarView.getDate());

        System.out.println("calendar=====>" + calendar.getTimeInMillis());

        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener( ) {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int i, int i1, int i2) {
                String date = (i1 +1) + "/" + i2 + "/" + i ;
                Log.d(TAG, "onSelectedDayChange: date: " + date);

                Intent intent = new Intent(CalendarActivity.this, SelectedDateActivity.class);
                intent.putExtra("date", date);
                startActivity(intent);

            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_calendar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent;
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {

            case R.id.action_home:
                intent = new Intent(CalendarActivity.this, MainActivity.class);
                startActivity(intent);
                //displayDatabaseInfo();
                return true;
            case R.id.action_thirty_days_piechart:
                intent = new Intent(CalendarActivity.this, ThirtyDaysReport.class);
                startActivity(intent);
                return true;
            case R.id.action_thirty_days_chart:
                intent = new Intent(CalendarActivity.this, ThirtyDaysSwings.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
