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
import com.example.c196pa2timothylawrence.Entity.Assessment;
import com.example.c196pa2timothylawrence.Entity.Course;
import com.example.c196pa2timothylawrence.R;

import java.util.Date;
import java.util.List;

import static com.example.c196pa2timothylawrence.Converter.DateConverter.toDate;

public class CourseDetail extends AppCompatActivity {
    TextView editCourseName;
    TextView editCourseStart;
    TextView editCourseEnd;
    TextView editCourseStatus;
    TextView editCourseInstName;
    TextView editCourseInstEmail;
    TextView editCourseInstPhone;
    TextView editCourseNotes;
    int courseID;
    String courseName;
    Long courseStartDateLong;
    Long courseEndDateLong;
    Date courseStartDate;
    Date courseEndDate;
    String courseStatus;
    String courseInstName;
    String courseInstEmail;
    String courseInstPhone;
    String courseNotes;
    int termID;




    Repository repository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_detail);

        //Set up variables for text views to fill with data from CourseList
        editCourseName = findViewById(R.id.courseDetName);
        editCourseStart = findViewById(R.id.courseDetStart);
        editCourseEnd = findViewById(R.id.courseDetEnd);
        editCourseStatus = findViewById(R.id.courseDetStatus);
        editCourseInstName = findViewById(R.id.courseDetInstName);
        editCourseInstEmail = findViewById(R.id.courseDetInstEmail);
        editCourseInstPhone = findViewById(R.id.courseDetInstPhone);
        editCourseNotes = findViewById(R.id.courseDetNotes);

        //Store course ID (foreign key for assessments)
        courseID = getIntent().getIntExtra("id", 0);
        AssessmentAdd.assessmentAddCourseID = courseID;
        //Store term ID for update / deletes
        termID = getIntent().getIntExtra("term id", 0);

        //Assign variables from intents sent by courseList
        courseName = getIntent().getStringExtra("name");
        courseStartDateLong = getIntent().getLongExtra("date start", 0);
        courseEndDateLong = getIntent().getLongExtra("date end", 0);
        //Convert stored Long start and end dates to Date
        courseStartDate = toDate(courseStartDateLong);
        courseEndDate = toDate(courseEndDateLong);
        courseStatus = getIntent().getStringExtra("status");
        courseInstName = getIntent().getStringExtra("instructor name");
        courseInstEmail = getIntent().getStringExtra("instructor email");
        courseInstPhone = getIntent().getStringExtra("instructor phone");
        courseNotes = getIntent().getStringExtra("notes");

        //Splitting dates to remove time and time zone
        //parts array takes Day of week, day of month, month, and then year from Date
        String sDate = courseStartDate.toString();
        String[] parts = sDate.split(" ");
        sDate = (parts[0] + " " + parts[1] + " " + parts[2] + " " + parts[5]);

        String eDate = courseEndDate.toString();
        parts = eDate.split(" ");
        eDate = (parts[0] + " " + parts[1] + " " + parts[2] + " " + parts[5]);

        //Fill text views with data
        editCourseName.setText(courseName);
        editCourseStart.setText(sDate);
        editCourseEnd.setText(eDate);
        editCourseStatus.setText(courseStatus);
        editCourseInstName.setText(courseInstName);
        editCourseInstEmail.setText(courseInstEmail);
        editCourseInstPhone.setText(courseInstPhone);
        editCourseNotes.setText(courseNotes);

        //Recycler for assessments
        RecyclerView recyclerView = findViewById(R.id.assessmentRecycler);
        //Set up repository for this page
        Repository assessmentRepo = new Repository(getApplication());
        //Get list of all assessments for the term ID we selected
        List<Assessment> assessments = assessmentRepo.getAllAssessmentsForCourse(courseID);
        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(assessmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        assessmentAdapter.setAssessments(assessments);
    }

    //Add assessment button - Move to add assessment screen
    public void addAssessmentBtn(View view){
        Intent intent = new Intent(CourseDetail.this, AssessmentAdd.class);
        startActivity(intent);
    }

    //Edit Course button - Move to Edit Course screen
    public void onClickEditCourse(View view){
        //Take all the info for this course and move to edit course screen
        Intent intent = new Intent(CourseDetail.this, CourseEdit.class);
        intent.putExtra("id", courseID);
        intent.putExtra("name", courseName);
        intent.putExtra("date start", courseStartDateLong);
        intent.putExtra("date end", courseEndDateLong);
        intent.putExtra("status", courseStatus);
        intent.putExtra("instructor name", courseInstName);
        intent.putExtra("instructor email", courseInstEmail);
        intent.putExtra("instructor phone", courseInstPhone);
        intent.putExtra("notes", courseNotes);
        intent.putExtra("term id", termID);

        startActivity(intent);
    }

    //Delete course button clicked
    public void onClickDeleteCourse(View view){
        AlertDialog.Builder dateAlert = new AlertDialog.Builder(this);
        dateAlert.setTitle("Confirm");
        dateAlert.setMessage("Delete this course? Any attached Assessments will also be deleted.");
        dateAlert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //YES selected - Continue with deletion
                Repository repository = new Repository(getApplication());
                Course course = new Course(courseID, courseName, courseStartDateLong, courseEndDateLong, courseStatus, courseInstName, courseInstEmail, courseInstPhone, courseNotes, termID);

                //Delete all assessments for the course
                repository.deleteAssessmentsForCourse(courseID);
                //Delete course
                repository.deleteCourse(course);

                //Return to Term List
                Intent intent = new Intent(CourseDetail.this, TermList.class);
                startActivity(intent);
            }

        });

        dateAlert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Cancel selected - Do nothing
            }
        });
        AlertDialog alertDialog = dateAlert.create();
        alertDialog.show();


    }

    //Notify for this course button clicked
    public void onClickCourseNotify(View view){
        Intent intent = new Intent(CourseDetail.this, CourseNotify.class);
        intent.putExtra("start long", courseStartDateLong);
        intent.putExtra("end long", courseEndDateLong);
        intent.putExtra("name", courseName);
        intent.putExtra("type", "Course");

        startActivity(intent);
    }

    //Share course button clicked
    public void onClickShareCourse(View view){
        String stringToSend = "Course name: " + courseName + "\n"
                + "Course Notes: " + courseNotes;
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, stringToSend);
        sendIntent.putExtra(Intent.EXTRA_TITLE, "title");
        sendIntent.setType("text/plain");
        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);
    }


}