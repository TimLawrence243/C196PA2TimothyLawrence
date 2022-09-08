package com.example.c196pa2timothylawrence.Entity;


import java.util.Date;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "courses")
public class Course {
    @PrimaryKey(autoGenerate = true)
    private int courseID;
    private String courseName;
    private Long courseStart;
    private Long courseEnd;
    private String courseStatus;
    private String courseInstructor;
    private String courseEmail;
    private String coursePhone;
    private String courseNotes;
    private int termID;



    public Course(int courseID,
                  String courseName,
                  Long courseStart,
                  Long courseEnd,
                  String courseStatus,
                  String courseInstructor,
                  String courseEmail,
                  String coursePhone,
                  String courseNotes,
                  int termID){
        this.courseID = courseID;
        this.courseName = courseName;
        this.courseStart = courseStart;
        this.courseEnd = courseEnd;
        this.courseStatus = courseStatus;
        this.courseInstructor = courseInstructor;
        this.courseEmail = courseEmail;
        this.coursePhone = coursePhone;
        this.courseNotes = courseNotes;
        this.termID = termID;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Long getCourseStart() {
        return courseStart;
    }

    public void setCourseStart(Long courseStart) {
        this.courseStart = courseStart;
    }

    public Long getCourseEnd() {
        return courseEnd;
    }

    public void setCourseEnd(Long courseEnd) {
        this.courseEnd = courseEnd;
    }

    public String getCourseStatus() {
        return courseStatus;
    }

    public void setCourseStatus(String courseStatus) {
        this.courseStatus = courseStatus;
    }

    public String getCourseInstructor() {
        return courseInstructor;
    }

    public void setCourseInstructor(String courseInstructor) {
        this.courseInstructor = courseInstructor;
    }

    public String getCourseEmail() {
        return courseEmail;
    }

    public void setCourseEmail(String courseEmail) {
        this.courseEmail = courseEmail;
    }

    public String getCoursePhone() {
        return coursePhone;
    }

    public void setCoursePhone(String coursePhone) {
        this.coursePhone = coursePhone;
    }

    public String getCourseNotes() {
        return courseNotes;
    }

    public void setCourseNotes(String courseNotes) {
        this.courseNotes = courseNotes;
    }

    public int getTermID() { return termID; }

    public void setTermID(int termID) { this.termID = termID; }

    @Override
    public String toString() {
        return "Course{" +
                "courseID=" + courseID +
                ", courseName='" + courseName + '\'' +
                ", courseStart=" + courseStart +
                ", courseEnd=" + courseEnd +
                ", courseStatus='" + courseStatus + '\'' +
                ", courseInstructor='" + courseInstructor + '\'' +
                ", courseEmail='" + courseEmail + '\'' +
                ", coursePhone='" + coursePhone + '\'' +
                ", courseNotes='" + courseNotes + '\'' +
                ", termID='" + termID + '\'' +
                '}';
    }
}
