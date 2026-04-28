package com.example.eduapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eduapp.sdk.EduSdkManager;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        EditText etName = findViewById(R.id.et_name);
        RadioGroup rgRole = findViewById(R.id.rg_role);
        Button btnStart = findViewById(R.id.btn_start);

        btnStart.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            if (name.isEmpty()) {
                Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show();
            } else {
                int selectedId = rgRole.getCheckedRadioButtonId();
                String role = (selectedId == R.id.rb_professor) ? "Profesor" : "Estudiante";
                saveUserData(name, role);
                
                if ("Estudiante".equals(role)) {
                    EduSdkManager sdkManager = new EduSdkManager(this);
                    sdkManager.addStudent(name);
                }
                
                startActivity(new Intent(this, MainActivity.class));
                finish();
            }
        });
    }

    private void saveUserData(String name, String role) {
        SharedPreferences prefs = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("User_Name", name);
        editor.putString("User_Role", role);
        editor.apply();
    }
}
