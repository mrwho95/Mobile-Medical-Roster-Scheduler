package com.example.dennis.medical;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>{
    private Context context;
    private List<UserProfile> userProfiles;
    private boolean ischat;

    String theLastMessage;

    public UserAdapter(Context context, List<UserProfile> userProfiles, boolean ischat){
        this.context = context;
        this.userProfiles = userProfiles;
        this.ischat = ischat;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_item, viewGroup, false);
        return new UserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final UserProfile userProfile =  userProfiles.get(i);
        viewHolder.userprofilename.setText(userProfile.getuserFullName());
        if (userProfile.getUserImageurl().equals("defaults")){
            viewHolder.profile_image.setImageResource(R.mipmap.ic_launcher);
        }else {
           Glide.with(context).load(userProfile.getUserImageurl()).into(viewHolder.profile_image);
        }

        if (ischat){
            lastMessage(userProfile.getUserId(), viewHolder.last_msg);
        }else {
            viewHolder.last_msg.setVisibility(View.GONE);
        }

        if (ischat){
            if (userProfile.getuserStatus().equals("online")){
                viewHolder.img_on.setVisibility(View.VISIBLE);
                viewHolder.img_off.setVisibility(View.GONE);
            }else{
                viewHolder.img_on.setVisibility(View.GONE);
                viewHolder.img_off.setVisibility(View.VISIBLE);
            }
        }else {
            viewHolder.img_on.setVisibility(View.GONE);
            viewHolder.img_off.setVisibility(View.GONE);
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChatMessage.class);
                intent.putExtra("userId", userProfile.getUserId());
                context.startActivity(intent);
            }
        });

//        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent1 = new Intent(context, DutyAlertFragment.class);
//                intent1.putExtra("userId", userProfile.getUserId());
//                context.startActivity(intent1);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return userProfiles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView userprofilename;
        public ImageView profile_image;

        private ImageView img_on, img_off;

        private TextView last_msg;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userprofilename = itemView.findViewById(R.id.user_name);
            profile_image = itemView.findViewById(R.id.sender_profile_picture);
            last_msg = itemView.findViewById(R.id.last_msg);
            img_on = itemView.findViewById(R.id.img_on);
            img_off = itemView.findViewById(R.id.img_off);


        }
    }

    private void lastMessage(final String user_id, final TextView last_msg){
        theLastMessage = "default";
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Chats");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                 chatmodel chat = snapshot.getValue(chatmodel.class);
                 if (chat.getReceiver().equals(mAuth.getUid()) && chat.getSender().equals(user_id) ||
                         chat.getReceiver().equals(user_id) && chat.getSender().equals(mAuth.getUid())){
                     theLastMessage = chat.getMessage();
                 }
                }
                switch (theLastMessage){
                    case "default":
                        last_msg.setText("No Message");
                        break;

                    default:
                        last_msg.setText(theLastMessage);
                        break;
                }
                theLastMessage = "default";
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
