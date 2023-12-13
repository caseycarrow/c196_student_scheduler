package com.example.studentschedulerc196caseyancarrow.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Courses")
public class Course {
    @PrimaryKey(autoGenerate = true)

    private int courseID;

    private int termID;
    private String title;
    private String start;
    private String end;
    private String status;
    private String instructor;
    private String phone;
    private String email;

    private String notes;

    public Course(int courseID, int termID, String title, String start, String end, String status, String instructor, String phone, String email, String notes) {
        this.courseID = courseID;
        this.termID = termID;
        this.title = title;
        this.start = start;
        this.end = end;
        this.status = status;
        this.instructor = instructor;
        this.phone = phone;
        this.email = email;
        this.notes = notes;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public int getTermID() {
        return termID;
    }

    public void setTermID(int termID) {
        this.termID = termID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @NonNull
    @Override
    public String toString() {
        return "Courses{" +
                "courseID=" + courseID +
                ", Title='" + title +
                "', Start='" + start +
                "', End='" + end +
                "', Status='" + status +
                "', Instructor Name='" + instructor +
                "', Instructor Email='" + email +
                "', Instructor Phone='" + phone +
                "', Notes='" + notes + '\'' +
                '}';
    }

}
