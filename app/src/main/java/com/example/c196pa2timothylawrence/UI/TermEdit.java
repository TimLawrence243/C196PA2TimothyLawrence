package com.example.c196pa2timothylawrence.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.c196pa2timothylawrence.Database.Repository;
import com.example.c196pa2timothylawrence.Entity.Term;
import com.example.c196pa2timothylawrence.R;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.c196pa2timothylawrence.Converter.DateConverter.datePatternMatches;
import static com.example.c196pa2timothylawrence.Converter.DateConverter.toDate;

public class TermEdit extends AppCompatActivity {
    //Initialize variables for text fields
    TextView editTermName;
    TextView editTermStart;
    TextView editTermEnd;

    //Variables for date
    int termID;
    String termName;
    Long termStartLong;
    Long termEndLong;
    Date termStartDate;
    Date termEndDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_edit);

        //Set up variables for text views to fill with data from CourseList
        editTermName = findViewById(R.id.editTermName);
        editTermStart = findViewById(R.id.editTermStart);
        editTermEnd = findViewById(R.id.editTermEnd);

        //Assign term values to variables
        termID = getIntent().getIntExtra("id", 0);
        termName = getIntent().getStringExtra("name");
        termStartLong = getIntent().getLongExtra("start", 0);
        termEndLong = getIntent().getLongExtra("end", 0);

        //Format start and end dates for text fields to MM/DD/YYYY
        termStartDate = toDate(termStartLong);
        termEndDate = toDate(termEndLong);
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        String displayStartDate = format.format(termStartDate);
        String displayEndDate = format.format(termEndDate);

        //Fill text fields with data
        editTermName.setText(termName);
        editTermStart.setText(displayStartDate);
        editTermEnd.setText(displayEndDate);
    }

    public void onClickUpdateTerm(View view){
        termName = editTermName.getText().toString();
        String start = editTermStart.getText().toString();
        String end = editTermEnd.getText().toString();

        //Check if term name is blank
        //Display alert error if blank and do not continue
        if (termName.isEmpty()) {
            AlertDialog.Builder dateAlert = new AlertDialog.Builder(this);
            dateAlert.setTitle("Term name not filled");
            dateAlert.setMessage("Term name cannot be left blank");
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
        } else if(!datePatternMatches(start) || !datePatternMatches(end)) {
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
        } else {
            //Continue with update
            Repository repository = new Repository(getApplication());

            //Parse start and end dates into Long format for database storage
            Long startDate = Date.parse(start);
            Long endDate = Date.parse(end);

            //Update term
            Term term = new Term(termID, termName, startDate, endDate);
            repository.updateTerm(term);

            //Go back to TermList
            Intent intent = new Intent(TermEdit.this, TermList.class);
            startActivity(intent);

        }
    }
}