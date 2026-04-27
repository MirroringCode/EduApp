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

import android.widget.TextView;
import com.example.eduapp.sdk.EduSdkManager;
import com.example.eduapp.sdk.models.SdkGrade;
import java.util.List;

public class HomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        RecyclerView rvRecentGrades = view.findViewById(R.id.rv_recent_grades);
        rvRecentGrades.setLayoutManager(new LinearLayoutManager(getContext()));

        TextView tvWelcome = view.findViewById(R.id.tv_welcome);
        android.content.SharedPreferences prefs = requireContext().getSharedPreferences("Settings", android.content.Context.MODE_PRIVATE);
        String userName = prefs.getString("User_Name", "User");
        tvWelcome.setText(getString(R.string.welcome_user, userName));

        EduSdkManager sdkManager = new EduSdkManager(requireContext());
        List<SdkGrade> recentGrades = sdkManager.getAllGrades();

        GradeAdapter adapter = new GradeAdapter(recentGrades);
        rvRecentGrades.setAdapter(adapter);

        return view;
    }
}
