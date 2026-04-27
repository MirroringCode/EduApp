package com.example.eduapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.widget.EditText;
import com.example.eduapp.sdk.EduSdkManager;
import com.example.eduapp.sdk.models.SdkGrade;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;

public class GradesFragment extends Fragment {
    
    private EduSdkManager sdkManager;
    private GradeAdapter adapter;
    private List<SdkGrade> gradesList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_grades, container, false);

        RecyclerView rvAllGrades = view.findViewById(R.id.rv_all_grades);
        rvAllGrades.setLayoutManager(new LinearLayoutManager(getContext()));

        sdkManager = new EduSdkManager(requireContext());
        gradesList = sdkManager.getAllGrades();

        adapter = new GradeAdapter(gradesList);
        rvAllGrades.setAdapter(adapter);

        FloatingActionButton fabAddGrade = view.findViewById(R.id.fab_add_grade);
        fabAddGrade.setOnClickListener(v -> showAddGradeDialog());

        return view;
    }

    private void showAddGradeDialog() {
        View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_grade, null);
        EditText etSubject = dialogView.findViewById(R.id.et_subject);
        EditText etDescription = dialogView.findViewById(R.id.et_description);
        EditText etScore = dialogView.findViewById(R.id.et_score);

        new AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setPositiveButton("Guardar", (dialog, which) -> {
                String subject = etSubject.getText().toString();
                String desc = etDescription.getText().toString();
                String scoreStr = etScore.getText().toString();

                if (!subject.isEmpty() && !scoreStr.isEmpty()) {
                    double score = Double.parseDouble(scoreStr);
                    SdkGrade newGrade = new SdkGrade(subject, desc, score);
                    sdkManager.addGrade(newGrade);
                    
                    gradesList.clear();
                    gradesList.addAll(sdkManager.getAllGrades());
                    adapter.notifyDataSetChanged();
                }
            })
            .setNegativeButton("Cancelar", null)
            .show();
    }
}
