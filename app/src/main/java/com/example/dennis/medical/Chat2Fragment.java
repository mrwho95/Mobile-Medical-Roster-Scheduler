package com.example.dennis.medical;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Chat2Fragment extends Fragment {


    public Chat2Fragment() {
        // Required empty public constructor
    }

    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private List<UserProfile> userProfiles;
    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_chat2, container, false);
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        recyclerView = view.findViewById(R.id.list_recycle_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        userProfiles = new ArrayList<>();
        readUser();

        return view;
    }

    public void readUser(){
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userProfiles.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    UserProfile userProfile = snapshot.getValue(UserProfile.class);
                    assert userProfile != null;
                    assert mAuth != null;
                    if (!userProfile.getUserId().equals(mAuth.getUid())){
                        userProfiles.add(userProfile);
                    }
                }
                userAdapter = new UserAdapter(getContext(), userProfiles, true);
                recyclerView.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
