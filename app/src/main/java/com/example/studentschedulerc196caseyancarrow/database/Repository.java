package com.example.studentschedulerc196caseyancarrow.database;

import android.app.Application;

import com.example.studentschedulerc196caseyancarrow.dao.AssessmentDAO;
import com.example.studentschedulerc196caseyancarrow.dao.CourseDAO;
import com.example.studentschedulerc196caseyancarrow.dao.TermDAO;
import com.example.studentschedulerc196caseyancarrow.entities.Assessment;
import com.example.studentschedulerc196caseyancarrow.entities.Course;
import com.example.studentschedulerc196caseyancarrow.entities.Term;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {
    private final TermDAO mTermDAO;
    private final CourseDAO mCourseDAO;
    private final AssessmentDAO mAssessmentDAO;

    private List<Term> mAllTerms;
    private List<Course> mAllCourses;
    private List<Assessment> mAllAssessments;

    private static final int NUMBER_OF_THREADS=4;
    static final ExecutorService databaseExecutor= Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repository(Application application){
        SchedulerDatabaseBuilder db=SchedulerDatabaseBuilder.getDatabase(application);
        mCourseDAO=db.courseDAO();
        mTermDAO=db.termDAO();
        mAssessmentDAO=db.assessmentDAO();
    }

    public List<Term> getAllTerms() {
        databaseExecutor.execute(() -> mAllTerms = mTermDAO.getAllTerms());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return mAllTerms;
    }

    public void insert(Term term){
        databaseExecutor.execute(()-> mTermDAO.insert(term));

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e){
            throw new RuntimeException(e);
        }
    }

    public void update(Term term) {
        databaseExecutor.execute(() -> mTermDAO.update(term));
        try{
            Thread.sleep(1000);
        } catch (InterruptedException e){
            throw new RuntimeException(e);
        }
    }


    public void delete(Term term){
        databaseExecutor.execute(() -> mTermDAO.delete(term));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e){
            throw new RuntimeException(e);
        }
    }

    public List<Course>getAllCourses(){
        databaseExecutor.execute(()-> mAllCourses=mCourseDAO.getAllCourses());

        try{
            Thread.sleep(1000);
        } catch (InterruptedException e ){
            throw new RuntimeException(e);
        }
        return mAllCourses;
    }


    public void insert(Course course){
        databaseExecutor.execute(() -> mCourseDAO.insert(course));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    public void update(Course course) {
        databaseExecutor.execute(() -> mCourseDAO.update(course));
        try{
            Thread.sleep(1000);
        } catch (InterruptedException e){
            throw new RuntimeException(e);
        }
    }


    public void delete(Course course){
        databaseExecutor.execute(() -> mCourseDAO.delete(course));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e){
            throw new RuntimeException(e);
        }
    }

    public List<Assessment>getAllAssessments(){
        databaseExecutor.execute(()-> mAllAssessments= mAssessmentDAO.getAllAssessments());

        try{
            Thread.sleep(1000);
        } catch (InterruptedException e ){
            throw new RuntimeException(e);
        }
        return mAllAssessments;
    }

    public void insert(Assessment assessment){
        databaseExecutor.execute(() -> mAssessmentDAO.insert(assessment));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    public void update(Assessment assessment) {
        databaseExecutor.execute(() -> mAssessmentDAO.update(assessment));
        try{
            Thread.sleep(1000);
        } catch (InterruptedException e){
            throw new RuntimeException(e);
        }
    }


    public void delete(Assessment assessment){
        databaseExecutor.execute(() -> mAssessmentDAO.delete(assessment));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e){
            throw new RuntimeException(e);
        }
    }
}
