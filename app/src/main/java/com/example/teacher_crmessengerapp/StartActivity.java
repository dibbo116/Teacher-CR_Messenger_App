package com.example.teacher_crmessengerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StartActivity extends AppCompatActivity implements View.OnClickListener {

    Button signinButton, tsignupButton, ssignupButton;
    FirebaseUser firebaseUser;

    @Override
    protected void onStart() {
        super.onStart();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if(firebaseUser != null){
            Intent intent = new Intent(StartActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        signinButton = findViewById(R.id.signinbuttonid);
        tsignupButton = findViewById(R.id.teachersignupid);
        ssignupButton = findViewById(R.id.studentsignupid);
        signinButton.setOnClickListener(this);
        tsignupButton.setOnClickListener(this);
        ssignupButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signinbuttonid:
                Intent intent = new Intent(StartActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.teachersignupid:
                Intent intent1 = new Intent(StartActivity.this, teachersignup.class);
                startActivity(intent1);
                finish();
                break;
            case R.id.studentsignupid:
                Intent intent2 = new Intent(StartActivity.this, RegisterActivity.class);
                startActivity(intent2);
                finish();
                break;
            default:
                break;
        }
    }
}