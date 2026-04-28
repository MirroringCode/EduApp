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
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
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
        
        SharedPreferences prefs = requireContext().getSharedPreferences("Settings", Context.MODE_PRIVATE);
        String userRole = prefs.getString("User_Role", "Estudiante");
        
        if ("Estudiante".equals(userRole)) {
            fabAddGrade.setVisibility(View.GONE);
        } else {
            fabAddGrade.setOnClickListener(v -> checkAndShowDialog());
        }

        return view;
    }

    private void checkAndShowDialog() {
        List<String> students = sdkManager.getAllStudents();
        List<String> subjects = sdkManager.getAllSubjects();
        
        if (students.isEmpty() || subjects.isEmpty()) {
            Toast.makeText(requireContext(), "Debes crear materias y esperar a que los estudiantes se registren antes de asignar notas", Toast.LENGTH_LONG).show();
            return;
        }
        
        showAddGradeDialog(students, subjects);
    }

    private void showAddGradeDialog(List<String> students, List<String> subjects) {
        View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_grade, null);
        Spinner spinnerStudent = dialogView.findViewById(R.id.spinner_student);
        Spinner spinnerSubject = dialogView.findViewById(R.id.spinner_subject);
        
        ArrayAdapter<String> studentAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, students);
        studentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStudent.setAdapter(studentAdapter);

        ArrayAdapter<String> subjectAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, subjects);
        subjectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSubject.setAdapter(subjectAdapter);

        EditText etDescription = dialogView.findViewById(R.id.et_description);
        EditText etScore = dialogView.findViewById(R.id.et_score);

        new AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setPositiveButton("Guardar", (dialog, which) -> {
                String studentName = spinnerStudent.getSelectedItem().toString();
                String subject = spinnerSubject.getSelectedItem().toString();
                String desc = etDescription.getText().toString();
                String scoreStr = etScore.getText().toString();

                if (!studentName.isEmpty() && !subject.isEmpty() && !scoreStr.isEmpty()) {
                    double score = Double.parseDouble(scoreStr);
                    SdkGrade newGrade = new SdkGrade(studentName, subject, desc, score);
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
