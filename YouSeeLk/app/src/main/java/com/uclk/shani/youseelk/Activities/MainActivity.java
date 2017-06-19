package com.uclk.shani.youseelk.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.uclk.shani.youseelk.R;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        final Button login = (Button)findViewById(R.id.btnLogIn);
        Button signup = (Button)findViewById(R.id.btnSignUp);
        final EditText uname = (EditText) findViewById(R.id.etEmail);
        final EditText upw = (EditText) findViewById(R.id.etPassword);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                int remainingAttempts = 3;

                if(uname.getText().toString().equals("admin") && upw.getText().toString().equals("admin"))
                {
                    Toast.makeText(getApplicationContext(), "Redirecting...",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Invalid Credentials",Toast.LENGTH_SHORT).show();
                    remainingAttempts --;

                    if(remainingAttempts == 0){
                        login.setEnabled(false);
                    }
                }

            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent2 = new Intent(getApplicationContext(),SignUpActivity.class);
                startActivity(intent2);
            }
        });
    }
}
