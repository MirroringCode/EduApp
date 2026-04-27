package com.example.eduapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.AlertDialog;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.eduapp.sdk.EduSdkManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
    private EduSdkManager sdkManager;
    private SubjectAdapter adapter;
    private List<String> subjectsList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        RecyclerView rvSubjects = view.findViewById(R.id.rv_subjects_taught);
        rvSubjects.setLayoutManager(new LinearLayoutManager(getContext()));

        TextView tvProfileName = view.findViewById(R.id.tv_profile_name);
        SharedPreferences prefs = requireContext().getSharedPreferences("Settings", Context.MODE_PRIVATE);
        String userName = prefs.getString("User_Name", "User");
        tvProfileName.setText(getString(R.string.welcome_user, userName));

        sdkManager = new EduSdkManager(requireContext());
        subjectsList = sdkManager.getAllSubjects();

        // If list is empty, maybe add some defaults or just let it be empty
/*         if (subjectsList.isEmpty()) {
            sdkManager.addSubject("Mathematics");
            sdkManager.addSubject("History");
            sdkManager.addSubject("Science");
            subjectsList = sdkManager.getAllSubjects();
        } */

        adapter = new SubjectAdapter(subjectsList);
        rvSubjects.setAdapter(adapter);

        FloatingActionButton fabAddSubject = view.findViewById(R.id.fab_add_subject);
        fabAddSubject.setOnClickListener(v -> showAddSubjectDialog());

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
        
        SharedPreferences prefs = requireActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("My_Lang", lang);
        editor.apply();
        
        // Restart activity to apply language changes
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        requireActivity().finish();
    }

    private void showAddSubjectDialog() {
        View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_subject, null);
        EditText etSubjectName = dialogView.findViewById(R.id.et_subject_name);

        new AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setPositiveButton("Guardar", (dialog, which) -> {
                String subjectName = etSubjectName.getText().toString().trim();
                if (!subjectName.isEmpty()) {
                    sdkManager.addSubject(subjectName);
                    subjectsList.clear();
                    subjectsList.addAll(sdkManager.getAllSubjects());
                    adapter.notifyDataSetChanged();
                }
            })
            .setNegativeButton("Cancelar", null)
            .show();
    }
}
