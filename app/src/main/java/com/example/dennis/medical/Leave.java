package com.example.dennis.medical;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;

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
        DatabaseReference databaseReference;
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Medical Leave").child(mAuth.getUid()).child("Leave Remain");

        int takeonleaveday;
        int TotalLeave = 39;
        int AL = 20;
        int PH = 19;
        int MC = 0;
        int remain = 0;

//        takeonleaveday = Integer.parseInt(takePH);
        TotalLeave = TotalLeave - remain;
        String TotalLeaveAsString = Integer.toString(TotalLeave);

//        takeonleaveday = Integer.parseInt(takePH);
        PH = PH - remain;
        String Totalpublicholidayleave = Integer.toString(PH);

//        takeonleaveday = Integer.parseInt(takeAL);
        AL = AL - remain;
        String Totalannualleave = Integer.toString(AL);

        takeonleaveday = Integer.parseInt(takeMC);
        MC = MC + takeonleaveday;
        String TotalMC = Integer.toString(MC);

        HashMap<String, Object>hashMap = new HashMap<>();
        hashMap.put("Total_Leave", TotalLeaveAsString );
        hashMap.put("Public_Holiday", Totalpublicholidayleave);
        hashMap.put("Annual_Leave", Totalannualleave);
        hashMap.put("Medical_Certificate", TotalMC);
        databaseReference.updateChildren(hashMap);
    }
    private void LeavePH(String takePH){
        FirebaseAuth mAuth;
        DatabaseReference databaseReference;
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Medical Leave").child(mAuth.getUid()).child("Leave Remain");

        int takeonleaveday;
        int TotalLeave = 39;
        int AL = 20;
        int PH = 19;
        int MC = 0;
        int remain = 0;

        takeonleaveday = Integer.parseInt(takePH);
        TotalLeave = TotalLeave - takeonleaveday;
        String TotalLeaveAsString = Integer.toString(TotalLeave);

        takeonleaveday = Integer.parseInt(takePH);
        PH = PH - takeonleaveday;
        String Totalpublicholidayleave = Integer.toString(PH);

//        takeonleaveday = Integer.parseInt(takeAL);
        AL = AL - remain;
        String Totalannualleave = Integer.toString(AL);

//        takeonleaveday = Integer.parseInt(takeleave);
        MC = MC + remain;
        String TotalMC = Integer.toString(MC);

        HashMap<String, Object>hashMap = new HashMap<>();
        hashMap.put("Total_Leave", TotalLeaveAsString );
        hashMap.put("Public_Holiday", Totalpublicholidayleave);
        hashMap.put("Annual_Leave", Totalannualleave);
        hashMap.put("Medical_Certificate", TotalMC);
        databaseReference.updateChildren(hashMap);
    }

    private void LeaveAL(String takeAL){

        FirebaseAuth mAuth;
        DatabaseReference databaseReference;
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Medical Leave").child(mAuth.getUid()).child("Leave Remain");

        int takeonleaveday;
        int TotalLeave = 39;
        int AL = 20;
        int PH = 19;
        int MC = 0;
        int remain = 0;

        takeonleaveday = Integer.parseInt(takeAL);
        TotalLeave = TotalLeave - takeonleaveday;
        String TotalLeaveAsString = Integer.toString(TotalLeave);

//        takeonleaveday = Integer.parseInt(takeleave);
        PH = PH - remain;
        String Totalpublicholidayleave = Integer.toString(PH);

        takeonleaveday = Integer.parseInt(takeAL);
        AL = AL - takeonleaveday;
        String Totalannualleave = Integer.toString(AL);

//        takeonleaveday = Integer.parseInt(takeleave);
        MC = MC + remain;
        String TotalMC = Integer.toString(MC);

        HashMap<String, Object>hashMap = new HashMap<>();
        hashMap.put("Total_Leave", TotalLeaveAsString );
        hashMap.put("Public_Holiday", Totalpublicholidayleave);
        hashMap.put("Annual_Leave", Totalannualleave);
        hashMap.put("Medical_Certificate", TotalMC);
        databaseReference.updateChildren(hashMap);

    }
}
