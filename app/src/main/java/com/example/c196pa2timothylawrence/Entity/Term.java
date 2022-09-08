package com.example.c196pa2timothylawrence.Entity;

import java.util.Date;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "terms")
public class Term {
    @PrimaryKey(autoGenerate = true)
    private int termID;
    private String termName;

    private Long termStart;
    private Long termEnd;

    public Term(int termID, String termName, Long termStart, Long termEnd){
        this.termID = termID;
        this.termName = termName;
        this.termStart = termStart;
        this.termEnd = termEnd;
    }

    public int getTermID() {
        return termID;
    }

    public void setTermID(int termID) {
        this.termID = termID;
    }

    public String getTermName() {
        return termName;
    }

    public void setTermName(String termName) {
        this.termName = termName;
    }

    public Long getTermStart() {
        return termStart;
    }

    public void setTermStart(Long termStart) {
        this.termStart = termStart;
    }

    public Long getTermEnd() {
        return termEnd;
    }

    public void setTermEnd(Long termEnd) {
        this.termEnd = termEnd;
    }

    @Override
    public String toString() {
        return "Term{" +
                "termID=" + termID +
                ", termName='" + termName + '\'' +
                ", termStart=" + termStart +
                ", termEnd=" + termEnd +
                '}';
    }
}
