package com.example.studentschedulerc196caseyancarrow.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.studentschedulerc196caseyancarrow.dao.AssessmentDAO;
import com.example.studentschedulerc196caseyancarrow.dao.CourseDAO;
import com.example.studentschedulerc196caseyancarrow.dao.TermDAO;
import com.example.studentschedulerc196caseyancarrow.entities.Assessment;
import com.example.studentschedulerc196caseyancarrow.entities.Course;
import com.example.studentschedulerc196caseyancarrow.entities.Term;

@Database(entities ={Term.class, Course.class, Assessment.class}, version=1, exportSchema = false)
public abstract class SchedulerDatabaseBuilder extends RoomDatabase {
    public abstract TermDAO termDAO();
    public abstract CourseDAO courseDAO();
    public abstract AssessmentDAO assessmentDAO();
    private static volatile SchedulerDatabaseBuilder INSTANCE;

    static SchedulerDatabaseBuilder getDatabase(final Context context){
        if (INSTANCE==null){
            synchronized (SchedulerDatabaseBuilder.class){
                if (INSTANCE==null){
                    INSTANCE= Room.databaseBuilder(context.getApplicationContext(),SchedulerDatabaseBuilder.class, "StudentSchedulerDatabase.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
