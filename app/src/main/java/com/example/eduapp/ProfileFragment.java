package com.example.eduapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProfileFragment extends Fragment {

    private boolean isSpanish = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        RecyclerView rvSubjects = view.findViewById(R.id.rv_subjects_taught);
        rvSubjects.setLayoutManager(new LinearLayoutManager(getContext()));

        // Mock data
        List<String> mockSubjects = new ArrayList<>();
        mockSubjects.add("Mathematics");
        mockSubjects.add("History");
        mockSubjects.add("Science");

        SubjectAdapter adapter = new SubjectAdapter(mockSubjects);
        rvSubjects.setAdapter(adapter);

        Button btnChangeLanguage = view.findViewById(R.id.btn_change_language);
        
        // Detect current language to set toggle state
        String currentLang = getResources().getConfiguration().getLocales().get(0).getLanguage();
        isSpanish = currentLang.equals("es");
        if (isSpanish) {
            btnChangeLanguage.setText("Switch to English");
        } else {
            btnChangeLanguage.setText("Cambiar a Español");
        }

        btnChangeLanguage.setOnClickListener(v -> {
            String newLang = isSpanish ? "en" : "es";
            setLocale(newLang);
        });

        return view;
    }

    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        requireActivity().getResources().updateConfiguration(config, requireActivity().getResources().getDisplayMetrics());
        
        // Restart activity to apply language changes
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        requireActivity().finish();
    }
}
