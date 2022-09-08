package com.example.c196pa2timothylawrence.Entity;


import java.util.Date;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "assessments")
public class Assessment {
    @PrimaryKey(autoGenerate = true)
    private int assessmentID;
    private String assessmentName;
    private Long assessmentStart;
    private Long assessmentEnd;
    private boolean isPerformanceAssessment;
    private int courseID;




    public Assessment(int assessmentID, String assessmentName, Long assessmentStart, Long assessmentEnd, boolean isPerformanceAssessment, int courseID){
        this.assessmentID = assessmentID;
        this.assessmentName = assessmentName;
        this.assessmentStart = assessmentStart;
        this.assessmentEnd = assessmentEnd;
        this.isPerformanceAssessment = isPerformanceAssessment;
        this.courseID = courseID;
    }

    public int getAssessmentID() {
        return assessmentID;
    }

    public void setAssessmentID(int assessmentID) {
        this.assessmentID = assessmentID;
    }

    public String getAssessmentName() {
        return assessmentName;
    }

    public void setAssessmentName(String assessmentName) {
        this.assessmentName = assessmentName;
    }

    public Long getAssessmentStart() {
        return assessmentStart;
    }

    public void setAssessmentStart(Long assessmentStart) {
        this.assessmentStart = assessmentStart;
    }

    public Long getAssessmentEnd() {
        return assessmentEnd;
    }

    public void setAssessmentEnd(Long assessmentEnd) {
        this.assessmentEnd = assessmentEnd;
    }

    public boolean isPerformanceAssessment() { return isPerformanceAssessment; }

    public void setPerformanceAssessment(boolean performanceAssessment) { isPerformanceAssessment = performanceAssessment; }

    public int getCourseID() { return courseID; }

    public void setCourseID(int courseID) { this.courseID = courseID; }

    @Override
    public String toString() {
        return "Assessment{" +
                "assessmentID=" + assessmentID +
                ", assessmentName='" + assessmentName + '\'' +
                ", assessmentStart=" + assessmentStart +
                ", assessmentEnd=" + assessmentEnd +
                ", isPerformanceAssessment=" + isPerformanceAssessment +
                ", course ID + " + courseID +
                '}';
    }
}
