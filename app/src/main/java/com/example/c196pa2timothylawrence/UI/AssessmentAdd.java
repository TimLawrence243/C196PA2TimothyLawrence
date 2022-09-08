package com.example.c196pa2timothylawrence.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.c196pa2timothylawrence.Database.Repository;
import com.example.c196pa2timothylawrence.Entity.Assessment;
import com.example.c196pa2timothylawrence.R;

import java.util.Date;

import static com.example.c196pa2timothylawrence.Converter.DateConverter.datePatternMatches;
import static com.example.c196pa2timothylawrence.Converter.DateConverter.fromDate;

public class AssessmentAdd extends AppCompatActivity {
    public static int assessmentAddCourseID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_add);
    }

    //Clicking save button
    public void onClickSaveAssessment(View view){
        //Get all the variables from fields
        //Radio button - Performance / Objective assessment
        final RadioButton radPerf = (RadioButton) findViewById(R.id.radPerf);
        final RadioButton radObj = (RadioButton) findViewById(R.id.radObj);
        //isPerformanceAssessment in database is boolean - Get true or false.  Null if no button selected.  Null gives alertDialog
        Boolean assessmentType;
        if(radPerf.isChecked()){
            assessmentType = true;
        } else if (radObj.isChecked()){
            assessmentType = false;
        } else {
            assessmentType = null;
        }
        //Name
        final EditText txtName = (EditText) findViewById(R.id.txtAssessmentName);
        String name = txtName.getText().toString();
        //Due date
        final EditText txtEnd = (EditText) findViewById(R.id.txtAssessmentEndDate);
        String end = txtEnd.getText().toString();

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
            //Continue with creation
            Repository repository = new Repository(getApplication());

            //Parse end date into Long format for database storage
            Long endDate = Date.parse(end);
            //Set start date as 'now'.  This is not visible at any point to the user, just for database storage
            Date startDateTemp = new Date();
            Long startDate = fromDate(startDateTemp);

            //Add new assessment to database
            Assessment assessment = new Assessment(0, name, startDate, endDate, assessmentType, assessmentAddCourseID);
            repository.insertAssessment(assessment);

            //Go back to TermList
            Intent intent = new Intent(AssessmentAdd.this, TermList.class);
            startActivity(intent);
        }

    }
}