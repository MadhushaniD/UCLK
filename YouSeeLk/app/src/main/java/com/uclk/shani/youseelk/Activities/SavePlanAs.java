package com.uclk.shani.youseelk.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.uclk.shani.youseelk.R;

public class SavePlanAs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.save_plan_as);
    }

    EditText pname = (EditText) findViewById(R.id.etPlanName);
    Button savePname = (Button) findViewById(R.id.btnSavePlanName);

    String planName = pname.getText().toString();

    //code to save the name in DB
}
