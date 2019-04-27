package com.example.dennis.medical;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class RosterFragment extends Fragment {


    public RosterFragment() {
        // Required empty public constructor
    }
    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    private TextView DRMON, DRTUE, DRWED, DRTHU, DRFRI, DRSAT, DRSUN, DTDate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_roster, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        DTDate = view.findViewById(R.id.txtweek);
        DRMON = view.findViewById(R.id.txtMON);
        DRTUE = view.findViewById(R.id.txtTUE);
        DRWED = view.findViewById(R.id.txtWED);
        DRTHU = view.findViewById(R.id.txtTHU);
        DRFRI = view.findViewById(R.id.txtFRI);
        DRSAT = view.findViewById(R.id.txtSAT);
        DRSUN = view.findViewById(R.id.txtSUN);

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = firebaseDatabase.getInstance();

        DatabaseReference databaseReference = firebaseDatabase.getReference().child("official_duty_roster").child(mAuth.getUid()).child("date");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String timetable = dataSnapshot.getValue(String.class);
                DTDate.setText(timetable);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        DatabaseReference databaseReference1 = firebaseDatabase.getReference().child("official_duty_roster").child(mAuth.getUid()).child("MON");
        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String timetable = dataSnapshot.getValue(String.class);
                DRMON.setText(timetable);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        DatabaseReference databaseReference2 = firebaseDatabase.getReference().child("official_duty_roster").child(mAuth.getUid()).child("TUE");
        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String timetable = dataSnapshot.getValue(String.class);
                DRTUE.setText(timetable);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        DatabaseReference databaseReference3 = firebaseDatabase.getReference().child("official_duty_roster").child(mAuth.getUid()).child("WED");
        databaseReference3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String timetable = dataSnapshot.getValue(String.class);
                DRWED.setText(timetable);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        DatabaseReference databaseReference4 = firebaseDatabase.getReference().child("official_duty_roster").child(mAuth.getUid()).child("THU");
        databaseReference4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String timetable = dataSnapshot.getValue(String.class);
                DRTHU.setText(timetable);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        DatabaseReference databaseReference5 = firebaseDatabase.getReference().child("official_duty_roster").child(mAuth.getUid()).child("FRI");
        databaseReference5.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String timetable = dataSnapshot.getValue(String.class);
                DRFRI.setText(timetable);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        DatabaseReference databaseReference6 = firebaseDatabase.getReference().child("official_duty_roster").child(mAuth.getUid()).child("SAT");
        databaseReference6.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String timetable = dataSnapshot.getValue(String.class);
                DRSAT.setText(timetable);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        DatabaseReference databaseReference7 = firebaseDatabase.getReference().child("official_duty_roster").child(mAuth.getUid()).child("SUN");
        databaseReference7.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String timetable = dataSnapshot.getValue(String.class);
                DRSUN.setText(timetable);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        return view;
    }

}
