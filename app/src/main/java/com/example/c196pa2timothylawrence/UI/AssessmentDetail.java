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
import com.example.c196pa2timothylawrence.Entity.Assessment;
import com.example.c196pa2timothylawrence.R;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.c196pa2timothylawrence.Converter.DateConverter.datePatternMatches;
import static com.example.c196pa2timothylawrence.Converter.DateConverter.toDate;

public class AssessmentDetail extends AppCompatActivity {
    int assessmentID;
    TextView editAssessmentName;
    TextView editAssessmentEnd;
    boolean isPerf;
    RadioButton editRadPerf;
    RadioButton editRadObj;
    String assessmentName;
    Long assessmentEndLong;
    Date assessmentEndDate;
    //We won't work with the start date other than storing it back in to database on edit
    Long assessmentStartLong;
    int courseID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_detail);

        //Set up variables for text views to fill with data sent by Course Detail
        editAssessmentName = findViewById(R.id.editAssessmentName);
        editAssessmentEnd = findViewById(R.id.editAssessmentEndDate);
        editRadPerf = findViewById(R.id.editRadPerf);
        editRadObj = findViewById(R.id.editRadObj);
        //Store assessmentID for when we update in the database
        assessmentID = getIntent().getIntExtra("id", 0);
        //Store courseID for when we update in the database
        courseID = getIntent().getIntExtra("course id", 0);
        //Set name in text field
        assessmentName = getIntent().getStringExtra("name");
        editAssessmentName.setText(getIntent().getStringExtra("name"));

        //Set end date (Long and Date) variables, put Date into text field
        assessmentEndLong = getIntent().getLongExtra("date end", 0);
        assessmentEndDate = toDate(assessmentEndLong);
        //We also need to format the date so it looks like MM/DD/YYYY
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        String displayDate = format.format(assessmentEndDate);
        editAssessmentEnd.setText(displayDate);

        //Set radio button - Check if it's already set to performance or objective and assign to radio button
        isPerf = getIntent().getBooleanExtra("is perf", false);
        if(isPerf == true){
            editRadPerf.setChecked(true);
        } else if(isPerf == false){
            editRadObj.setChecked(true);
        }
        //Store assessment start date, but we won't be changing it, just putting it back in
        assessmentStartLong = getIntent().getLongExtra("date start", 0);

    }

    //Save Changes button clicked - Update assessment
    public void onClickEditAssessment(View view){
        //isPerformanceAssessment in database is boolean - Get true or false.  Null if no button selected.  Null gives alertDialog
        Boolean assessmentType;
        if(editRadPerf.isChecked()){
            assessmentType = true;
        } else if (editRadObj.isChecked()){
            assessmentType = false;
        } else {
            assessmentType = null;
        }
        String name = editAssessmentName.getText().toString();
        String end = editAssessmentEnd.getText().toString();

        //Check if course name is blank
        //Display alert error if blank and do not continue
        if (name.isEmpty()) {
            AlertDialog.Builder dateAlert = new AlertDialog.Builder(this);
            dateAlert.setTitle("Assessment name not filled");
            dateAlert.setMessage("Assessment name cannot be left blank");
            dateAlert.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //Do nothing
                }
            });
            AlertDialog alertDialog = dateAlert.create();
            alertDialog.show();
        } else if(!datePatternMatches(end)){
            AlertDialog.Builder dateAlert = new AlertDialog.Builder(this);
            dateAlert.setTitle("Date format error");
            dateAlert.setMessage("End date format does not match MM/DD/YYYY.  Date cannot be blank");
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
        } else if(assessmentType == null){
            AlertDialog.Builder dateAlert = new AlertDialog.Builder(this);
            dateAlert.setTitle("No assessment type chosen");
            dateAlert.setMessage("Please select an option for assessment type.  Performance or Objective");
            dateAlert.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //Do nothing
                }
            });
            AlertDialog alertDialog = dateAlert.create();
            alertDialog.show();
        } else {
            //Continue with UPDATE
            Repository repository = new Repository(getApplication());

            //Parse end date into Long format for database storage
            assessmentEndLong = Date.parse(end);

            //Update assessment
            Assessment assessment = new Assessment(assessmentID, name, assessmentStartLong, assessmentEndLong, assessmentType, courseID);
            repository.updateAssessment(assessment);

            //Go back to TermList
            Intent intent = new Intent(AssessmentDetail.this, TermList.class);
            startActivity(intent);

        }
    }

    //Notify for this Assessment button clicked
    public void onClickAssessmentNotify(View view){
        Intent intent = new Intent(AssessmentDetail.this, CourseNotify.class);
        intent.putExtra("start long", assessmentStartLong);
        intent.putExtra("end long", assessmentEndLong);
        intent.putExtra("name", assessmentName);
        intent.putExtra("type", "Assessment");

        startActivity(intent);
    }

    //DELETE ASSESSMENT button clicked
    public void onClickDeleteAssessment(View view){
        Repository repository = new Repository(getApplication());
        Assessment assessment = new Assessment(assessmentID, assessmentName, assessmentStartLong, assessmentEndLong, isPerf, courseID);
        repository.deleteAssessment(assessment);

        //Go back to TermList
        Intent intent = new Intent(AssessmentDetail.this, TermList.class);
        startActivity(intent);
    }

}