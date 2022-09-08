package com.example.c196pa2timothylawrence.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.c196pa2timothylawrence.Database.Repository;
import com.example.c196pa2timothylawrence.Entity.Course;
import com.example.c196pa2timothylawrence.R;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.c196pa2timothylawrence.Converter.DateConverter.datePatternMatches;
import static com.example.c196pa2timothylawrence.Converter.DateConverter.toDate;

public class CourseEdit extends AppCompatActivity {
    //Layout resources
    TextView editCourseName;
    TextView editCourseStart;
    TextView editCourseEnd;
    RadioButton radProg;
    RadioButton radComp;
    RadioButton radDrop;
    RadioButton radPlan;
    TextView editInstName;
    TextView editInstEmail;
    TextView editInstPhone;
    TextView editNotes;
    //Variables for fields
    int courseID;
    String courseName;
    Long courseStartLong;
    Long courseEndLong;
    Date courseStartDate;
    Date courseEndDate;
    String status;
    String instName;
    String instEmail;
    String instPhone;
    String notes;
    int termID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_edit);
        //Assign Layout resources
        editCourseName = findViewById(R.id.editCourseName);
        editCourseStart = findViewById(R.id.editCourseStart);
        editCourseEnd = findViewById(R.id.editCourseEnd);
        radProg = findViewById(R.id.editRadProg);
        radComp = findViewById(R.id.editRadComp);
        radDrop = findViewById(R.id.editRadDrop);
        radPlan = findViewById(R.id.editRadPlan);
        editInstName = findViewById(R.id.editInstName);
        editInstEmail = findViewById(R.id.editInstEmail);
        editInstPhone = findViewById(R.id.editInstPhone);
        editNotes = findViewById(R.id.editCourseNotes);

        //Assign course values to variables
        courseID = getIntent().getIntExtra("id", 0);
        courseName = getIntent().getStringExtra("name");
        courseStartLong = getIntent().getLongExtra("date start", 0);
        courseEndLong = getIntent().getLongExtra("date end", 0);
        status = getIntent().getStringExtra("status");
        instName = getIntent().getStringExtra("instructor name");
        instEmail = getIntent().getStringExtra("instructor email");
        instPhone = getIntent().getStringExtra("instructor phone");
        notes = getIntent().getStringExtra("notes");
        termID = getIntent().getIntExtra("term id", 0);

        //Format start and end dates for text fields to MM/DD/YYYY
        courseStartDate = toDate(courseStartLong);
        courseEndDate = toDate(courseEndLong);
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        String displayStartDate = format.format(courseStartDate);
        String displayEndDate = format.format(courseEndDate);

        //Fill text fields with data
        editCourseName.setText(courseName);
        editCourseStart.setText(displayStartDate);
        editCourseEnd.setText(displayEndDate);
        editInstName.setText(instName);
        editInstEmail.setText(instEmail);
        editInstPhone.setText(instPhone);
        editNotes.setText(notes);

        //Select correct radio button
        if(status.equals("In Progress")){
            radProg.setChecked(true);
        } else if(status.equals("Completed")){
            radComp.setChecked(true);
        } else if(status.equals("Dropped")){
            radDrop.setChecked(true);
        } else if(status.equals("Plan to Take")){
            radPlan.setChecked(true);
        } else {
            System.out.println("Was not able to check a radio button");
        }


    }

    public void onClickUpdateCourse(View view){
        courseName = editCourseName.getText().toString();
        String start = editCourseStart.getText().toString();
        String end = editCourseEnd.getText().toString();
        if(radProg.isChecked()){
            status = "In Progress";
        } else if(radComp.isChecked()){
            status = "Completed";
        } else if(radDrop.isChecked()){
            status = "Dropped";
        } else if(radPlan.isChecked()){
            status = "Plan to Take";
        } else {
            status = null;
        }
        instName = editInstName.getText().toString();
        instEmail = editInstEmail.getText().toString();
        instPhone = editInstPhone.getText().toString();
        notes = editNotes.getText().toString();

        //Check if course name is blank
        //Display alert error if blank and do not continue
        if (courseName.isEmpty()) {
            AlertDialog.Builder dateAlert = new AlertDialog.Builder(this);
            dateAlert.setTitle("Course name not filled");
            dateAlert.setMessage("Course name cannot be left blank");
            dateAlert.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //Do nothing
                }
            });
            AlertDialog alertDialog = dateAlert.create();
            alertDialog.show();
            //Check for date format errors
            //Display alert error if date format is in error and do not continue
        } else if(!datePatternMatches(start) || !datePatternMatches(end)){
            AlertDialog.Builder dateAlert = new AlertDialog.Builder(this);
            dateAlert.setTitle("Date format error");
            dateAlert.setMessage("Start or End date format does not match MM/DD/YYYY.  Date cannot be blank");
            dateAlert.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //Do nothing
                }
            });
            AlertDialog alertDialog = dateAlert.create();
            alertDialog.show();
            //Check for NO radio button clicked
            //Display alert error if no button clicked and do not continue
        } else if(status == null){
            AlertDialog.Builder dateAlert = new AlertDialog.Builder(this);
            dateAlert.setTitle("No status chosen");
            dateAlert.setMessage("Please select an option for 'Status'");
            dateAlert.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //Do nothing
                }
            });
            AlertDialog alertDialog = dateAlert.create();
            alertDialog.show();
        } else {
            //Continue with update
            Repository repository = new Repository(getApplication());

            //Parse start and end dates into Long format for database storage
            Long startDate = Date.parse(start);
            Long endDate = Date.parse(end);

            //Update course
            Course course = new Course(courseID, courseName, startDate, endDate, status, instName, instEmail, instPhone, notes, termID);
            repository.updateCourse(course);

            //Go back to TermList
            Intent intent = new Intent(CourseEdit.this, TermList.class);
            startActivity(intent);

        }

    }
}