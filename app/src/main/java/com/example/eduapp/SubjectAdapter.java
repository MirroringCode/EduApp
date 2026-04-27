package com.example.eduapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.SubjectViewHolder> {

    private List<String> subjectsList;

    public SubjectAdapter(List<String> subjectsList) {
        this.subjectsList = subjectsList;
    }

    @NonNull
    @Override
    public SubjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_subject, parent, false);
        return new SubjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectViewHolder holder, int position) {
        holder.tvSubjectName.setText(subjectsList.get(position));
    }

    @Override
    public int getItemCount() {
        return subjectsList != null ? subjectsList.size() : 0;
    }

    static class SubjectViewHolder extends RecyclerView.ViewHolder {
        TextView tvSubjectName;

        public SubjectViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSubjectName = itemView.findViewById(R.id.tv_subject_name);
        }
    }
}
