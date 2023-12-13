package com.example.studentschedulerc196caseyancarrow.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.studentschedulerc196caseyancarrow.entities.Assessment;

import java.util.List;

@Dao
public interface AssessmentDAO {

    @Insert(onConflict= OnConflictStrategy.IGNORE)
    void insert (Assessment assessment);

    @Update
    void update (Assessment assessment);

    @Delete
    void delete (Assessment assessment);

   @Query("SELECT * FROM ASSESSMENTS ORDER BY courseID")
   List<Assessment> getAllAssessments();
}


