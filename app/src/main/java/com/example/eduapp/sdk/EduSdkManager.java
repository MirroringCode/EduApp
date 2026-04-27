package com.example.eduapp.sdk;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.eduapp.sdk.models.SdkGrade;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class EduSdkManager {

    private static final String PREF_NAME = "edu_sdk_prefs";
    private static final String KEY_GRADES = "grades_json";
    private static final String KEY_SUBJECTS = "subjects_json";

    private final SharedPreferences prefs;
    private final Gson gson;

    public EduSdkManager(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    /**
     * Retrieve all saved grades from local persistence.
     * @return List of SdkGrade
     */
    public List<SdkGrade> getAllGrades() {
        String json = prefs.getString(KEY_GRADES, null);
        if (json == null || json.isEmpty()) {
            return new ArrayList<>();
        }
        Type listType = new TypeToken<ArrayList<SdkGrade>>() {}.getType();
        return gson.fromJson(json, listType);
    }

    /**
     * Save the entire list of grades back to local persistence.
     */
    private void saveGrades(List<SdkGrade> grades) {
        String json = gson.toJson(grades);
        prefs.edit().putString(KEY_GRADES, json).apply();
    }

    /**
     * Add a new grade.
     */
    public void addGrade(SdkGrade grade) {
        List<SdkGrade> grades = getAllGrades();
        grades.add(grade);
        saveGrades(grades);
    }

    /**
     * Update an existing grade by its ID.
     * @return true if found and updated, false otherwise
     */
    public boolean updateGrade(String gradeId, SdkGrade updatedGrade) {
        List<SdkGrade> grades = getAllGrades();
        for (int i = 0; i < grades.size(); i++) {
            if (grades.get(i).getId().equals(gradeId)) {
                updatedGrade.setId(gradeId); // Preserve original ID
                grades.set(i, updatedGrade);
                saveGrades(grades);
                return true;
            }
        }
        return false;
    }

    /**
     * Calculate the general average across all subjects.
     */
    public double getGeneralAverage() {
        List<SdkGrade> grades = getAllGrades();
        if (grades.isEmpty()) return 0.0;

        double sum = 0;
        for (SdkGrade g : grades) {
            sum += g.getScore();
        }
        return sum / grades.size();
    }

    /**
     * Calculate the average score for a specific subject.
     */
    public double getAverageBySubject(String subject) {
        List<SdkGrade> grades = getAllGrades();
        double sum = 0;
        int count = 0;

        for (SdkGrade g : grades) {
            if (g.getSubject().equalsIgnoreCase(subject)) {
                sum += g.getScore();
                count++;
            }
        }
        return count == 0 ? 0.0 : (sum / count);
    }
    
    /**
     * Retrieve all saved subjects from local persistence.
     * @return List of String
     */
    public List<String> getAllSubjects() {
        String json = prefs.getString(KEY_SUBJECTS, null);
        if (json == null || json.isEmpty()) {
            return new ArrayList<>();
        }
        Type listType = new TypeToken<ArrayList<String>>() {}.getType();
        return gson.fromJson(json, listType);
    }

    /**
     * Save the entire list of subjects back to local persistence.
     */
    private void saveSubjects(List<String> subjects) {
        String json = gson.toJson(subjects);
        prefs.edit().putString(KEY_SUBJECTS, json).apply();
    }

    /**
     * Add a new subject.
     */
    public void addSubject(String subject) {
        List<String> subjects = getAllSubjects();
        if (!subjects.contains(subject)) {
            subjects.add(subject);
            saveSubjects(subjects);
        }
    }

    /**
     * Clears all data (useful for testing/resetting)
     */
    public void clearAllData() {
        prefs.edit().clear().apply();
    }
}
