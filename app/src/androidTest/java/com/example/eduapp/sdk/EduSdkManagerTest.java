package com.example.eduapp.sdk;

import android.content.Context;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.eduapp.sdk.models.SdkGrade;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class EduSdkManagerTest {

    private EduSdkManager sdkManager;

    @Before
    public void setUp() {
        Context context = ApplicationProvider.getApplicationContext();
        sdkManager = new EduSdkManager(context);
        sdkManager.clearAllData(); // start fresh
    }

    @After
    public void tearDown() {
        sdkManager.clearAllData();
    }

    @Test
    public void testAddAndRetrieveGrades() {
        SdkGrade newGrade = new SdkGrade("Math", "Linear Algebra", 90.0);
        sdkManager.addGrade(newGrade);

        List<SdkGrade> grades = sdkManager.getAllGrades();
        assertEquals(1, grades.size());
        assertEquals("Math", grades.get(0).getSubject());
        assertEquals(90.0, grades.get(0).getScore(), 0.001);
    }

    @Test
    public void testUpdateGrade() {
        SdkGrade grade = new SdkGrade("History", "World War I", 80.0);
        sdkManager.addGrade(grade);
        
        // Grab grade with generated ID saved in memory
        List<SdkGrade> savedGrades = sdkManager.getAllGrades();
        String idToUpdate = savedGrades.get(0).getId();

        SdkGrade updatedGrade = new SdkGrade("History", "World War I Modified", 95.0);
        boolean result = sdkManager.updateGrade(idToUpdate, updatedGrade);

        assertTrue(result);
        
        List<SdkGrade> finalGrades = sdkManager.getAllGrades();
        assertEquals(1, finalGrades.size());
        assertEquals(95.0, finalGrades.get(0).getScore(), 0.001);
        assertEquals("World War I Modified", finalGrades.get(0).getDescription());
    }

    @Test
    public void testAverages() {
        sdkManager.addGrade(new SdkGrade("Math", "Algebra", 10.0));
        sdkManager.addGrade(new SdkGrade("Math", "Calculus", 8.0));
        sdkManager.addGrade(new SdkGrade("Science", "Physics", 6.0));

        // Math should be (10+8)/2 = 9
        assertEquals(9.0, sdkManager.getAverageBySubject("Math"), 0.001);
        
        // General should be (10+8+6)/3 = 8
        assertEquals(8.0, sdkManager.getGeneralAverage(), 0.001);
    }

    @Test
    public void testSubjects() {
        sdkManager.addSubject("Art");
        sdkManager.addSubject("Music");
        sdkManager.addSubject("Art"); // Duplicate

        List<String> subjects = sdkManager.getAllSubjects();
        assertEquals(2, subjects.size());
        assertTrue(subjects.contains("Art"));
        assertTrue(subjects.contains("Music"));
    }
}
