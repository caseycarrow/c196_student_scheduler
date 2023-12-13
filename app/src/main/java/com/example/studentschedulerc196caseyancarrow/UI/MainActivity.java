package com.example.studentschedulerc196caseyancarrow.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.example.studentschedulerc196caseyancarrow.R;
import com.example.studentschedulerc196caseyancarrow.database.Repository;

public class MainActivity extends AppCompatActivity {
    public static int numAlert;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void TermList(View view) {
        Intent intent = new Intent(MainActivity.this, TermsList.class);
        startActivity(intent);
        Repository repository = new Repository(getApplication());

    }

    public void CourseList(View view) {
        Intent intent = new Intent(MainActivity.this, CourseList.class);
        startActivity(intent);
        Repository repository = new Repository(getApplication());
    }

    public void AssessmentList(View view) {
        Intent intent = new Intent(MainActivity.this, AssessmentList.class);
        startActivity(intent);
        Repository repository = new Repository(getApplication());
    }
}