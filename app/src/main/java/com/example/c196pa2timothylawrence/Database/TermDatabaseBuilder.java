package com.example.c196pa2timothylawrence.Database;

import android.content.Context;

import com.example.c196pa2timothylawrence.Converter.DateConverter;
import com.example.c196pa2timothylawrence.DAO.AssessmentDAO;
import com.example.c196pa2timothylawrence.DAO.CourseDAO;
import com.example.c196pa2timothylawrence.DAO.TermDAO;
import com.example.c196pa2timothylawrence.Entity.Assessment;
import com.example.c196pa2timothylawrence.Entity.Course;
import com.example.c196pa2timothylawrence.Entity.Term;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

@Database(entities = {Term.class, Course.class, Assessment.class}, version = 5, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class TermDatabaseBuilder extends RoomDatabase {
    public abstract TermDAO termDAO();
    public abstract CourseDAO courseDAO();
    public abstract AssessmentDAO assessmentDAO();

    private static volatile TermDatabaseBuilder INSTANCE;


    static TermDatabaseBuilder getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (TermDatabaseBuilder.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), TermDatabaseBuilder.class, "mDatabase.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
