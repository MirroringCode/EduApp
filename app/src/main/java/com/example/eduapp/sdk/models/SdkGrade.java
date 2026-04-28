package com.example.eduapp.sdk.models;

import java.util.UUID;

public class SdkGrade {
    private String id;
    private String studentName;
    private String subject;
    private String description;
    private double score;

    public SdkGrade(String studentName, String subject, String description, double score) {
        this.id = UUID.randomUUID().toString();
        this.studentName = studentName;
        this.subject = subject;
        this.description = description;
        this.score = score;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
