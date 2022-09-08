package com.example.c196pa2timothylawrence.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.c196pa2timothylawrence.Database.Repository;
import com.example.c196pa2timothylawrence.Entity.Course;
import com.example.c196pa2timothylawrence.R;

import java.sql.Date;

import static com.example.c196pa2timothylawrence.Converter.DateConverter.datePatternMatches;

public class CourseAdd extends AppCompatActivity {
    //int for termID of the course we're adding (foreign key)
    public static int courseAddTermID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_add);
    }

    //Clicking save button
    public void onClickSaveCourse(View view) {
        //Get all the variables from fields
        //Name
        final EditText txtName = (EditText) findViewById(R.id.txtCourseName);
        String name = txtName.getText().toString();
        //Start Date
        final EditText txtStart = (EditText) findViewById(R.id.txtCourseStartDate);
        String start = txtStart.getText().toString();
        //End Date
        final EditText txtEnd = (EditText) findViewById(R.id.txtCourseEndDate);
        String end = txtEnd.getText().toString();
        //Status
        final RadioGroup radGroup = (RadioGroup) findViewById(R.id.radGroup);
        final RadioButton radProg = (RadioButton) findViewById(R.id.radProgress);
        final RadioButton radComp = (RadioButton) findViewById(R.id.radCompleted);
        final RadioButton radDrop = (RadioButton) findViewById(R.id.radDropped);
        final RadioButton radPlan = (RadioButton) findViewById(R.id.radPlan);
        String status = new String();
        if (radProg.isChecked()) {
            status = "In Progress";
        } else if (radComp.isChecked()) {
            status = "Completed";
        } else if (radDrop.isChecked()) {
            status = "Dropped";
        } else if (radPlan.isChecked()) {
            status = "Plan to Take";
        } else {
            //No status radio checked
            status = null;
        }
        //Instructor name
        final EditText txtInstName = (EditText) findViewById(R.id.txtInstName);
        String instName = txtInstName.getText().toString();
        //Instructor Email
        final EditText txtInstEmail = (EditText) findViewById(R.id.txtInstEmail);
        String instEmail = txtInstEmail.getText().toString();
        //Instructor Phone
        final EditText txtInstPhone = (EditText) findViewById(R.id.txtInstPhone);
        String instPhone = txtInstPhone.getText().toString();
        //Additional Notes
        final EditText txtNotes = (EditText) findViewById(R.id.txtCourseNotes);
        String notes = txtNotes.getText().toString();


        //Check if course name is blank
        //Display alert error if blank and do not continue
        if (name.isEmpty()) {
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
            //Continue with creation
            System.out.println("We got to creation");
            System.out.println("name: " + name);
            System.out.println(start);
            System.out.println(end);
            System.out.println(status);
            System.out.println(instName);
            System.out.println(instEmail);
            System.out.println(instPhone);
            System.out.println(notes);

            Repository repository = new Repository(getApplication());

            //Parse start and end dates into Long format for database storage
            Long startDate = Date.parse(start);
            Long endDate = Date.parse(end);

            //Add new course to database
            Course course = new Course(0, name, startDate, endDate, status, instName, instEmail, instPhone, notes, courseAddTermID);
            repository.insertCourse(course);

            //Go back to TermList
            Intent intent = new Intent(CourseAdd.this, TermList.class);
            startActivity(intent);
        }





    }
}