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

public class HomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        RecyclerView rvRecentGrades = view.findViewById(R.id.rv_recent_grades);
        rvRecentGrades.setLayoutManager(new LinearLayoutManager(getContext()));

        // Mock data
        List<Grade> mockGrades = new ArrayList<>();
        mockGrades.add(new Grade("Mathematics", "A-"));
        mockGrades.add(new Grade("History", "B+"));
        mockGrades.add(new Grade("Science", "A"));

        GradeAdapter adapter = new GradeAdapter(mockGrades);
        rvRecentGrades.setAdapter(adapter);

        return view;
    }
}
