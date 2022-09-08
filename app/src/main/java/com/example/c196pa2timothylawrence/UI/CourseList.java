package com.example.c196pa2timothylawrence.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.c196pa2timothylawrence.Database.Repository;
import com.example.c196pa2timothylawrence.Entity.Course;
import com.example.c196pa2timothylawrence.R;

import java.util.Date;
import java.util.List;

import static com.example.c196pa2timothylawrence.Converter.DateConverter.toDate;

public class CourseList extends AppCompatActivity {
    TextView editTermName;
    TextView editTermStart;
    TextView editTermEnd;
    String termName;
    int termID;
    Long termStartDateLong;
    Long termEndDateLong;
    Date termStartDate;
    Date termEndDate;

    Repository repository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_list);

        //Set up variables for text views to fill with data from TermList
        editTermName = findViewById(R.id.txtClassListTermName);
        editTermStart = findViewById(R.id.txtClassListTermStart);
        editTermEnd = findViewById(R.id.txtClassListTermEnd);
        termName = getIntent().getStringExtra("name");
        //Store term ID for when we go to add a course to the term (Foreign key)
        termID = getIntent().getIntExtra("id", 0);
        CourseAdd.courseAddTermID = termID;
        //Store start and end date from intents as Long.  Convert to Date for readability
        termStartDateLong = getIntent().getLongExtra("date start", 0);
        termEndDateLong = getIntent().getLongExtra("date end", 0);
        //Convert stored Long start and end dates to Date
        termStartDate = toDate(termStartDateLong);
        termEndDate = toDate(termEndDateLong);

        //Splitting dates to remove time and time zone
        //parts array takes Day of week, day of month, month, and then year from Date
        String sDate = termStartDate.toString();
        String[] parts = sDate.split(" ");
        sDate = (parts[0] + " " + parts[1] + " " + parts[2] + " " + parts[5]);

        String eDate = termEndDate.toString();
        parts = eDate.split(" ");
        eDate = (parts[0] + " " + parts[1] + " " + parts[2] + " " + parts[5]);

        //Fill text views with data
        editTermName.setText(termName);
        editTermStart.setText(sDate);
        editTermEnd.setText(eDate);

        //Recycler view set up
        RecyclerView recyclerView = findViewById(R.id.courseRecycler);
        //Set up repository for this page
        repository = new Repository(getApplication());
        //Get list of all courses for the term ID we selected
        List<Course> courses = repository.getAllCoursesForTerm(termID);
        final CourseAdapter courseAdapter = new CourseAdapter(this);
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        courseAdapter.setCourses(courses);

    }

    public void addClassBtn(View view){
        Intent intent=new Intent(CourseList.this, CourseAdd.class);
        startActivity(intent);
    }

    //Edit Term button - Move to Edit Term screen
    public void onClickEditTerm(View view){
        //Take all the info for this Term and move to edit course screen
        Intent intent = new Intent(CourseList.this, TermEdit.class);
        intent.putExtra("id", termID);
        intent.putExtra("name", termName);
        intent.putExtra("start", termStartDateLong);
        intent.putExtra("end", termEndDateLong);

        startActivity(intent);
    }

    //Delete Term button
    public void onClickDeleteTerm(View view){
        //Check if any courses are attached to the current term before deleting
        int coursesInTerm = repository.checkCoursesInTerm(termID);
        //If we have greater than 0 courses attached to the term, create alertDialog and do not delete
        if (coursesInTerm !=0){
            AlertDialog.Builder dateAlert = new AlertDialog.Builder(this);
            dateAlert.setTitle("Error: Term has courses attached.");
            dateAlert.setMessage("Term cannot be deleted - Active courses must first be deleted");
            dateAlert.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //Do nothing
                }
            });
            AlertDialog alertDialog = dateAlert.create();
            alertDialog.show();
        } else {
            //delete the current term
            repository.deleteTerm(termID);

            //Return to TermList screen
            Intent intent = new Intent(CourseList.this, TermList.class);
            startActivity(intent);
        }


    }


}