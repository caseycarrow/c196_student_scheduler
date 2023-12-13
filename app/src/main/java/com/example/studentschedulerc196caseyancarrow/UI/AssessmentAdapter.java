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
import com.example.studentschedulerc196caseyancarrow.entities.Assessment;

import java.util.List;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentViewHolder>  {
    class AssessmentViewHolder extends RecyclerView.ViewHolder{
        private final TextView assessmentItemView;
        private AssessmentViewHolder(View itemView) {
            super(itemView);
            assessmentItemView=itemView.findViewById(R.id.assessmentNameTextView);
            itemView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                final Assessment current = mAssessments.get(position);
                Intent intent = new Intent(context, AssessmentDetails.class);
                intent.putExtra("assessmentID", current.getAssessmentID());
                intent.putExtra("courseID", current.getCourseID());
                intent.putExtra("name", current.getTitle());
                intent.putExtra("type", current.getType());
                intent.putExtra("start", current.getStart());
                intent.putExtra("end", current.getEnd());
                context.startActivity(intent);
            });
        }
    }

    private List<Assessment> mAssessments;
    private final Context context;
    private final LayoutInflater mInflater;
    public AssessmentAdapter(Context context) {
        mInflater=LayoutInflater.from(context);
        this.context=context;
    }
    @NonNull
    @Override
    public AssessmentAdapter.AssessmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.assessment_list_item, parent, false);
        return new AssessmentAdapter.AssessmentViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull AssessmentAdapter.AssessmentViewHolder holder, int position) {
        if (mAssessments != null) {
            Assessment current = mAssessments.get(position);
            String title = current.getTitle();
            holder.assessmentItemView.setText(title + " , Course ID:" + current.getCourseID());
        } else {
            holder.assessmentItemView.setText("No available assessments");
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    public void setAssessments(List<Assessment> assessments) {
        mAssessments=assessments;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        if(mAssessments != null) {
            return mAssessments.size();
        }
        else return 0;
    }
}