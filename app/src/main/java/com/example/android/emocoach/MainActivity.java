package com.example.android.emocoach;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.SyncFailedException;
import java.util.Calendar;
public class MainActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        theDate = (TextView) findViewById(R.id.date);
        currentDate = (TextView)  findViewById((R.id.today));
        btnGoCalendar = (Button) findViewById(R.id.btnGoCalendar);
        btnSave = (Button) findViewById(R.id.btnSave);

//        Intent incomingIntent = getIntent();
//        String date = incomingIntent.getStringExtra("date");
//        theDate.setText(date);

        Calendar calander = Calendar.getInstance();
        cDay = calander.get(Calendar.DAY_OF_MONTH);
        cMonth = calander.get(Calendar.MONTH) + 1;
        cYear = calander.get(Calendar.YEAR);

        currentDate.setText("Today is " + "" + cMonth + "/" + cDay + "/" + cYear);



        btnGoCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CalendarActivity.class);
                startActivity(intent);
            }
        });



        rGroup = (RadioGroup)findViewById(R.id.myRadioGroup);

        emotions = (TextView)findViewById(R.id.emotions);
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
                RadioButton checkedRadioButton = (RadioButton)group.findViewById(checkedId);

                String radioTextValue = checkedRadioButton.getText().toString();
                Drawable radioImgValue = checkedRadioButton.getCompoundDrawables()[0];

                emotions.setText(radioTextValue);
                emotions.setCompoundDrawablesWithIntrinsicBounds (null, null, radioImgValue, null);
            }
        });



    }
}
