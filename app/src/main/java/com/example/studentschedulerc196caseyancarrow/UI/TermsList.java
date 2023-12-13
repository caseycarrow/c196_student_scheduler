package com.example.studentschedulerc196caseyancarrow.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.studentschedulerc196caseyancarrow.R;
import com.example.studentschedulerc196caseyancarrow.database.Repository;
import com.example.studentschedulerc196caseyancarrow.entities.Term;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;

public class TermsList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_list);
        RecyclerView recyclerView=findViewById(R.id.termRecyclerView);
        Repository repository;
        repository=new Repository(getApplication());
        List<Term> terms;
        terms = repository.getAllTerms();
        TermAdapter termAdapter;
        termAdapter=new TermAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        termAdapter.setTerms(terms);
        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(view -> {
            Intent intent = new Intent(TermsList.this, TermDetails.class);
            startActivity(intent);

        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        Repository repository = new Repository(getApplication());
        RecyclerView recyclerViewOnResume = findViewById(R.id.termRecyclerView);
        final TermAdapter termAdapterOnResume = new TermAdapter(this);
        recyclerViewOnResume.setAdapter(termAdapterOnResume);
        recyclerViewOnResume.setLayoutManager(new LinearLayoutManager(this));
        List<Term> terms = repository.getAllTerms();
        termAdapterOnResume.setTerms(terms);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_terms_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int itemID = item.getItemId();
        if(itemID == android.R.id.home){
            this.finish();
            return true;
        } else if(itemID == R.id.refreshTerm){
            Repository repository = new Repository(getApplication());
            repository.getAllTerms();
            List<Term> terms;
            terms = repository.getAllTerms();
            TermAdapter termAdapter = new TermAdapter(this);
            RecyclerView recyclerView = findViewById(R.id.termRecyclerView);
            recyclerView.setAdapter(termAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            termAdapter.setTerms(terms);
            return true;
        } else{
            return super.onOptionsItemSelected(item);
        }
    }
}