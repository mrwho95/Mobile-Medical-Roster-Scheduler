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

    private TextView Annualleave, MC, Leaveremain, Publicholiday,SelectedPreference, leavestatus;
    private TextView MON, TUE, WED, THU, FRI, SAT, SUN;

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

        MON = view.findViewById(R.id.txtMonday);
        TUE = view.findViewById(R.id.txtTueday);
        WED = view.findViewById(R.id.txtWednesday);
        THU = view.findViewById(R.id.txtThursday);
        FRI = view.findViewById(R.id.txtFriday);
        SAT = view.findViewById(R.id.txtSaturday);
        SUN = view.findViewById(R.id.txtSunday);

        DatabaseReference getmonday = firebaseDatabase.getReference().child("official_duty_roster").child(mAuth.getUid()).child("MON");
        getmonday.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String worktime = dataSnapshot.getValue(String.class);
                MON.setText(worktime);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        DatabaseReference gettueday = firebaseDatabase.getReference().child("official_duty_roster").child(mAuth.getUid()).child("TUE");
        gettueday.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String worktime = dataSnapshot.getValue(String.class);
                TUE.setText(worktime);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        DatabaseReference getwednesday = firebaseDatabase.getReference().child("official_duty_roster").child(mAuth.getUid()).child("WED");
        getwednesday.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String worktime = dataSnapshot.getValue(String.class);
                WED.setText(worktime);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        DatabaseReference getthursday = firebaseDatabase.getReference().child("official_duty_roster").child(mAuth.getUid()).child("THU");
        getthursday.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String worktime = dataSnapshot.getValue(String.class);
                THU.setText(worktime);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        DatabaseReference getfriday = firebaseDatabase.getReference().child("official_duty_roster").child(mAuth.getUid()).child("FRI");
        getfriday.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String worktime = dataSnapshot.getValue(String.class);
                FRI.setText(worktime);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        DatabaseReference getsaturday = firebaseDatabase.getReference().child("official_duty_roster").child(mAuth.getUid()).child("SAT");
        getsaturday.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String worktime = dataSnapshot.getValue(String.class);
                SAT.setText(worktime);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        DatabaseReference getsunday = firebaseDatabase.getReference().child("official_duty_roster").child(mAuth.getUid()).child("SUN");
        getsunday.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String worktime = dataSnapshot.getValue(String.class);
                SUN.setText(worktime);
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
                if(preferenceradio.getCheckedRadioButtonId() == -1){
                    Toast.makeText(getActivity(),"Submit Error, No Shift Preference is Selected!", Toast.LENGTH_LONG).show();
                }else{
                DatabaseReference databaseReference = firebaseDatabase.getReference().child("Shift").child("Shift Preference").child(mAuth.getUid());
                databaseReference.setValue(shiftpreference);
                Toast.makeText(getActivity(), "Submit Preference Successful!", Toast.LENGTH_LONG).show();
                }
            }
        });

        SelectedPreference = view.findViewById(R.id.txtShiftPreferenceSelected);
        DatabaseReference getshiftpreference = FirebaseDatabase.getInstance().getReference().child("Shift").child("Shift Preference").child(mAuth.getUid());
        getshiftpreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String sp = dataSnapshot.getValue(String.class);
                SelectedPreference.setText(sp);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Publicholiday = view.findViewById(R.id.txtpublicholiday);
        Annualleave = view.findViewById(R.id.txtannualleave);
        MC = view.findViewById(R.id.txtmc);
        Leaveremain = view.findViewById(R.id.txtleaveremain);
        leavestatus = view.findViewById(R.id.txtleavependingstatus);

        DatabaseReference getleavestatus = firebaseDatabase.getReference().child("Medical Leave").child(mAuth.getUid()).child("leavestatus");
        getleavestatus.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String Leavestatus = dataSnapshot.getValue(String.class);
                leavestatus.setText(Leavestatus);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

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
