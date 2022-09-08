package com.example.c196pa2timothylawrence.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.c196pa2timothylawrence.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.example.c196pa2timothylawrence.Converter.DateConverter.toDate;

public class CourseNotify extends AppCompatActivity {
    String type;
    Long courseStartLong;
    Date courseStartDate;
    Long courseEndlong;
    Date courseEndDate;
    String courseName;

    EditText editDate;
    DatePickerDialog.OnDateSetListener startDate;
    final Calendar myCalendarStart = Calendar.getInstance();

    SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_notify);
        editDate = findViewById(R.id.txtCourseNotifyDate);

        //Get the Long from previous page (CourseDetail)
        courseStartLong = getIntent().getLongExtra("start long", 0);
        courseStartDate = toDate(courseStartLong);
        courseEndlong = getIntent().getLongExtra("end long", 0);
        courseEndDate = toDate(courseEndlong);

        courseName = getIntent().getStringExtra("name");
        type = getIntent().getStringExtra("type");

        String displayStartDate = format.format(courseStartDate);
        editDate.setText(displayStartDate);

        editDate.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Date date;
                String info = editDate.getText().toString();

                try{
                    myCalendarStart.setTime(format.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                new DatePickerDialog(CourseNotify.this, startDate, myCalendarStart.get(Calendar.YEAR),
                        myCalendarStart.get(Calendar.MONTH), myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        startDate = new DatePickerDialog.OnDateSetListener(){
            //This method takes in the date in the datepicker and will update it in the editDate text field when selecting a new date in datepicker
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                myCalendarStart.set(Calendar.YEAR, year);
                myCalendarStart.set(Calendar.MONTH, monthOfYear);
                myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                editDate.setText(format.format(myCalendarStart.getTime()));
            }
        };

    }

    public void onClickSetNotifyStart(View view){
        String dateFromScreen = editDate.getText().toString();
        Date notifyDate = null;
        try{
            notifyDate=format.parse(dateFromScreen);
        } catch (ParseException e){
            e.printStackTrace();
        }
        Long trigger = notifyDate.getTime();
        Intent intent = new Intent(CourseNotify.this, MyReceiver.class);
        //What the notification reads
        intent.putExtra("key", type + " " + courseName + " starts " + format.format(courseStartDate));
        PendingIntent sender = PendingIntent.getBroadcast(CourseNotify.this, MainActivity.numAlert++, intent, PendingIntent.FLAG_IMMUTABLE);
        AlarmManager alarmManager=(AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);


    }

    public void onClickSetNotifyEnd(View view){
        String dateFromScreen = editDate.getText().toString();
        Date notifyDate = null;
        try{
            notifyDate=format.parse(dateFromScreen);
        } catch (ParseException e){
            e.printStackTrace();
        }
        Long trigger = notifyDate.getTime();
        Intent intent = new Intent(CourseNotify.this, MyReceiver.class);
        //What the notification reads
        intent.putExtra("key", type + " " + courseName + " ends " + format.format(courseEndDate));
        PendingIntent sender = PendingIntent.getBroadcast(CourseNotify.this, MainActivity.numAlert++, intent, PendingIntent.FLAG_IMMUTABLE);
        AlarmManager alarmManager=(AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
    }


}