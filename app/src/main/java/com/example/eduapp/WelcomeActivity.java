package com.example.eduapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        EditText etName = findViewById(R.id.et_name);
        Button btnStart = findViewById(R.id.btn_start);

        btnStart.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            if (name.isEmpty()) {
                Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show();
            } else {
                saveName(name);
                startActivity(new Intent(this, MainActivity.class));
                finish();
            }
        });
    }

    private void saveName(String name) {
        SharedPreferences prefs = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        prefs.edit().putString("User_Name", name).apply();
    }
}
