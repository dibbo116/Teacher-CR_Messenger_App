package com.example.teacher_crmessengerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.TestLooperManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class teachersignup extends AppCompatActivity {


    private static final String TAG = teachersignup.class.getSimpleName();
    MaterialEditText username,email,phnNUmber,password;
    Button registration;
    Spinner spinner;
    designation designation1;
    FirebaseAuth auth;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teachersignup);
        username = findViewById(R.id.tusernameid);
        email = findViewById(R.id.temailaddressid);
        phnNUmber = findViewById(R.id.tphonenumberid);
        password = findViewById(R.id.tpasswordid);
        spinner = findViewById(R.id.designationid);
        registration = findViewById(R.id.tregisterbuttonid);
        designation1 = new designation();

        final List<String> designation = new ArrayList<>();
        designation.add("Professor");
        designation.add("Assistant Professor");
        designation.add("Associate Professor");
        designation.add("Lecturer");

        final ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,designation);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Teacher Sign Up");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),StartActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });
        auth =FirebaseAuth.getInstance();


        spinner.setAdapter(adapter);
         registration.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 String txt_username = username.getText().toString();
                 String txt_email = email.getText().toString();
                 designation1.setSpinner(spinner.getSelectedItem().toString());
                 String designation11 = designation1.getSpinner().toString();
                 String txt_phnNumber = phnNUmber.getText().toString();
                 String txt_password = password.getText().toString();



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
                 else if(TextUtils.isEmpty(txt_phnNumber)) {
                     phnNUmber.setError("Enter Phone Number");
                     phnNUmber.requestFocus();
                     return;
                 }
                 else if(TextUtils.isEmpty(txt_password)){
                     password.setError("Enter Password");
                     password.requestFocus();
                     return;
                 }
                 else if(txt_password.length() < 6){
                     password.setError("password must be 6 characters");
                     password.requestFocus();
                 }
                 else{
                     register(txt_username,txt_email,designation11,txt_phnNumber,txt_password);
                 }
             }
         });


    }
    private void register(final String username, String email, final String desig, final String phnNumber, String password){
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    FirebaseUser firebaseUser = auth.getCurrentUser();
                    assert firebaseUser !=null;
                    String userid = firebaseUser.getUid();

                    reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);
                    HashMap<String,String> hashMap = new HashMap<>();
                    hashMap.put("id",userid);
                    hashMap.put("username",username);
                    hashMap.put("imageURL", "default");
                    hashMap.put("designation",desig);
                    hashMap.put("phnNumber",phnNumber);
                    hashMap.put("status","offline");
                    hashMap.put("search",username.toLowerCase());

                    reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Log.d(TAG, "createUserWithEmail:success");
                                Toast.makeText(teachersignup.this,"Account Created Succesfully!",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(teachersignup.this,MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });


                }
                else {
                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                    Toast.makeText(teachersignup.this,"You can't register with this email or password",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
