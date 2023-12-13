package com.example.studentschedulerc196caseyancarrow.UI;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.studentschedulerc196caseyancarrow.R;
import com.example.studentschedulerc196caseyancarrow.entities.Term;

import java.util.List;

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.TermViewHolder> {

    private List<Term> mTerms;
    private final Context context;
    private final LayoutInflater mInflater;
    public TermAdapter(Context context){
        mInflater= LayoutInflater.from(context);
        this.context=context;
    }
    class TermViewHolder extends RecyclerView.ViewHolder {
        private final TextView termItemView;
        private TermViewHolder(@NonNull View itemView) {
            super(itemView);
            termItemView = itemView.findViewById(R.id.termNameListItem);
            itemView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                final Term current=mTerms.get(position);
                Intent intent=new Intent(context,TermDetails.class);
                intent.putExtra("id",current.getTermID());
                intent.putExtra("title", current.getTitle());
                intent.putExtra("start", current.getStart());
                intent.putExtra("end", current.getEnd());
                context.startActivity(intent);

            });
        }
    }

    @NonNull
    @Override
    public TermAdapter.TermViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView=mInflater.inflate(R.layout.term_list_item,parent, false);
        return new TermViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull TermAdapter.TermViewHolder holder, int position) {
        if(mTerms !=null){
            Term current = mTerms.get(position);
            String title = current.getTitle();
            String start = current.getStart();
            String end = current.getEnd();
            holder.termItemView.setText(title + "\nStart Date: " + start + "\nEnd Date: " + end);
        }
        else{
            holder.termItemView.setText("No Term Title");
        }
    }

    @Override
    public int getItemCount() {
        if (mTerms != null) {
            return mTerms.size();
        } else return 0;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setTerms(List<Term> terms) {
        mTerms = terms;
        notifyDataSetChanged();
    }
}
