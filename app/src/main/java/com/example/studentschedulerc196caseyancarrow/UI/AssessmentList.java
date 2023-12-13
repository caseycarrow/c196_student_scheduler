package com.example.studentschedulerc196caseyancarrow.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentschedulerc196caseyancarrow.R;
import com.example.studentschedulerc196caseyancarrow.database.Repository;
import com.example.studentschedulerc196caseyancarrow.entities.Assessment;

import java.util.List;
import java.util.Objects;

public class AssessmentList extends AppCompatActivity {
    List<com.example.studentschedulerc196caseyancarrow.entities.Assessment> assessments;
    Repository repository;
    AssessmentAdapter adapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_list);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        recyclerView=findViewById(R.id.recyclerViewAssessment);
        repository = new Repository(getApplication());
        assessments = repository.getAllAssessments();
        adapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setAssessments(assessments);
    }

    public void AddAssessment(View view) {
        Intent intent = new Intent(AssessmentList.this, AssessmentDetails.class);
        startActivity(intent);
    }

    @Override
    protected void onResume(){
        super.onResume();
        Repository repository = new Repository(getApplication());
        RecyclerView recyclerViewOnResume = findViewById(R.id.recyclerViewAssessment);
        final AssessmentAdapter assessmentAdapterOnResume = new AssessmentAdapter(this);
        recyclerViewOnResume.setAdapter(assessmentAdapterOnResume);
        recyclerViewOnResume.setLayoutManager(new LinearLayoutManager(this));
        List<Assessment> assessments = repository.getAllAssessments();
        assessmentAdapterOnResume.setAssessments(assessments);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_assesment_list, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        } else if (item.getItemId() == R.id.refreshAssessmentList) {
            assessments = repository.getAllAssessments();
            recyclerView.setAdapter(adapter);
            adapter.setAssessments(assessments);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }


}
