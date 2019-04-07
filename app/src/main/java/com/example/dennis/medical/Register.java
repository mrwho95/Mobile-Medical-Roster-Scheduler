package com.example.dennis.medical;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Register extends AppCompatActivity implements View.OnClickListener{

    private EditText Email;
    private EditText Password;
    private EditText FullName, StaffID, Age, Handphone, HomeAddress, Position, Department, Hospital;
    String  fullname,email,password,staffID,age,handphone, homeaddress, clinicianposition, department, hospital;

    private FirebaseAuth mAuth;

    DatabaseReference databaseReference;




    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Register an Account");

        Email = (EditText)findViewById(R.id.loginEmail);
        Password = (EditText)findViewById(R.id.loginPassword);
        FullName = (EditText) findViewById(R.id.userName);
        StaffID = (EditText) findViewById(R.id.userStaffID);
        Age = (EditText) findViewById(R.id.userAge);
        Handphone = (EditText) findViewById(R.id.userHandphone);
        HomeAddress = (EditText) findViewById(R.id.userHomeAddress);
        Position = (EditText) findViewById(R.id.userPosition);
        Department = (EditText) findViewById(R.id.userDepartment);
        Hospital = (EditText) findViewById(R.id.userHospital);

        progressBar = (ProgressBar) findViewById(R.id.progressbar2);

        findViewById(R.id.updateBtn).setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");



    }

    private void Signup() {
        fullname = FullName.getText().toString().trim();
        staffID = StaffID.getText().toString().trim();
        email = Email.getText().toString().trim();
        password = Password.getText().toString().trim();
        age = Age.getText().toString().trim();
        handphone = Handphone.getText().toString().trim();
        homeaddress = HomeAddress.getText().toString().trim();
        clinicianposition = Position.getText().toString().trim();
        department = Department.getText().toString().trim();
        hospital = Hospital.getText().toString().trim();


        if (fullname.isEmpty()){
            FullName.setError("Full Name is required");
            FullName.requestFocus();
            return;
        }
        if (staffID.isEmpty()){
            StaffID.setError("Clinician ID is required");
            StaffID.requestFocus();
            return;
        }
        if (email.isEmpty()){
            Email.setError("Email is required");
            Email.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Email.setError("Please enter a valid email");
            Email.requestFocus();
            return;
        }
        if (password.isEmpty()){
            Password.setError("Password is required");
            Password.requestFocus();
            return;
        }
        if (department.isEmpty()){
            Department.setError("Department Name is required");
            Department.requestFocus();
            return;
        }
        if (age.isEmpty()){
            Age.setError("Age is required");
            Age.requestFocus();
            return;
        }
        if (handphone.isEmpty()){
            Handphone.setError("Handphone Number is required");
            Handphone.requestFocus();
            return;
        }
        if (homeaddress.isEmpty()){
            HomeAddress.setError("Home Address is required");
            HomeAddress.requestFocus();
            return;
        }
        if (clinicianposition.isEmpty()){
            Position.setError("Position is required");
            Position.requestFocus();
            return;
        }
        if (hospital.isEmpty()){
            Hospital.setError("Hospital Name is required");
            Hospital.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()){
                    //Intent intent = new Intent(Signin.this, MainActivity.class);
                    // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    //startActivity(intent);
                    if (!TextUtils.isEmpty(fullname))
                    senduserData();
                    Toast.makeText(getApplicationContext(), "Clinician Account Registered Successful", Toast.LENGTH_LONG).show();
                    finish();
                    startActivity(new Intent(Register.this, MainActivity.class));
                    //sendemailverification();

                }else {
                    if (task.getException() instanceof FirebaseAuthUserCollisionException){
                        Toast.makeText(getApplicationContext(), "You are already registered", Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    private void senduserData(){
        String user_id = mAuth.getUid();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference().child("Users").child(mAuth.getUid());
        UserProfile userProfile = new UserProfile(fullname,email,password, staffID, age, handphone, homeaddress,
                clinicianposition, department,hospital, user_id);
//        UserProfile userProfile = new UserProfile(fullname,email,password);
        myRef.setValue(userProfile);
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
//                FullName.setText(userProfile.getuserFullName());
//                StaffID.setText(userProfile.getuserStaffID());
//                Email.setText(userProfile.getuserEmail());
//                Age.setText(userProfile.getuserAge());
//                HomeAddress.setText(userProfile.getuserHomeAddress());
//                Handphone.setText(userProfile.getuserHandphone());
//                Hospital.setText(userProfile.getuserHospital());
//                Position.setText(userProfile.getuserPosition());
//                Password.setText(userProfile.getuserPassword());
//                Department.setText(userProfile.getuserDepartment());
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
    }


@Override
public void onClick(View view){
    switch (view.getId()){
        case R.id.updateBtn:
            //startActivity(new Intent(Register.this, Signin.class));
            Signup();
            break;

        }
    }
}