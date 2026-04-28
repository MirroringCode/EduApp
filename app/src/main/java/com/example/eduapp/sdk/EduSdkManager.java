package com.example.eduapp.sdk;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.eduapp.sdk.models.SdkGrade;

import java.util.ArrayList;
import java.util.List;

public class EduSdkManager {

    private final EduDbHelper dbHelper;

    public EduSdkManager(Context context) {
        dbHelper = new EduDbHelper(context);
    }

    /**
     * Retrieve all saved grades from local persistence.
     * @return List of SdkGrade
     */
    public List<SdkGrade> getAllGrades() {
        List<SdkGrade> grades = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(EduDbHelper.TABLE_GRADES, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(cursor.getColumnIndexOrThrow(EduDbHelper.COLUMN_ID));
                String studentName = cursor.getString(cursor.getColumnIndexOrThrow(EduDbHelper.COLUMN_STUDENT_NAME));
                String subject = cursor.getString(cursor.getColumnIndexOrThrow(EduDbHelper.COLUMN_SUBJECT));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(EduDbHelper.COLUMN_DESCRIPTION));
                double score = cursor.getDouble(cursor.getColumnIndexOrThrow(EduDbHelper.COLUMN_SCORE));

                SdkGrade grade = new SdkGrade(studentName, subject, description, score);
                grade.setId(id);
                grades.add(grade);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return grades;
    }

    /**
     * Add a new grade.
     */
    public void addGrade(SdkGrade grade) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(EduDbHelper.COLUMN_ID, grade.getId());
        values.put(EduDbHelper.COLUMN_STUDENT_NAME, grade.getStudentName());
        values.put(EduDbHelper.COLUMN_SUBJECT, grade.getSubject());
        values.put(EduDbHelper.COLUMN_DESCRIPTION, grade.getDescription());
        values.put(EduDbHelper.COLUMN_SCORE, grade.getScore());
        db.insert(EduDbHelper.TABLE_GRADES, null, values);
        db.close();
    }

    /**
     * Update an existing grade by its ID.
     * @return true if found and updated, false otherwise
     */
    public boolean updateGrade(String gradeId, SdkGrade updatedGrade) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(EduDbHelper.COLUMN_STUDENT_NAME, updatedGrade.getStudentName());
        values.put(EduDbHelper.COLUMN_SUBJECT, updatedGrade.getSubject());
        values.put(EduDbHelper.COLUMN_DESCRIPTION, updatedGrade.getDescription());
        values.put(EduDbHelper.COLUMN_SCORE, updatedGrade.getScore());
        
        int rows = db.update(EduDbHelper.TABLE_GRADES, values, EduDbHelper.COLUMN_ID + "=?", new String[]{gradeId});
        db.close();
        return rows > 0;
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
        List<String> subjects = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(EduDbHelper.TABLE_SUBJECTS, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndexOrThrow(EduDbHelper.COLUMN_SUBJECT_NAME));
                subjects.add(name);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return subjects;
    }

    /**
     * Add a new subject.
     */
    public void addSubject(String subject) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query(EduDbHelper.TABLE_SUBJECTS, null, EduDbHelper.COLUMN_SUBJECT_NAME + "=?", new String[]{subject}, null, null, null);
        if (cursor.getCount() == 0) {
            ContentValues values = new ContentValues();
            values.put(EduDbHelper.COLUMN_SUBJECT_NAME, subject);
            db.insert(EduDbHelper.TABLE_SUBJECTS, null, values);
        }
        cursor.close();
        db.close();
    }

    /**
     * Retrieve all saved students from local persistence.
     * @return List of String
     */
    public List<String> getAllStudents() {
        List<String> students = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(EduDbHelper.TABLE_STUDENTS, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndexOrThrow(EduDbHelper.COLUMN_STUDENT_TABLE_NAME));
                students.add(name);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return students;
    }

    /**
     * Add a new student.
     */
    public void addStudent(String studentName) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query(EduDbHelper.TABLE_STUDENTS, null, EduDbHelper.COLUMN_STUDENT_TABLE_NAME + "=?", new String[]{studentName}, null, null, null);
        if (cursor.getCount() == 0) {
            ContentValues values = new ContentValues();
            values.put(EduDbHelper.COLUMN_STUDENT_TABLE_NAME, studentName);
            db.insert(EduDbHelper.TABLE_STUDENTS, null, values);
        }
        cursor.close();
        db.close();
    }

    /**
     * Clears all data (useful for testing/resetting)
     */
    public void clearAllData() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(EduDbHelper.TABLE_GRADES, null, null);
        db.delete(EduDbHelper.TABLE_SUBJECTS, null, null);
        db.delete(EduDbHelper.TABLE_STUDENTS, null, null);
        db.close();
    }
}
