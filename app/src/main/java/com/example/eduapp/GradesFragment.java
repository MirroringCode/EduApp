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

import java.util.ArrayList;
import java.util.List;

public class GradesFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_grades, container, false);

        RecyclerView rvAllGrades = view.findViewById(R.id.rv_all_grades);
        rvAllGrades.setLayoutManager(new LinearLayoutManager(getContext()));

        // Mock data
        List<Grade> mockGrades = new ArrayList<>();
        mockGrades.add(new Grade("Mathematics - Algebra", "A-"));
        mockGrades.add(new Grade("Mathematics - Calculus", "B+"));
        mockGrades.add(new Grade("History - World War II", "A"));
        mockGrades.add(new Grade("History - Ancient Rome", "A-"));
        mockGrades.add(new Grade("Science - Physics", "B"));
        mockGrades.add(new Grade("Science - Chemistry", "B+"));

        GradeAdapter adapter = new GradeAdapter(mockGrades);
        rvAllGrades.setAdapter(adapter);

        return view;
    }
}
