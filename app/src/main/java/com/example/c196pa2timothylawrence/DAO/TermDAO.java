package com.example.c196pa2timothylawrence.DAO;


import com.example.c196pa2timothylawrence.Entity.Course;
import com.example.c196pa2timothylawrence.Entity.Term;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface TermDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Term term);

    @Update
    void update(Term term);

    @Delete
    void delete(Term term);

    @Query("SELECT * FROM terms ORDER BY termID ASC")
    List<Term> getAllTerms();

    @Query("DELETE FROM terms WHERE termID = :termID")
    void deleteTerm(int termID);
}
