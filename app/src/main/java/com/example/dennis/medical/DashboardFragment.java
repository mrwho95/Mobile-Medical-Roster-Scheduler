package com.example.dennis.medical;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment {

    private RadioGroup preferenceradio;
    private RadioButton shiftoptionradio;
    private Button preferenceBtn;

    private TextView Annualleave, MC, Leaveremain, Publicholiday;
    private TextView Morningshift, Afternoonshift, Nightshift;

    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth mAuth;

    String shiftpreference;

    public DashboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();

        firebaseDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();

        Morningshift = view.findViewById(R.id.txtmorningshift);
        Afternoonshift = view.findViewById(R.id.txtafternoonshift);
        Nightshift = view.findViewById(R.id.txtnightshift);

        DatabaseReference getmorningshift = firebaseDatabase.getReference().child("Work Shift").child(mAuth.getUid()).child("Morning Shift");
        getmorningshift.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String morningshift = dataSnapshot.getValue(String.class);
                Morningshift.setText(morningshift);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        DatabaseReference getafternoonshift = firebaseDatabase.getReference().child("Work Shift").child(mAuth.getUid()).child("Afternoon Shift");
        getafternoonshift.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String afternoonshift = dataSnapshot.getValue(String.class);
                Afternoonshift.setText(afternoonshift);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        DatabaseReference getnightshift = firebaseDatabase.getReference().child("Work Shift").child(mAuth.getUid()).child("Night Shift");
        getnightshift.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String nightshift = dataSnapshot.getValue(String.class);
                Nightshift.setText(nightshift);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


        preferenceradio = view.findViewById(R.id.PreferenceRadio);
        preferenceradio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                shiftoptionradio = preferenceradio.findViewById(checkedId);
                switch (checkedId){
                    case R.id.RadioMorningShift:
                        shiftpreference = shiftoptionradio.getText().toString().trim();
                        break;
                    case R.id.RadioAfternoonShift:
                         shiftpreference = shiftoptionradio.getText().toString().trim();
                         break;
                    case R.id.RadioNightShift:
                        shiftpreference = shiftoptionradio.getText().toString().trim();
                        break;

                    default:
                }
            }
        });

        preferenceBtn = view.findViewById(R.id.SubmitPreferenceBtn);
        preferenceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference databaseReference = firebaseDatabase.getReference().child("Shift").child("Shift Preference").child(mAuth.getUid());
                databaseReference.setValue(shiftpreference);
                Toast.makeText(getActivity(), "Submit Preference Successful!", Toast.LENGTH_LONG).show();
            }
        });

        Publicholiday = view.findViewById(R.id.txtpublicholiday);
        Annualleave = view.findViewById(R.id.txtannualleave);
        MC = view.findViewById(R.id.txtmc);
        Leaveremain = view.findViewById(R.id.txtleaveremain);

        DatabaseReference leavedisplay = firebaseDatabase.getReference().child("Medical Leave").child(mAuth.getUid());
        leavedisplay.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                MedicalLeave displayleave = dataSnapshot.getValue(MedicalLeave.class);
                Publicholiday.setText(displayleave.getPublic_Holiday());
                Annualleave.setText(displayleave.getAnnual_Leave());
                MC.setText(displayleave.getMedical_Certificate());
                Leaveremain.setText(displayleave.getTotal_Leave());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


        Button buttonapplyleave = (Button) view.findViewById(R.id.applyleave);
        buttonapplyleave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent applyleave = new Intent(getActivity(), Leave.class);
                startActivity(applyleave);
            }
        });

        return view;
    }

}
