package com.example.dennis.medical;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Leave extends AppCompatActivity {
    private TextView startdate;
    private TextView enddate;
    private EditText txtreason, txtcliniciancover;
    private RadioGroup leaveradio;
    private RadioButton leaveoption;
    private Button leaveSubmitBtn;
    private TextView leaveduration;

    String medicalleave, TxTreason, TxTcliniciancover, StartDate, EndDate, onleaveduration;

    Calendar c;
    DatePickerDialog dpd1,dpd2;

    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Leave Application");

        firebaseDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        startdate = (TextView)findViewById(R.id.txtstartdate);
        enddate = (TextView)findViewById(R.id.txtenddate);

        startdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                final int year = c.get(Calendar.YEAR);
                final int month = c.get(Calendar.MONTH);
                final int day = c.get(Calendar.DAY_OF_MONTH);
                dpd1 = new DatePickerDialog(Leave.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        startdate.setText(dayOfMonth+ "/" +(month+1)+ "/" + year);
                    }
                },year, month, day);
                dpd1.show();
            }
        });
        enddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                final int year = c.get(Calendar.YEAR);
                final int month = c.get(Calendar.MONTH);
                final int day = c.get(Calendar.DAY_OF_MONTH);
                dpd2 = new DatePickerDialog(Leave.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        enddate.setText(dayOfMonth+ "/" +(month+1)+ "/" + year);
                    }
                },year, month, day);
                dpd2.show();
            }
        });

        leaveduration = (TextView)findViewById(R.id.txtduration);
        leaveduration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NumberPicker numberPicker = new NumberPicker(Leave.this);
                numberPicker.setMinValue(0);
                numberPicker.setMaxValue(20);
                NumberPicker.OnValueChangeListener onValueChangeListener = new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        leaveduration.setText(""+newVal);
                    }
                };
                numberPicker.setOnValueChangedListener(onValueChangeListener);
                AlertDialog.Builder builder = new AlertDialog.Builder(Leave.this).setView(numberPicker);
                builder.setTitle("Select The Number of On Leave");
                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        });

        final int TotalLeave = 39;
        final int MC = 0;
        final int AL = 20;
        final int PH = 19;


        txtcliniciancover = (EditText)findViewById(R.id.txtcliniciancoverxml);
        txtreason = (EditText)findViewById(R.id.txtreasonxml);
        leaveradio = (RadioGroup)findViewById(R.id.LeaveRadio);
        leaveradio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                leaveoption = leaveradio.findViewById(checkedId);
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference leaveRef = firebaseDatabase.getReference().child("Medical Leave").child(mAuth.getUid());
                switch (checkedId){
                    case R.id.RadioAL:
                        TxTreason = txtreason.getText().toString().trim();
                        StartDate = startdate.getText().toString().trim();
                        EndDate = enddate.getText().toString().trim();
                        TxTcliniciancover = txtcliniciancover.getText().toString().trim();
                        medicalleave = leaveoption.getText().toString().trim();
                        onleaveduration = leaveduration.getText().toString().trim();
                        HashMap<String, Object>hashMap = new HashMap<>();
                        hashMap.put("clinician_cover", TxTcliniciancover );
                        hashMap.put("leaveduration", onleaveduration);
                        hashMap.put("leavereason", TxTreason);
                        hashMap.put("leaveenddate", EndDate);
                        hashMap.put("leavetype", medicalleave);
                        hashMap.put("leavestartdate", StartDate);
                        leaveRef.updateChildren(hashMap);
                        LeaveAL(onleaveduration);
                        Toast.makeText(Leave.this, "Submit Leave Application Successful!", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.RadioMC:
                        TxTreason = txtreason.getText().toString().trim();
                        StartDate = startdate.getText().toString().trim();
                        EndDate = enddate.getText().toString().trim();
                        TxTcliniciancover = txtcliniciancover.getText().toString().trim();
                        medicalleave = leaveoption.getText().toString().trim();
                        onleaveduration = leaveduration.getText().toString().trim();
                        HashMap<String, Object>hashMap2 = new HashMap<>();
                        hashMap2.put("clinician_cover", TxTcliniciancover );
                        hashMap2.put("leaveduration", onleaveduration);
                        hashMap2.put("leavereason", TxTreason);
                        hashMap2.put("leaveenddate", EndDate);
                        hashMap2.put("leavetype", medicalleave);
                        hashMap2.put("leavestartdate", StartDate);
                        leaveRef.updateChildren(hashMap2);
                        LeaveMC(onleaveduration);
                        Toast.makeText(Leave.this, "Submit Leave Application Successful!", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.RadioPH:
                        TxTreason = txtreason.getText().toString().trim();
                        StartDate = startdate.getText().toString().trim();
                        EndDate = enddate.getText().toString().trim();
                        TxTcliniciancover = txtcliniciancover.getText().toString().trim();
                        medicalleave = leaveoption.getText().toString().trim();
                        onleaveduration = leaveduration.getText().toString().trim();
                        HashMap<String, Object>hashMap3 = new HashMap<>();
                        hashMap3.put("clinician_cover", TxTcliniciancover );
                        hashMap3.put("leaveduration", onleaveduration);
                        hashMap3.put("leavereason", TxTreason);
                        hashMap3.put("leaveenddate", EndDate);
                        hashMap3.put("leavetype", medicalleave);
                        hashMap3.put("leavestartdate", StartDate);
                        leaveRef.updateChildren(hashMap3);
                        LeavePH(onleaveduration);
                        Toast.makeText(Leave.this, "Submit Leave Application Successful!", Toast.LENGTH_LONG).show();
                        break;
                    default:
                }

            }
        });

        leaveSubmitBtn = (Button)findViewById(R.id.leavesubmitBtn);
        leaveSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TxTreason = txtreason.getText().toString().trim();
                StartDate = startdate.getText().toString().trim();
                EndDate = enddate.getText().toString().trim();
                TxTcliniciancover = txtcliniciancover.getText().toString().trim();
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference leaveRef = firebaseDatabase.getReference().child("Medical Leave").child(mAuth.getUid());
                HashMap<String, Object>hashMap = new HashMap<>();
                hashMap.put("clinician_cover", TxTcliniciancover );
                hashMap.put("leavereason", TxTreason);
                hashMap.put("leaveenddate", EndDate);
                hashMap.put("leavestartdate", StartDate);
                leaveRef.updateChildren(hashMap);
                Toast.makeText(Leave.this, "Submit Leave Application Successful!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private  void LeaveMC(String takeMC){
        FirebaseAuth mAuth;
        final DatabaseReference databaseReference;
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Medical Leave").child(mAuth.getUid());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String, String> data = (HashMap<String, String>) dataSnapshot.getValue();
                double MC = Double.parseDouble(data.get("Medical_Certificate"));
                double takeMC = Double.parseDouble(data.get("leaveduration"));
                double MedicalC;
                MedicalC = MC + takeMC;
                int MedicalCertificate = (int) MedicalC;
                String TotalMC = Integer.toString(MedicalCertificate);
                setTotalMC(TotalMC);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setTotalMC(String TotalMC){
        mAuth = FirebaseAuth.getInstance();
        DatabaseReference databaseReference;
        databaseReference = FirebaseDatabase.getInstance().getReference("Medical Leave").child(mAuth.getUid());
            HashMap<String, Object>hashMap = new HashMap<>();
            hashMap.put("Medical_Certificate", TotalMC);
            databaseReference.updateChildren(hashMap);
    }
    private void LeavePH(String takePH){
        FirebaseAuth mAuth;
        DatabaseReference databaseReference;
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Medical Leave").child(mAuth.getUid());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String, String> data = (HashMap<String, String>) dataSnapshot.getValue();
                double PH = Double.parseDouble(data.get("Public_Holiday"));
                double TT = Double.parseDouble(data.get("Total_Leave"));
                double takePH = Double.parseDouble(data.get("leaveduration"));
                double RemainPH, RemainTT;
                RemainTT = TT - takePH;
                RemainPH = PH - takePH;
                int totalTT = (int) RemainTT;
                int totalPH = (int) RemainPH;
                String TotalTT = Integer.toString(totalTT);
                String TotalPH = Integer.toString(totalPH);
                setTotalPH(TotalTT,TotalPH);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void setTotalPH(String TotalTT, String TotalPH){
        mAuth = FirebaseAuth.getInstance();
        DatabaseReference databaseReference;
        databaseReference = FirebaseDatabase.getInstance().getReference("Medical Leave").child(mAuth.getUid());
        HashMap<String, Object>hashMap = new HashMap<>();
        hashMap.put("Public_Holiday", TotalPH);
        hashMap.put("Total_Leave", TotalTT);
        databaseReference.updateChildren(hashMap);
    }

    private void LeaveAL(String takeAL){
        FirebaseAuth mAuth;
        DatabaseReference databaseReference;
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Medical Leave").child(mAuth.getUid());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String, String> data = (HashMap<String, String>) dataSnapshot.getValue();
                double AL = Double.parseDouble(data.get("Annual_Leave"));
                double TT = Double.parseDouble(data.get("Total_Leave"));
                double takeAL = Double.parseDouble(data.get("leaveduration"));
                double RemainAL, RemainTT;
                RemainTT = TT - takeAL;
                RemainAL = AL - takeAL;
                int totalTT = (int) RemainTT;
                int totalAL = (int) RemainAL;
                String TotalTT = Integer.toString(totalTT);
                String TotalAL = Integer.toString(totalAL);
                setTotalAL(TotalTT, TotalAL);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void setTotalAL(String TotalTT, String TotalAL){
        mAuth = FirebaseAuth.getInstance();
        DatabaseReference databaseReference;
        databaseReference = FirebaseDatabase.getInstance().getReference("Medical Leave").child(mAuth.getUid());
        HashMap<String, Object>hashMap = new HashMap<>();
        hashMap.put("Annual_Leave", TotalAL);
        hashMap.put("Total_Leave", TotalTT);
        databaseReference.updateChildren(hashMap);
    }
}
