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

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder>{

    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;
    private Context context;
    private List<chatmodel> chatmodels;
    private String imageurl;

    FirebaseAuth mAuth;

//add String imageurl
    public MessageAdapter(Context context, List<chatmodel> chatmodels, String imageurl){
        this.context = context;
        this.chatmodels = chatmodels;
        this.imageurl = imageurl;
    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == MSG_TYPE_RIGHT) {
            View view = LayoutInflater.from(context).inflate(R.layout.chat_item_right, viewGroup, false);
            return new MessageAdapter.ViewHolder(view);
        }else {
            View view = LayoutInflater.from(context).inflate(R.layout.chat_item_left, viewGroup, false);
            return new MessageAdapter.ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder viewHolder, int i) {
    final chatmodel chat = chatmodels.get(i);
    viewHolder.show_message.setText(chat.getMessage());

        if (imageurl.equals("defaults")){
            viewHolder.profile_image.setImageResource(R.mipmap.ic_launcher);
         }else {
            Glide.with(context).load(imageurl).into(viewHolder.profile_image);
        }

     if (i == chatmodels.size()-1){
            if (chat.isIsseen()){
                viewHolder.txt_seen.setText("Seen");
            }else {
                viewHolder.txt_seen.setText("Delivered");
            }
     }else {
            viewHolder.txt_seen.setVisibility(View.GONE);
     }
    }

    @Override
    public int getItemCount() {
        return chatmodels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView show_message;
        public ImageView profile_image;

        public TextView txt_seen;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            show_message = itemView.findViewById(R.id.show_message);
            profile_image = itemView.findViewById(R.id.profile_image);
            txt_seen = itemView.findViewById(R.id.txt_seen);
        }
    }

    @Override
    public int getItemViewType(int position) {
        mAuth = FirebaseAuth.getInstance();
        if(chatmodels.get(position).getSender().equals(mAuth.getUid())){
            return MSG_TYPE_RIGHT;
        }else {
            return MSG_TYPE_LEFT;
        }
    }
}

