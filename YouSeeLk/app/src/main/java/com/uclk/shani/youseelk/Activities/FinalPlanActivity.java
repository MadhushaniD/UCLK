package com.uclk.shani.youseelk.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.uclk.shani.youseelk.R;

public class FinalPlanActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_plan);

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        String[] dests = {"Kandy","Colombo","Kaduwela","Matale","Kegalle"};
        Double[] distances = {0.00,108.0,138.0,145.0,193.5};
        Double[] times = {0.00,3.5,3.95,11.10,13.5};

    }
}
