package com.example.c196pa2timothylawrence.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.c196pa2timothylawrence.Database.Repository;
import com.example.c196pa2timothylawrence.Entity.Term;
import com.example.c196pa2timothylawrence.R;

import java.util.Date;

import static com.example.c196pa2timothylawrence.Converter.DateConverter.fromDate;

public class MainActivity extends AppCompatActivity {
    public static int numAlert;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void goToTermList(View view){
        //Move to list of terms
        Intent intent = new Intent(MainActivity.this, TermList.class);
        startActivity(intent);


//        Repository repository = new Repository(getApplication());
//        Date s = new Date(2022, 1, 1);
//        Date e = new Date(2022, 1, 2);
//        Term term = new Term(1, "Term One", fromDate(s), fromDate(e));
//        repository.insertTerm(term);
    }


}