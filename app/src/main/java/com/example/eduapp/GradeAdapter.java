package com.example.eduapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class GradeAdapter extends RecyclerView.Adapter<GradeAdapter.GradeViewHolder> {

    private List<Grade> gradesList;

    public GradeAdapter(List<Grade> gradesList) {
        this.gradesList = gradesList;
    }

    @NonNull
    @Override
    public GradeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grade, parent, false);
        return new GradeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GradeViewHolder holder, int position) {
        Grade grade = gradesList.get(position);
        holder.tvSubject.setText(grade.getSubject());
        holder.tvGrade.setText(grade.getScore());
    }

    @Override
    public int getItemCount() {
        return gradesList != null ? gradesList.size() : 0;
    }

    static class GradeViewHolder extends RecyclerView.ViewHolder {
        TextView tvSubject;
        TextView tvGrade;

        public GradeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSubject = itemView.findViewById(R.id.tv_subject);
            tvGrade = itemView.findViewById(R.id.tv_grade);
        }
    }
}
