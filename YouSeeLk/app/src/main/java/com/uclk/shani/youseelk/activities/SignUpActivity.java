package com.uclk.shani.youseelk.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.uclk.shani.youseelk.Objects.User;
import com.uclk.shani.youseelk.R;

public class SignUpActivity extends AppCompatActivity {

    DatabaseReference rootRef;
    DatabaseReference rootRefId;
    private EditText username;
    private EditText password;
    private EditText confirm;
    private Button next;
    private Button signUpFirst;
    private Button hasAcc;
    private FirebaseAuth mAuth;
    private  int rootId;
    private String s;
    //private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

       // rootId=0;

        mAuth = FirebaseAuth.getInstance();
        username = (EditText)findViewById(R.id.etSUEmail);
        password = (EditText)findViewById(R.id.etSUPassword);
        confirm = (EditText)findViewById(R.id.etSUConfirmPassword);
        next = (Button)findViewById(R.id.btnNextPref);
        signUpFirst = (Button)findViewById(R.id.btnSignUpWithoutPref);
        hasAcc = (Button)findViewById(R.id.btnHasAccount);
        rootRef = FirebaseDatabase.getInstance().getReference();
        rootRefId = FirebaseDatabase.getInstance().getReference("id");

        rootRefId.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Integer id = dataSnapshot.getValue(Integer.class);
                saveRootId(id);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(),PreferencesActivity.class);
                startActivity(intent1);
            }
        });

        signUpFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uName = username.getText().toString().trim();
                String pass = password.getText().toString().trim();
                String conf =  confirm.getText().toString().trim();

                if(comparePasswords(pass,conf)){
                    createUser(uName,pass);
                }else {
                    Toast.makeText(getApplicationContext(),"Passwords do not match!", Toast.LENGTH_SHORT).show();
                }

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

    boolean comparePasswords(String pass, String confirm){
        //check if the entered passwords are matching then return
        //simplified if else statement

        return pass.equals(confirm);
    }

    void createUser(String username, String password){
        //create a new user in the database under "user" child node

        User user = new User(username,password);

        rootRef.child("users").child(s).setValue(user);

        mAuth.createUserWithEmailAndPassword(username,password).
                addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(SignUpActivity.this,"New user authenticated!",
                                    Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(SignUpActivity.this,"New user authentication failed!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });



        rootRefId.setValue(rootId+1);
    }

    void saveRootId(int id){
        this.rootId = id;
        this.s = Integer.toString(rootId+1);
    }

}