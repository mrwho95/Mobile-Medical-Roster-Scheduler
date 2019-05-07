package com.example.dennis.medical;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
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

import java.util.ArrayList;
import java.util.List;

public class Register extends AppCompatActivity implements View.OnClickListener{

    private EditText Email;
    private EditText Password;
    private EditText FullName, StaffID, Age, Handphone, HomeAddress, Position, Department, Hospital;
    String  fullname,email,password,staffID,age,handphone, homeaddress, clinicianposition, department, hospital;

    private FirebaseAuth mAuth;

    DatabaseReference databaseReference;

    Spinner HospitalSpinner, DepartmentSpinner, PositionSpinner;



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
//        Position = (EditText) findViewById(R.id.userPosition);
//        Department = (EditText) findViewById(R.id.userDepartment);
//        Hospital = (EditText) findViewById(R.id.userHospital);
        HospitalSpinner = (Spinner)findViewById(R.id.spinnerhospital);
        DepartmentSpinner = (Spinner)findViewById(R.id.spinnerdepartment);
        PositionSpinner = (Spinner)findViewById(R.id.spinnerposition);

        progressBar = (ProgressBar) findViewById(R.id.progressbar2);

        findViewById(R.id.updateBtn).setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        //Hospital list
        List<String> hospitallist = new ArrayList<>();
        hospitallist.add("Pusat Rawatan Warga UMS");
        hospitallist.add("Queen Elizabeth Hospital I");
        hospitallist.add("Queen Elizabeth Hospital II");
        hospitallist.add("Rafflesia Medical Centre");
        hospitallist.add("Hospital Wanita Dan Kanak-Kanak Sabah");
        hospitallist.add("KPJ Damai Specialist Hospital");
        hospitallist.add("Gleneagles Kota Kinabalu");
        hospitallist.add("Jesselton Medical Centre Kota Kinabalu");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,  hospitallist);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        HospitalSpinner.setAdapter(adapter);
        HospitalSpinner.getSelectedItem().toString();
        HospitalSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String itemvalue = parent.getItemAtPosition(position).toString();
                Toast.makeText(Register.this,  itemvalue + " is selected", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //department list
        List<String> departmentlist = new ArrayList<>();
        departmentlist.add("Critical Care");
        departmentlist.add("Accident and Emergency");
        departmentlist.add("Anaesthetics");
        departmentlist.add("Cardiology");
        departmentlist.add("General Surgery");
        departmentlist.add("Nurition and Dietetics");
        departmentlist.add("Occupational Therapy");
        departmentlist.add("Physiotherapy");
        departmentlist.add("Pharmacy");
        departmentlist.add("Urology");

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,  departmentlist);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        DepartmentSpinner.setAdapter(adapter2);
        DepartmentSpinner.getSelectedItem().toString();
        DepartmentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String itemvalue2 = parent.getItemAtPosition(position).toString();
                Toast.makeText(Register.this,  itemvalue2 + " is selected", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

            //position list
            List<String> positionlist = new ArrayList<>();
            positionlist.add("Nurse");
            positionlist.add("Doctor");

            ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,  positionlist);
            adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            PositionSpinner.setAdapter(adapter3);
            PositionSpinner.getSelectedItem().toString();
            PositionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String itemvalue3 = parent.getItemAtPosition(position).toString();
                    Toast.makeText(Register.this,  itemvalue3 + " is selected", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

    }

    private void Signup() {
        fullname = FullName.getText().toString().trim();
        staffID = StaffID.getText().toString().trim();
        email = Email.getText().toString().trim();
        password = Password.getText().toString().trim();
        age = Age.getText().toString().trim();
        handphone = Handphone.getText().toString().trim();
        homeaddress = HomeAddress.getText().toString().trim();
        clinicianposition = PositionSpinner.getSelectedItem().toString();
        department = DepartmentSpinner.getSelectedItem().toString();
        hospital = HospitalSpinner.getSelectedItem().toString();


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

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()){
                    //Intent intent = new Intent(Signin.this, MainActivity.class);
                    // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    //startActivity(intent);
                    if (!TextUtils.isEmpty(staffID) || !TextUtils.isEmpty(password))
//                        checkstaffID();
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

    private void  checkstaffID(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference checkstaffID = firebaseDatabase.getReference().child("Users").child(mAuth.getUid());
        checkstaffID.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()){
                    if (!data.getValue(UserProfile.class).getuserStaffID().equals(staffID)) {
                       //senduserData();
                    } else {
                        Toast.makeText(Register.this, "Staff ID already exists.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void senduserData(){
        String user_id = mAuth.getUid();
        Log.d("Hello", "senduserData: "+ user_id);



        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference().child("Users").child(mAuth.getUid());
        UserProfile userProfile = new UserProfile(fullname,email,password, staffID, age, handphone, homeaddress,
                clinicianposition, department,hospital, user_id);
//        UserProfile userProfile = new UserProfile(fullname,email,password);
        myRef.setValue(userProfile);
        DatabaseReference userstatusref = FirebaseDatabase.getInstance().getReference("Users/"+ user_id + "/userstatus");
        userstatusref.setValue("offline");
        DatabaseReference userimageurl = FirebaseDatabase.getInstance().getReference("Users/"+ user_id + "/userImageurl");
        userimageurl.setValue("defaults");

        DatabaseReference AL = FirebaseDatabase.getInstance().getReference("Medical Leave/" + user_id + "/Annual_Leave");
        AL.setValue("20");
        DatabaseReference PH = FirebaseDatabase.getInstance().getReference("Medical Leave/" + user_id + "/Public_Holiday");
        PH.setValue("19");
        DatabaseReference TL = FirebaseDatabase.getInstance().getReference("Medical Leave/" + user_id + "/Total_Leave");
        TL.setValue("39");
        DatabaseReference MC = FirebaseDatabase.getInstance().getReference("Medical Leave/" + user_id + "/Medical_Certificate");
        MC.setValue("0");
        DatabaseReference Leavestartdate = FirebaseDatabase.getInstance().getReference("Medical Leave/" + user_id + "/leavestartdate");
        Leavestartdate.setValue("-");
        DatabaseReference Leaveenddate = FirebaseDatabase.getInstance().getReference("Medical Leave/" + user_id + "/leaveenddate");
        Leaveenddate.setValue("-");
        DatabaseReference Leavepending = FirebaseDatabase.getInstance().getReference("Medical Leave/" + user_id + "/leavestatus");
        Leavepending.setValue("No pending status");
        DatabaseReference Leavereason = FirebaseDatabase.getInstance().getReference("Medical Leave/" + user_id + "/leavereason");
        Leavereason.setValue("-");
        DatabaseReference Leaveduration = FirebaseDatabase.getInstance().getReference("Medical Leave/" + user_id + "/leaveduration");
        Leaveduration.setValue("-");
        DatabaseReference clinician_cover = FirebaseDatabase.getInstance().getReference("Medical Leave/" + user_id + "/clinician_cover");
        clinician_cover.setValue("-");
        DatabaseReference Leavetype = FirebaseDatabase.getInstance().getReference("Medical Leave/" + user_id + "/leavetype");
        Leavetype.setValue("-");
        DatabaseReference MLName = FirebaseDatabase.getInstance().getReference("Medical Leave/" + user_id + "/Name");
        MLName.setValue(fullname);
        DatabaseReference shift_preference = FirebaseDatabase.getInstance().getReference("Shift/Shift Preference/" + user_id);
        shift_preference.setValue("No status");

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