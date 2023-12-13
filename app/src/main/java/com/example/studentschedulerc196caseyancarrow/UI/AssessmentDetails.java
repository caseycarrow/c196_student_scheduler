package com.example.studentschedulerc196caseyancarrow.UI;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.studentschedulerc196caseyancarrow.R;
import com.example.studentschedulerc196caseyancarrow.database.Repository;
import com.example.studentschedulerc196caseyancarrow.entities.Assessment;
import com.example.studentschedulerc196caseyancarrow.entities.Course;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

public class AssessmentDetails extends AppCompatActivity {
    final Calendar myCalStart = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener startDate;
    DatePickerDialog.OnDateSetListener endDate;
    Repository repository;
    int assessmentID;
    EditText assessmentNameEditText;
    EditText assessmentStartEditText;
    EditText assessmentEndEditText;
    SimpleDateFormat sdf;
    Spinner assessmentTypeSpinner;
    Spinner nameSpinner;
    String assessmentStatus;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_details);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        repository=new Repository(getApplication());
        assessmentID = getIntent().getIntExtra("assessmentID", -1);
        assessmentNameEditText = findViewById(R.id.assessmentTitleEditText);
        assessmentNameEditText.setText(getIntent().getStringExtra("name"));
        assessmentTypeSpinner = findViewById(R.id.assessmentTypeSpinner);
        List<String> types = new ArrayList<>();
        types.add("Performance");
        types.add("Objective");
        ArrayAdapter<String> types1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, types);
        types1.setDropDownViewResource(android.R.layout.simple_spinner_item);
        assessmentTypeSpinner.setAdapter(types1);
        assessmentTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                assessmentStatus = assessmentTypeSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        String type = getIntent().getStringExtra("type");
        if(type == null) assessmentTypeSpinner.setSelection(0);
        else if(type.equals("Performance")) assessmentTypeSpinner.setSelection(0);
        else assessmentTypeSpinner.setSelection(1);

        List<Course> allCourses = repository.getAllCourses();
        List<String> names = new ArrayList<>();
        for(Course course : allCourses) {
            names.add(course.getTitle());
        }
        ArrayAdapter<String> namesArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, names);
        namesArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        nameSpinner= findViewById(R.id.CourseSpinner);

        nameSpinner.setAdapter(namesArrayAdapter);
        nameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                name = nameSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        nameSpinner.setSelection(getIndex(nameSpinner, getIntent().getStringExtra("name")));
        assessmentStartEditText = findViewById(R.id.editAssessmentStart);
        assessmentEndEditText = findViewById(R.id.editAssessmentEnd);
        String myFormat = "mm/dd/yy";
        sdf = new SimpleDateFormat(myFormat, Locale.US);
        assessmentStartEditText.setText(getIntent().getStringExtra("start"));
        assessmentEndEditText.setText(getIntent().getStringExtra("end"));
        assessmentStartEditText.setOnClickListener(view -> {

            String info = assessmentStartEditText.getText().toString();
            if(info.equals(""))info= "11/26/2023";
            try {
                myCalStart.setTime(Objects.requireNonNull(sdf.parse(info)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            new DatePickerDialog(AssessmentDetails.this, startDate, myCalStart.get(Calendar.YEAR),
                    myCalStart.get(Calendar.MONTH), myCalStart.get(Calendar.DAY_OF_MONTH)).show();
        });
        assessmentEndEditText.setOnClickListener(view -> {
            String info = assessmentEndEditText.getText().toString();
            if(info.equals(""))info= "11/26/2023";
            try {
                myCalStart.setTime(Objects.requireNonNull(sdf.parse(info)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            new DatePickerDialog(AssessmentDetails.this, endDate, myCalStart.get(Calendar.YEAR),
                    myCalStart.get(Calendar.MONTH), myCalStart.get(Calendar.DAY_OF_MONTH)).show();

        });
        startDate= (datePicker, year, month, day) -> {
            myCalStart.set(Calendar.YEAR, year);
            myCalStart.set(Calendar.MONTH, month);
            myCalStart.set(Calendar.DAY_OF_MONTH, day);
            updateStart();
        };
        endDate= (datePicker, year, month, day) -> {
            myCalStart.set(Calendar.YEAR, year);
            myCalStart.set(Calendar.MONTH, month);
            myCalStart.set(Calendar.DAY_OF_MONTH, day);
            updateEnd();
        };
    }
    public void updateStart() { assessmentStartEditText.setText(sdf.format(myCalStart.getTime()));}
    public void updateEnd() { assessmentEndEditText.setText(sdf.format(myCalStart.getTime()));}
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_assessment_detail, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == android.R.id.home) {
            this.finish();
            return true;
        } else if (itemId == R.id.notifyAssessmentStart) {
            handleAssessmentNotification(assessmentStartEditText.getText().toString(), "Start of Assessment: ");
            return true;
        } else if (itemId == R.id.notifyAssessmentEnd) {
            handleAssessmentNotification(assessmentEndEditText.getText().toString(), "End of Assessment: ");
            return true;
        } else if (itemId == R.id.deleteAssessment) {
            handleDeleteAssessment();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void handleAssessmentNotification(String dateFromScreen, String notificationTextPrefix) {
        Date myDate = null;
        try {
            myDate = sdf.parse(dateFromScreen);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (myDate != null) {
            long trigger = myDate.getTime();
            String notificationText = notificationTextPrefix + assessmentNameEditText.getText().toString();
            Intent intent = new Intent(AssessmentDetails.this, MyReceiver.class);
            intent.putExtra("key", notificationText);
            PendingIntent sender = PendingIntent.getBroadcast(AssessmentDetails.this, MainActivity.numAlert++, intent, PendingIntent.FLAG_IMMUTABLE);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, trigger, sender);
            String toastMessage = notificationTextPrefix.contains("Start") ? "Start date notification set" : "End date notification set";
            Toast.makeText(AssessmentDetails.this, toastMessage, Toast.LENGTH_SHORT).show();
        }
    }

    private void handleDeleteAssessment() {
        if (assessmentID == -1) {
            Toast.makeText(AssessmentDetails.this, "Cannot delete a non-saved assessment", Toast.LENGTH_SHORT).show();
        } else {
            List<Assessment> allAssessments = repository.getAllAssessments();
            for (Assessment assessment : allAssessments) {
                if (assessment.getAssessmentID() == assessmentID) {
                    repository.delete(assessment);
                    Toast.makeText(AssessmentDetails.this, "Assessment deleted", Toast.LENGTH_SHORT).show();
                }
            }
        }
        finish();
    }

    public int getIndex(Spinner spinner, String name) {
        for(int i = 0;i < spinner.getCount();i++) {
            if(spinner.getItemAtPosition(i).toString().equals(name))
                return i;
        }
        return 0;
    }
    public int getCourseID(String name) {
        List<Course> courses = repository.getAllCourses();
        for(Course course : courses) {
            if(course.getTitle().equals(name)) {
                return course.getCourseID();
            }
        }
        return -1;
    }
    public void saveButton(View view) {
        Assessment assessments;
        List<Assessment> allAssessments = repository.getAllAssessments();
        String assessmentName = assessmentNameEditText.getText().toString();
        boolean nameCheck = false;
        if(name == null) {
            Toast.makeText(AssessmentDetails.this, "Please add courses before adding assessment", Toast.LENGTH_SHORT).show();
        }
        else if(assessmentID == -1) {
            for(Assessment assessment : allAssessments) {
                if (assessment.getTitle().equals(assessmentName)) {
                    nameCheck = true;
                    break;
                }
            }
            if(nameCheck) Toast.makeText(AssessmentDetails.this, "Assessment with this name already exists. Choose new name", Toast.LENGTH_SHORT).show();
            else if(allAssessments.size() == 0) {
                assessments = new Assessment(1, getCourseID(name), assessmentNameEditText.getText().toString(), assessmentStatus, assessmentStartEditText.getText().toString(),
                        assessmentEndEditText.getText().toString());
                repository.insert(assessments);
                Toast.makeText(AssessmentDetails.this, "Assessment saved", Toast.LENGTH_SHORT).show();
            }
            else {
                int newID = repository.getAllAssessments().get(repository.getAllAssessments().size() - 1).getAssessmentID() + 1;
                assessments = new Assessment(newID, getCourseID(name), assessmentNameEditText.getText().toString(), assessmentStatus, assessmentStartEditText.getText().toString(),
                        assessmentEndEditText.getText().toString());
                repository.insert(assessments);
                Toast.makeText(AssessmentDetails.this, "Assessment saved", Toast.LENGTH_SHORT).show();
            }

        }
        else {
            for(Assessment assessment : allAssessments) {
                if (assessment.getTitle().equals(name) && assessment.getAssessmentID() != assessmentID) {
                    nameCheck = true;
                    break;
                }
            }
            if(nameCheck) Toast.makeText(AssessmentDetails.this, "Assessment with this name already exists. Choose new name", Toast.LENGTH_SHORT).show();
            else {
                assessments = new Assessment(assessmentID, getCourseID(name), assessmentNameEditText.getText().toString(), assessmentStatus, assessmentStartEditText.getText().toString(),
                        assessmentEndEditText.getText().toString());
                repository.update(assessments);
                Toast.makeText(AssessmentDetails.this, "Assessment updated", Toast.LENGTH_SHORT).show();
            }

        }
        finish();
    }

}

