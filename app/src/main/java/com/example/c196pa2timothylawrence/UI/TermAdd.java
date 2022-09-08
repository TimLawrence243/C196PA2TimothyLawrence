package com.example.c196pa2timothylawrence.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.c196pa2timothylawrence.Database.Repository;
import com.example.c196pa2timothylawrence.Entity.Term;
import com.example.c196pa2timothylawrence.R;

import java.util.Date;

import static com.example.c196pa2timothylawrence.Converter.DateConverter.datePatternMatches;
import static com.example.c196pa2timothylawrence.Converter.DateConverter.fromDate;
import static com.example.c196pa2timothylawrence.Converter.DateConverter.toDate;

public class TermAdd extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_add);

        TextView errorText = findViewById(R.id.txtFormatError);
        errorText.setVisibility(TextView.INVISIBLE);
    }

    //Clicking Save button
    public void onClickSaveTerm(View view){
        //Get term name from text field
        final EditText txtName = (EditText) findViewById(R.id.txtTermName);
        String name = txtName.getText().toString();

        //Get start date from start date field
        final EditText txtStart = (EditText) findViewById(R.id.txtStartDate);
        String start = txtStart.getText().toString();

        //Get end date from end date field
        final EditText txtEnd = (EditText) findViewById(R.id.txtEndDate);
        String end = txtEnd.getText().toString();


        //Check if start and end dates match pattern MM/DD/YYYY
        if(datePatternMatches(start) && datePatternMatches(end)){
            Repository repository = new Repository(getApplication());

            //Parse start and end dates into Long format for database storage
            Long startDate = Date.parse(start);
            Long endDate = Date.parse(end);

            //Testing
//            System.out.println(name);
//            System.out.println(toDate(startDate));
//            System.out.println(toDate(endDate));

            //Add the Term to database
            repository.insertTerm(new Term(0, name, startDate, endDate));

            //Go back to TermList
            Intent intent = new Intent(TermAdd.this, TermList.class);
            startActivity(intent);
        } else {
            //Set errorText visible as we've failed the MM/DD/YYYY pattern match, do not add to database or continue
            TextView errorText = findViewById(R.id.txtFormatError);
            errorText.setVisibility(TextView.VISIBLE);
        }

    }
}