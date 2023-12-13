package com.example.studentschedulerc196caseyancarrow.UI;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentschedulerc196caseyancarrow.R;
import com.example.studentschedulerc196caseyancarrow.entities.Course;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {


    class CourseViewHolder extends RecyclerView.ViewHolder {
        private final TextView courseItemView;
        private CourseViewHolder(View itemView){
            super(itemView);
            courseItemView=itemView.findViewById(R.id.courseListTextView);
            itemView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                final Course current= mCourses.get(position);
                Intent intent = new Intent(context,CourseDetails.class);
                intent.putExtra("courseID", current.getCourseID());
                intent.putExtra("termID", current.getTermID());
                intent.putExtra("title", current.getTitle());
                intent.putExtra("start", current.getStart());
                intent.putExtra("end", current.getEnd());
                intent.putExtra("status", current.getStatus());
                intent.putExtra("instructor", current.getInstructor());
                intent.putExtra("email", current.getEmail());
                intent.putExtra("phone", current.getPhone());
                intent.putExtra("notes", current.getNotes());
                context.startActivity(intent);

            });
        }

    }

    private List<Course> mCourses;
    private final Context context;
    private final LayoutInflater mInflater;
    public CourseAdapter(Context context) {
        mInflater=LayoutInflater.from(context);
        this.context=context;
    }

    @NonNull
    @Override
    public CourseAdapter.CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.course_list_item, parent, false);
        return new CourseAdapter.CourseViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.CourseViewHolder holder, int position) {
        if (mCourses != null) {
            Course current = mCourses.get(position);
            String title = current.getTitle();
            String start = current.getStart();
            String end = current.getEnd();
            String note = current.getNotes();
            String status = current.getStatus();
            holder.courseItemView.setText(title + "\nStart Date: " + start + "\nEnd Date: " + end + "\nStatus:" + status + "\nNotes: " + note);

        } else {
            holder.courseItemView.setText("No available courses");
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    public void setCourse(List<Course> course){
        mCourses = course;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount(){
        if(mCourses!=null) return mCourses.size();
        else return 0;
    }
}

