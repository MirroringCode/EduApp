package com.example.eduapp.sdk;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class EduDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "EduApp.db";
    private static final int DATABASE_VERSION = 2;

    // Tables
    public static final String TABLE_GRADES = "grades";
    public static final String TABLE_SUBJECTS = "subjects";
    public static final String TABLE_STUDENTS = "students";

    // Grades columns
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_STUDENT_NAME = "student_name";
    public static final String COLUMN_SUBJECT = "subject";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_SCORE = "score";

    // Subjects columns
    public static final String COLUMN_SUBJECT_ID = "id";
    public static final String COLUMN_SUBJECT_NAME = "name";

    // Students columns
    public static final String COLUMN_STUDENT_ID = "id";
    public static final String COLUMN_STUDENT_TABLE_NAME = "name";

    public EduDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createGradesTable = "CREATE TABLE " + TABLE_GRADES + " (" +
                COLUMN_ID + " TEXT PRIMARY KEY, " +
                COLUMN_STUDENT_NAME + " TEXT, " +
                COLUMN_SUBJECT + " TEXT, " +
                COLUMN_DESCRIPTION + " TEXT, " +
                COLUMN_SCORE + " REAL)";
        db.execSQL(createGradesTable);

        String createSubjectsTable = "CREATE TABLE " + TABLE_SUBJECTS + " (" +
                COLUMN_SUBJECT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_SUBJECT_NAME + " TEXT UNIQUE)";
        db.execSQL(createSubjectsTable);

        String createStudentsTable = "CREATE TABLE " + TABLE_STUDENTS + " (" +
                COLUMN_STUDENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_STUDENT_TABLE_NAME + " TEXT UNIQUE)";
        db.execSQL(createStudentsTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GRADES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUBJECTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS);
        onCreate(db);
    }
}
