package com.example.android.emocoach;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Calendar;
public class MainActivity extends AppCompatActivity {

    private TextView theDate;
    private TextView currentDate;
    private Button btnGoCalendar;
    private int cDay;
    private int cMonth;
    private int cYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        theDate = (TextView) findViewById(R.id.date);
        currentDate = (TextView)  findViewById((R.id.today));
        btnGoCalendar = (Button) findViewById(R.id.btnGoCalendar);

//        Intent incomingIntent = getIntent();
//        String date = incomingIntent.getStringExtra("date");
//        theDate.setText(date);

        Calendar calander = Calendar.getInstance();
        cDay = calander.get(Calendar.DAY_OF_MONTH);
        cMonth = calander.get(Calendar.MONTH) + 1;
        cYear = calander.get(Calendar.YEAR);

        currentDate.setText("Today is " + " " + cMonth + " " + cDay + " " + cYear);



        btnGoCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CalendarActivity.class);
                startActivity(intent);
            }
        });

    }
}
