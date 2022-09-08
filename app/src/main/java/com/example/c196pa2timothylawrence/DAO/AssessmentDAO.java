package com.example.c196pa2timothylawrence.DAO;

import com.example.c196pa2timothylawrence.Entity.Assessment;
import com.example.c196pa2timothylawrence.Entity.Course;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface AssessmentDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Assessment assessment);

    @Update
    void update(Assessment assessment);

    @Delete
    void delete(Assessment assessment);

    @Query("SELECT * FROM assessments ORDER BY assessmentID ASC")
    List<Assessment> getAllAssessments();

    @Query("SELECT * FROM assessments WHERE courseID = :courseID ")
    List<Assessment> getAllAssessmentsForCourse(int courseID);

    @Query("DELETE FROM assessments WHERE courseID = :courseID")
    void deleteAssessmentsForCourse(int courseID);
}
