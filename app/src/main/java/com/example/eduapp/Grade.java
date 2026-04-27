package com.example.eduapp;

public class Grade {
    private String subject;
    private String score;

    public Grade(String subject, String score) {
        this.subject = subject;
        this.score = score;
    }

    public String getSubject() {
        return subject;
    }

    public String getScore() {
        return score;
    }
}
