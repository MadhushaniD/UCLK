package com.uclk.shani.youseelk.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.uclk.shani.youseelk.adapters.DurationListAdapter;
import com.uclk.shani.youseelk.R;

public class DurationActivity extends AppCompatActivity {

    private String[] addedLocs = new String[]{"Kandy","Matale", "Kaduwela","Kalutara","Matara","Anuradhapura","Galle","Kegalle"};
    private String[] arrayTemp;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duration);

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);


        Button plan = (Button)findViewById(R.id.btnPlan);

        plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent2 = new Intent(getApplicationContext(),FinalPlanActivity.class);
                startActivity(intent2);
            }
        });

        arrayTemp = new String[addedLocs.length];

        DurationListAdapter adapter = new DurationListAdapter(addedLocs,arrayTemp,getApplicationContext());
        ListView lv = (ListView)findViewById(R.id.addedPlaces);
        lv.setAdapter(adapter);
    }
}
