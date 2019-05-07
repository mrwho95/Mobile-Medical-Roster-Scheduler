package com.example.dennis.medical;


import android.app.Notification;
import android.content.Intent;
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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
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

//    private static final String Notification_URL = "http://127.0.0.1/Medical/secret/notificationapi.php";
//    private static final String Notification_URL = "http://10.114.44.244/Medical/secret/notificationapi.php";

    private RecyclerView NotificationRecycleView;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    ArrayList<Notificationmodel> notificationmodels;
    NotificationAdapter notificationAdapter;
    private TextView notification_title, notification_message, notification_timestamp;
    Intent intent;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_duty_alert, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();

        NotificationRecycleView = view.findViewById(R.id.notificationrecycleview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        NotificationRecycleView.setLayoutManager(linearLayoutManager);
        NotificationRecycleView.setHasFixedSize(true);

        notificationmodels = new ArrayList<>();

//        loadnotification();
//        setButton();

//        intent = getActivity().getIntent();
//        final String user_id = intent.getStringExtra("userId");

        databaseReference = FirebaseDatabase.getInstance().getReference("Notification");
        mAuth = FirebaseAuth.getInstance();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                notificationmodels.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Notificationmodel notificationmodel = dataSnapshot1.getValue(Notificationmodel.class);
                    notificationmodels.add(notificationmodel);
//                    assert notificationmodel != null;
//                    assert mAuth != null;
//                    if (!notificationmodel.getNotificationuserId().equals(mAuth.getUid())){
//                        notificationmodels.add(notificationmodel);
//                    }
//                    notification_message.setText(notificationmodel.getnotificationMessage());
//                    notification_title.setText(notificationmodel.getnotificationTitle());
//                    notification_timestamp.setText(notificationmodel.getnotificationTimestamp());
                }

                notificationAdapter = new NotificationAdapter(getContext(), notificationmodels);
                NotificationRecycleView.setAdapter(notificationAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Opps, something is wrong!", Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }

//    private void loadnotification(){
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, Notification_URL, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                try {
//                    JSONArray notification = new JSONArray(response);
//                    for(int i = 0; i<notification.length(); i++){
//                        JSONObject notificationobject = notification.getJSONObject(i);
//                        String Message = notificationobject.getString("Message");
//                        String Title = notificationobject.getString("Title");
//
//
//                        Notificationmodel notificationmodel = new Notificationmodel(Message, Title,);
//                        notificationmodels.add(notificationmodel);
//                    }
//
//                    notificationAdapter = new NotificationAdapter(getContext(), notificationmodels);
//                    NotificationRecycleView.setAdapter(notificationAdapter);
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
//            }
//        });
//        Volley.newRequestQueue(getActivity()).add(stringRequest);
//    }


}
