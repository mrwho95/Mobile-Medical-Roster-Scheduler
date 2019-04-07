package com.example.dennis.medical;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.CookieJar;

public class ChatMessage extends AppCompatActivity {


    CircleImageView user_profile_image;
    TextView senderusername;
    EditText sender_text;
    ImageButton sender_button;

    MessageAdapter messageAdapter;
    List<chatmodel> chatmodels;
    RecyclerView recyclerView;

    Intent intent;

    ValueEventListener seenListener;

    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_message);
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setTitle("Roster Schedule");

//        Toolbar toolbar = findViewById(R.id.Toolbar);
//        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.chat_recycle_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        user_profile_image = findViewById(R.id.profile_image);
        senderusername = findViewById(R.id.username);
        sender_button = findViewById(R.id.sendchat_btn);
        sender_text = findViewById(R.id.message_content);


        intent = getIntent();
        final String user_id = intent.getStringExtra("userId");

        sender_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = sender_text.getText().toString();
                if (!msg.equals("")){
                    sendMessage(mAuth.getUid(), user_id, msg);
                }else {
                    Toast.makeText(ChatMessage.this, "No message is sent!", Toast.LENGTH_LONG).show();
                }
                sender_text.setText("");
            }
        });

        mAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(user_id);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                senderusername.setText(userProfile.getuserFullName());

                if (userProfile.getUserImageurl().equals("defaults")){
                    user_profile_image.setImageResource(R.mipmap.ic_launcher);
                    }else {
                    Glide.with(getApplicationContext()).load(userProfile.getUserImageurl()).into(user_profile_image);
                    }


                readMessage(mAuth.getUid(),user_id, userProfile.getUserImageurl());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        seenMesssage(user_id);
    }

    private void seenMesssage(final String user_id){
    databaseReference = FirebaseDatabase.getInstance().getReference("Chats");
    seenListener = databaseReference.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                chatmodel chat = snapshot.getValue(chatmodel.class);
                if (chat.getReceiver().equals(mAuth.getUid()) && chat.getSender().equals(user_id)){
                    HashMap<String, Object>hashMap = new HashMap<>();
                    hashMap.put("isseen",true);
                    snapshot.getRef().updateChildren(hashMap);
                }
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });
    }

    private void sendMessage(String sender, String receiver, String message){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        HashMap<String, Object>hashMap = new HashMap<>();
        hashMap.put("sender",sender);
        hashMap.put("receiver", receiver);
        hashMap.put("message", message);
        hashMap.put("isseen", false);

        databaseReference.child("Chats").push().setValue(hashMap);
    }


    private void readMessage(final String myid , final String user_id, final String imageurl){
        chatmodels = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("Chats");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chatmodels.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    chatmodel chat = snapshot.getValue(chatmodel.class);
//                    Log.d("fwefwefwefwefwef", "loadJoinedClass: ");
                    if (chat.getReceiver().equals(myid) && chat.getSender().equals(user_id)
                            || chat.getReceiver().equals(user_id) && chat.getSender().equals(myid)){
                            chatmodels.add(chat);
                        }
                        messageAdapter = new MessageAdapter(ChatMessage.this, chatmodels, imageurl);
                        recyclerView.setAdapter(messageAdapter);
                    }
                }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onPause(){
        super.onPause();
        userstatus("offline");
        databaseReference.removeEventListener(seenListener);
    }

    private void userstatus(String userstatus){

        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(mAuth.getUid());
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("userstatus", userstatus);
        databaseReference.updateChildren(hashMap);

    }

    @Override
    public void onResume() {
        super.onResume();
        userstatus("online");
    }

}
