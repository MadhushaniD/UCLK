package com.uclk.shani.youseelk.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.uclk.shani.youseelk.R;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        EditText username = (EditText)findViewById(R.id.etSUEmail);
        EditText password = (EditText)findViewById(R.id.etSUPassword);
        EditText confirm = (EditText)findViewById(R.id.etSUConfirmPassword);
        Button next = (Button)findViewById(R.id.btnNextPref);
        Button signupFirst = (Button)findViewById(R.id.btnSignUpWithoutPref);
        Button hasAcc = (Button)findViewById(R.id.btnHasAccount);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1 = new Intent(getApplicationContext(),PreferencesActivity.class);
                startActivity(intent1);
            }
        });

        signupFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //code to save user details in DB
            }
        });

        hasAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent2 = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent2);

            }
        });

    }


}
