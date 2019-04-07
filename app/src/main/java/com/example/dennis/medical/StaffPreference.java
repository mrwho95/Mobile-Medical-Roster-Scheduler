package com.example.dennis.medical;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class StaffPreference extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_preference);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Staff Preference");
    }
}
