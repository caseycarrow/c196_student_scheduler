package com.example.studentschedulerc196caseyancarrow.UI;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.studentschedulerc196caseyancarrow.R;
import com.example.studentschedulerc196caseyancarrow.database.Repository;
import com.example.studentschedulerc196caseyancarrow.entities.Course;
import com.example.studentschedulerc196caseyancarrow.entities.Term;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class CourseDetails extends AppCompatActivity {
    final  Calendar myCalStart = Calendar.getInstance();
    EditText courseNameEdit;
    EditText courseStartEdit;
    EditText courseEndEdit;
    String courseStatus;
    EditText instructorNameEdit;
    EditText instructorEmailEdit;
    EditText instructorPhoneEdit;
    EditText notesEditText;
    int courseID;
    int termID;
    String myFormat;
    String selectedTerm;
    RadioGroup statusRadio;
    Spinner spinner;
    SimpleDateFormat sdf;
    Repository repository;
    DatePickerDialog.OnDateSetListener courseStartDate;
    DatePickerDialog.OnDateSetListener courseEndDate;
    List<Term> allTerms;
    List<Course> allCourses;
    Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        repository = new Repository(getApplication());
        allTerms = repository.getAllTerms();
        allCourses = repository.getAllCourses();
        courseID = getIntent().getIntExtra("courseID", -1);
        termID = getIntent().getIntExtra("termID", -1);
        myFormat = "MM/dd/yy";
        sdf = new SimpleDateFormat(myFormat, Locale.US);

        //findViewById
        courseNameEdit = findViewById(R.id.courseNameEditText);
        courseStartEdit = findViewById(R.id.courseStartDateEditText);
        courseEndEdit = findViewById(R.id.courseEndDateEditText);
        instructorNameEdit = findViewById(R.id.instructorNameEditText);
        instructorEmailEdit = findViewById(R.id.instructorEmailEditText);
        instructorPhoneEdit = findViewById(R.id.instructorPhoneEditText);
        notesEditText = findViewById(R.id.notesEditText);
        statusRadio = findViewById(R.id.statusRadioGroup);
        saveButton = findViewById(R.id.saveButton2);
        //setText(getIntent())
        courseNameEdit.setText(getIntent().getStringExtra("title"));
        instructorNameEdit.setText(getIntent().getStringExtra("instructor"));
        instructorEmailEdit.setText(getIntent().getStringExtra("email"));
        instructorPhoneEdit.setText(getIntent().getStringExtra("phone"));
        notesEditText.setText(getIntent().getStringExtra("notes"));
        //Status functionality
        courseStatus = getIntent().getStringExtra("status");
        if (courseStatus == null) statusRadio.check(R.id.PlanToTakeButton);
        else if (courseStatus.equals("In Progress")) statusRadio.check(R.id.InProgressButton);
        else if (courseStatus.equals("Completed")) statusRadio.check(R.id.CompletedButton);
        else if (courseStatus.equals("Dropped")) statusRadio.check(R.id.DroppedButton);
        else statusRadio.check(R.id.PlanToTakeButton);


        //Add spinner
        spinner = findViewById(R.id.spinner);
        List<String> termName = new ArrayList<>();
        for (Term term : allTerms) {
            termName.add(term.getTitle());
        }
        ArrayAdapter<String> termArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, termName);
        termArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(termArrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedTerm = spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner.setSelection(getIndex(spinner, getTitle(termID)));


        courseStartEdit.setText(getIntent().getStringExtra("start"));
        courseStartEdit.setOnClickListener(view -> {
            String info = courseStartEdit.getText().toString();
            if (info.equals("")) info = "11/26/23";
            try {
                myCalStart.setTime(Objects.requireNonNull(sdf.parse(info)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            new DatePickerDialog(CourseDetails.this, courseStartDate, myCalStart.get(Calendar.YEAR), myCalStart.get(Calendar.MONTH), myCalStart.get(Calendar.DAY_OF_MONTH)).show();
        });

        courseStartDate = (datePicker, year, month, day) -> {
            myCalStart.set(Calendar.YEAR, year);
            myCalStart.set(Calendar.MONTH, month);
            myCalStart.set(Calendar.DAY_OF_MONTH, day);
            updateStart();

        };

        courseEndEdit.setText(getIntent().getStringExtra("end"));
        courseEndEdit.setOnClickListener(view -> {
            String info = courseEndEdit.getText().toString();
            if (info.equals("")) info = "11/26/23";
            try {
                myCalStart.setTime(Objects.requireNonNull(sdf.parse(info)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            new DatePickerDialog(CourseDetails.this, courseEndDate, myCalStart.get(Calendar.YEAR), myCalStart.get(Calendar.MONTH), myCalStart.get(Calendar.DAY_OF_MONTH)).show();
        });

        courseEndDate = (datePicker, year, month, day) -> {
            myCalStart.set(Calendar.YEAR, year);
            myCalStart.set(Calendar.MONTH, month);
            myCalStart.set(Calendar.DAY_OF_MONTH, day);
            updateEnd();

        };


        saveButton.setOnClickListener(v -> saveCourse());
    }

    //Methods & Functions
    public int getIndex(Spinner spinner, String termName) {
        for(int i = 0; i < spinner.getCount(); i++){
            if(spinner.getItemAtPosition(i).toString().equals(termName))
                return i;
        }
        return 0;
    }

    public String getTitle(int termID) {
        for(Term term : allTerms){
            if(term.getTermID() == termID){
                return term.getTitle();
            }
        }
        return null;
    }

    public int getTermID(String title) {
        for(Term term : allTerms) {
            if(term.getTitle().equals(title)) {
                return term.getTermID();
            }
        }
        return -1;
    }

    public void updateStart(){ courseStartEdit.setText(sdf.format(myCalStart.getTime()));}
    public void updateEnd(){ courseEndEdit.setText(sdf.format(myCalStart.getTime()));}
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_course_details, menu);
        return true;
    }


    //Notify & Sharing Functionality
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        if (item.getItemId() == R.id.action_notify) {
            String startDateFromScreen = courseStartEdit.getText().toString();
            try {
                Date startDate = sdf.parse(startDateFromScreen);
                String courseName = courseNameEdit.getText().toString();
                assert startDate != null;
                long startTrigger = startDate.getTime();
                Intent startIntent = new Intent(CourseDetails.this, MyReceiver.class);
                startIntent.putExtra("key", "Your course: " + courseName + " has started!");
                PendingIntent startPendingIntent = PendingIntent.getBroadcast(CourseDetails.this, MainActivity.numAlert++, startIntent, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager startAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                startAlarmManager.setExact(AlarmManager.RTC_WAKEUP, startTrigger, startPendingIntent);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if (item.getItemId() == R.id.action_notifyend) {
            String endDateFromScreen = courseEndEdit.getText().toString();
            try {
                Date endDate = sdf.parse(endDateFromScreen);
                String courseName = courseNameEdit.getText().toString();
                assert endDate != null;
                long endTrigger = endDate.getTime();
                Intent endIntent = new Intent(CourseDetails.this, MyReceiver.class);
                endIntent.putExtra("key", "Your Course: " + courseName + " has ended!");
                PendingIntent endPendingIntent = PendingIntent.getBroadcast(CourseDetails.this, MainActivity.numAlert++, endIntent, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager endAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                endAlarmManager.setExact(AlarmManager.RTC_WAKEUP, endTrigger, endPendingIntent);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        if (item.getItemId() == R.id.action_share) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, notesEditText.getText().toString());
            sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Course Notes");
            sendIntent.setType("text/plain");
            sendIntent.setType("message/rfc822");
            Intent shareIntent = Intent.createChooser(sendIntent, null);
            startActivity(shareIntent);
            return true;
        }


        if (item.getItemId() == R.id.coursedelete) {
            if (courseID == -1) {
                Toast.makeText(CourseDetails.this, "Cannot delete a non-saved course", Toast.LENGTH_SHORT).show();
            }
            else {
                List<Course> courseToDelete = new ArrayList<>();
                for (Course courses1 : allCourses) {
                    if(courses1.getCourseID() == courseID) {
                        courseToDelete.add(courses1);
                        repository.delete(courseToDelete.get(0));
                        Toast.makeText(CourseDetails.this, "Course deleted", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }

        return super.onOptionsItemSelected(item);
    }
    //Save
    private void saveCourse() {
        String courseName = courseNameEdit.getText().toString().trim();
        String start = courseStartEdit.getText().toString().trim();
        String end = courseEndEdit.getText().toString().trim();
        String instructorName = instructorNameEdit.getText().toString().trim();
        String instructorEmail = instructorEmailEdit.getText().toString().trim();
        String instructorPhone = instructorPhoneEdit.getText().toString().trim();
        String note = notesEditText.getText().toString().trim();
        int selectedStatusRadioID = statusRadio.getCheckedRadioButtonId();
        RadioButton selectedRadioButton = findViewById(selectedStatusRadioID);
        String status = selectedRadioButton.getText().toString().trim();


        // Perform validation on the input fields
        if (TextUtils.isEmpty(courseName)) {
            courseNameEdit.setError("Please enter a course name");
            courseNameEdit.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(start)) {
            courseStartEdit.setError("Please enter a start date");
            courseStartEdit.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(end)) {
            courseEndEdit.setError("Please enter an end date");
            courseEndEdit.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(instructorName)) {
            instructorNameEdit.setError("Please enter an instructor name");
            instructorNameEdit.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(instructorEmail)) {
            instructorEmailEdit.setError("Please enter valid e-mail");
            instructorEmailEdit.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(instructorPhone)) {
            instructorPhoneEdit.setError("Please enter valid phone number");
            instructorPhoneEdit.requestFocus();
            return;
        }

        Repository repository = new Repository(getApplication());
        Course course;
        boolean nameCheck = false;
        if (selectedTerm == null) {
            Toast.makeText(CourseDetails.this, "Please add a term before adding course", Toast.LENGTH_SHORT).show();
        } else if (courseID == -1) {
            for (Course courses : allCourses) {
                if (courses.getTitle().equals(courseName)) {
                    nameCheck = true;
                    break;
                }
            }
            if (nameCheck)
                Toast.makeText(CourseDetails.this, "Course with this name already exists. Please use unique name", Toast.LENGTH_SHORT).show();
            else if (allCourses.size() == 0) {
                course = new Course(0, getTermID(selectedTerm), courseName, start, end, status, instructorName, instructorPhone, instructorEmail, note);
                repository.insert(course);
                Toast.makeText(CourseDetails.this, "Course saved", Toast.LENGTH_SHORT).show();
            } else {
                int newID = repository.getAllCourses().get(repository.getAllCourses().size() - 1).getCourseID() + 1;
                course = new Course(newID, getTermID(selectedTerm), courseName, start, end, status, instructorName, instructorPhone, instructorEmail, note);
                repository.insert(course);
                Toast.makeText(CourseDetails.this, "Course saved", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            for(Course courses : allCourses){
                if (courses.getTitle().equals(courseName) && courses.getCourseID() != courseID){
                    nameCheck = true;
                    break;
                }
            }
             if(nameCheck)Toast.makeText(CourseDetails.this, "Course with this name already exists. Please use unique name", Toast.LENGTH_SHORT).show();
             else {
                 course = new Course(courseID, getTermID(selectedTerm), courseName, start, end, status, instructorName, instructorPhone, instructorEmail, note);
                 repository.update(course);
                 Toast.makeText(CourseDetails.this, "Course updated", Toast.LENGTH_SHORT).show();
             }
        }
        finish();
    }
}
