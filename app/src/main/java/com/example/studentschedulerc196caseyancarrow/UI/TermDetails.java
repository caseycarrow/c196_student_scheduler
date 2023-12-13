package com.example.studentschedulerc196caseyancarrow.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.studentschedulerc196caseyancarrow.R;
import com.example.studentschedulerc196caseyancarrow.database.Repository;
import com.example.studentschedulerc196caseyancarrow.entities.Course;
import com.example.studentschedulerc196caseyancarrow.entities.Term;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class TermDetails extends AppCompatActivity {

    Repository repository;
    private EditText editTitle;
    private EditText editStart;
    private EditText editEnd;
    String termTitle;
    private int termID;
    Button saveTerm;
    DatePickerDialog.OnDateSetListener termStartDate;
    DatePickerDialog.OnDateSetListener termEndDate;
    SimpleDateFormat simpleDate;
    final Calendar myCalStart = Calendar.getInstance();
    String formatDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);

        editTitle = findViewById(R.id.termNameListItem);
        editStart = findViewById(R.id.termStartTextEdit);
        editEnd = findViewById(R.id.termEndTextEdit);
        saveTerm = findViewById(R.id.saveButton);
        termTitle = getIntent().getStringExtra("title");
        editTitle.setText(termTitle);
        formatDate = "MM/dd/yy";
        simpleDate = new SimpleDateFormat(formatDate, Locale.US);
        termID = getIntent().getIntExtra("id", -1);
        editStart.setText(getIntent().getStringExtra("start"));
        editEnd.setText(getIntent().getStringExtra("end"));
        repository = new Repository(getApplication());
        editStart.setOnClickListener(view -> {
            String info = editStart.getText().toString();
            if (info.equals("")) info = "11/25/23";
            try {
                myCalStart.setTime(Objects.requireNonNull(simpleDate.parse(info)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            new DatePickerDialog(TermDetails.this, termStartDate, myCalStart.get(Calendar.YEAR), myCalStart.get(Calendar.MONTH), myCalStart.get(Calendar.DAY_OF_MONTH)).show();
        });

        editEnd.setOnClickListener(view -> {
            String info = editEnd.getText().toString();
            if (info.equals("")) info = "11/25/23";
            try {
                myCalStart.setTime(Objects.requireNonNull(simpleDate.parse(info)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            new DatePickerDialog(TermDetails.this, termEndDate, myCalStart.get(Calendar.YEAR), myCalStart.get(Calendar.MONTH), myCalStart.get(Calendar.DAY_OF_MONTH)).show();
        });

        termStartDate = (datePicker, year, month, day) -> {
            myCalStart.set(Calendar.YEAR, year);
            myCalStart.set(Calendar.MONTH, month);
            myCalStart.set(Calendar.DAY_OF_MONTH, day);
            updateStart();
        };

        termEndDate = (datePicker, year, month, day) -> {
            myCalStart.set(Calendar.YEAR, year);
            myCalStart.set(Calendar.MONTH, month);
            myCalStart.set(Calendar.DAY_OF_MONTH, day);
            updateEnd();
        };
        RecyclerView recyclerView = findViewById(R.id.courseRecyclerView);
        final CourseAdapter courseAdapter = new CourseAdapter(this);
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Course> filteredCourses = new ArrayList<>();
        for (Course course : repository.getAllCourses()) {
            if (course.getTermID() == termID) {
                filteredCourses.add(course);
            }
        }
        courseAdapter.setCourse(filteredCourses);
    }




    public void updateStart(){editStart.setText(simpleDate.format(myCalStart.getTime()));}
    public void updateEnd(){editEnd.setText(simpleDate.format(myCalStart.getTime()));}
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_term_details, menu);
        return true;
    }

    //Delete term
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemID = item.getItemId();
        if(itemID == android.R.id.home){
            this.finish();
            return true;
        } else if(itemID == R.id.deleteTermMenu){
            if(termID == -1){
                Toast.makeText(TermDetails.this,"Cannot delete a non-saved Term", Toast.LENGTH_SHORT).show();
            } else {
                List<Course> course = repository.getAllCourses();
                int count = 0;
                for(Course courses : course){
                    if(courses.getTermID() == termID){
                        count++;
                    }
                }
                if(count > 0){
                    Toast.makeText(TermDetails.this, "Cannot delete term with courses. This term has " + count + " saved courses. Please delete these courses to delete term.", Toast.LENGTH_SHORT).show();
                }
                else {
                    List<Term> term = repository.getAllTerms();
                    for(Term terms : term){
                        if(terms.getTermID() == termID){
                            repository.delete(terms);
                            Toast.makeText(TermDetails.this, "Term deleted", Toast.LENGTH_SHORT).show();
                            return true;
                        }
                    }
                }
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    //Save Term
    public void saveButton(View view){
        List<Term> allTerms = repository.getAllTerms();
        String title = editTitle.getText().toString();
        boolean titleCheck = false;
        if(termID == -1){
            for(Term term : allTerms){
                if (term.getTitle().equals(title)) {
                    titleCheck = true;
                    break;
                }
            }
            if(titleCheck) Toast.makeText(TermDetails.this, "Term name already exists. Please choose unique name.", Toast.LENGTH_SHORT).show();
            else if(allTerms.size() == 0){
                Term term = new Term(1, editTitle.getText().toString(), editStart.getText().toString(), editEnd.getText().toString());
                repository.insert(term);
                Toast.makeText(TermDetails.this, "Term saved", Toast.LENGTH_SHORT).show();
            }
            else {
                int newID = repository.getAllTerms().get(repository.getAllTerms().size() -1).getTermID() + 1;
                Term term = new Term(newID, editTitle.getText().toString(), editStart.getText().toString(), editEnd.getText().toString());
                repository.insert(term);
                Toast.makeText(TermDetails.this, "Term saved", Toast.LENGTH_SHORT).show();
            }
        }

        else{
            for(Term term: allTerms){
                if (term.getTitle().equals(title) && term.getTermID() != termID) {
                    titleCheck = true;
                    break;
                }
            }
            if(titleCheck) Toast.makeText(TermDetails.this, "Term name already exists. Please choose unique name.", Toast.LENGTH_SHORT).show();
            else {
                Term term = new Term(termID, editTitle.getText().toString(), editStart.getText().toString(), editEnd.getText().toString());
                Toast.makeText(TermDetails.this, "Term information updated", Toast.LENGTH_SHORT).show();
                repository.update(term);
            }
        }

finish();

        FloatingActionButton fab=findViewById(R.id.floatingActionButton2);
        fab.setOnClickListener(view1 -> {
            Intent intent=new Intent(TermDetails.this, TermAdapter.class);
            startActivity(intent);
        });
    }
}