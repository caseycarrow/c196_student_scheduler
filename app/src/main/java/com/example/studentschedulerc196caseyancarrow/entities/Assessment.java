package com.example.studentschedulerc196caseyancarrow.entities;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Assessments")
public class Assessment {
    @PrimaryKey(autoGenerate = true)
    private int assessmentID;
    private int courseID;
    private String title;
    private String type;
    private String start;
    private String end;

    public Assessment(int assessmentID, int courseID, String title, String type, String start, String end) {
        this.assessmentID = assessmentID;
        this.courseID = courseID;
        this.title = title;
        this.type = type;
        this.start = start;
        this.end = end;
    }




    public int getAssessmentID() {
        return assessmentID;
    }

    public void setAssessmentID(int assessmentID) {
        this.assessmentID = assessmentID;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    @NonNull
    @Override
    public String toString() {
        return "Assessments{" +
                "assessmentID=" + assessmentID +
                ", title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", start='" + start + '\'' +
                ", end='" + end + '\'' +
                ", courseID=" + courseID +
                '}';
    }


}
