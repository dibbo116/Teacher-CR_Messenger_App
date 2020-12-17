package com.example.teacher_crmessengerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;



import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    Spinner series,section;
    MaterialEditText username,email,roll,phonenumber,password;
    Button sregisterbutton;
    FirebaseAuth auth;
    DatabaseReference reference;
    Student_series series1;
    Student_section section1;
    private static final String TAG = RegisterActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        series = findViewById(R.id.seriesid);
        section = findViewById(R.id.sectionid);

        ArrayAdapter<String> myadapter = new ArrayAdapter<String>(RegisterActivity.this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.series));
        myadapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        series.setAdapter(myadapter);
        ArrayAdapter<String> myadapter1 = new ArrayAdapter<String>(RegisterActivity.this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.section));
        myadapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        section.setAdapter(myadapter1);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Student Sign Up");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),StartActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });


        username = findViewById(R.id.usernameid);
        email = findViewById(R.id.semailaddressid);
        roll = findViewById(R.id.rollnumberid);
        phonenumber = findViewById(R.id.phonenumberid);
        password = findViewById(R.id.spasswordid);
        sregisterbutton = findViewById(R.id.sregisterbuttonid);
        series1 = new Student_series();
        section1 = new Student_section();

        auth =FirebaseAuth.getInstance();
        sregisterbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_username = username.getText().toString();
                String txt_email = email.getText().toString();
                String txt_roll = roll.getText().toString();
                series1.setSpinner(series.getSelectedItem().toString());
                String series11 = series1.getSpinner().toString();
                section1.setSpinner(section.getSelectedItem().toString());
                String section11 = section1.getSpinner().toString();
                String txt_password = password.getText().toString();
                String txt_phnumber = phonenumber.getText().toString();

                if(TextUtils.isEmpty(txt_username)){
                    username.setError("Enter Username");
                    username.requestFocus();
                    return;
                }
                else if(TextUtils.isEmpty(txt_email)){
                    email.setError("Enter Email Address");
                    email.requestFocus();
                    return;

                }
                else if(TextUtils.isEmpty(txt_phnumber)) {
                    phonenumber.setError("Enter Phone Number");
                    phonenumber.requestFocus();
                    return;
                }
                else if(TextUtils.isEmpty(txt_roll)){
                    roll.setError("Enter Roll Number");
                    roll.requestFocus();
                    return;
                }
                else if(TextUtils.isEmpty(txt_password)){
                    password.setError("Enter Password");
                    password.requestFocus();
                    return;
                }
                else if(series1==null){
                    Toast.makeText(RegisterActivity.this,"Enter series",Toast.LENGTH_SHORT).show();
                }
                else if(section1==null){
                    Toast.makeText(RegisterActivity.this,"Enter series",Toast.LENGTH_SHORT).show();
                }
                else if(txt_password.length() < 6){
                    password.setError("password must be 6 characters");
                    password.requestFocus();
                }
                else{
                    register(txt_username,txt_email,txt_roll,series11,section11,txt_phnumber,txt_password);
                }
            }
        });
    }
    private void register(final String username,  String email, final String roll, final String series, final String section, final String phoneNumber, String password){
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.d(TAG, "signInWithEmail:success");
                    FirebaseUser firebaseUser = auth.getCurrentUser();
                    assert firebaseUser != null;
                    String userid = firebaseUser.getUid();

                    reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);

                    HashMap<String,String> hashMap = new HashMap<>();
                    hashMap.put("id",userid);
                    hashMap.put("username",username);
                    hashMap.put("imageURL", "default");
                    hashMap.put("roll",roll);
                    hashMap.put("series",series);
                    hashMap.put("section",section);
                    hashMap.put("phnNumber",phoneNumber);
                    hashMap.put("status","offline");
                    hashMap.put("search",username.toLowerCase()+series+section);

                    reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(RegisterActivity.this,"Account Created Successfully!",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });

                }
                else {
                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                    Toast.makeText(RegisterActivity.this,"You can't register with this email or password",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}