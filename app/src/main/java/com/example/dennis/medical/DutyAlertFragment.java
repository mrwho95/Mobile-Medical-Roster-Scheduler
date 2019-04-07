package com.example.dennis.medical;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


/**
 * A simple {@link Fragment} subclass.
 */
public class DutyAlertFragment extends Fragment {



    public DutyAlertFragment() {
        // Required empty public constructor
    }

    private RecyclerView NotificationRecycleView;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    ArrayList<Notificationmodel> notificationmodels;
    NotificationAdapter notificationAdapter;
    private TextView notification_title, notification_message;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_duty_alert, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();

        NotificationRecycleView = view.findViewById(R.id.notificationrecycleview);
        NotificationRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        NotificationRecycleView.setHasFixedSize(true);
        notificationmodels = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("Notification");
        mAuth = FirebaseAuth.getInstance();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                notificationmodels.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    Notificationmodel notificationmodel =  dataSnapshot1.getValue(Notificationmodel.class);
                    notification_message.setText(notificationmodel.getNotificationmessage());
                    notification_title.setText(notificationmodel.getNotificationtitle());
                    notificationmodels.add(notificationmodel);
                }
                notificationAdapter = new NotificationAdapter(getContext(), notificationmodels);
                NotificationRecycleView.setAdapter(notificationAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Toast.makeText(getActivity(), "Opps, something is wrong!", Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }

}
