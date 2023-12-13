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
import com.example.studentschedulerc196caseyancarrow.entities.Course;

import java.util.List;
import java.util.Objects;

public class CourseList extends AppCompatActivity {
    List<Course> courses;
    Repository repository;
    CourseAdapter courseAdapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        recyclerView = findViewById(R.id.recyclerview2);
        repository = new Repository(getApplication());
        courses = repository.getAllCourses();
        courseAdapter = new CourseAdapter(this);
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        courseAdapter.setCourse(courses);

    }

    public void AddCourse(View view) {
        Intent intent = new Intent(CourseList.this, CourseDetails.class);
        startActivity(intent);
    }

    @Override
    protected void onResume(){
        super.onResume();
        Repository repository = new Repository(getApplication());
        RecyclerView recyclerViewOnResume = findViewById(R.id.recyclerview2);
        final CourseAdapter courseAdapterOnResume = new CourseAdapter(this);
        recyclerViewOnResume.setAdapter(courseAdapterOnResume);
        recyclerViewOnResume.setLayoutManager(new LinearLayoutManager(this));
        List<Course> courses = repository.getAllCourses();
        courseAdapterOnResume.setCourse(courses);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_course_list, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        } else if (item.getItemId() == R.id.refreshCourseList) {
            courses = repository.getAllCourses();
            recyclerView.setAdapter(courseAdapter);
            courseAdapter.setCourse(courses);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}
