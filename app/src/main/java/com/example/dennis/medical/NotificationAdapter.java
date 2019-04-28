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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationHolder> {

    Context notificationcontext;
    ArrayList<Notificationmodel> notificationmodels;


    public NotificationAdapter(Context notificationcontext, ArrayList<Notificationmodel>notificationmodels)
    {
        this.notificationcontext = notificationcontext;
        this.notificationmodels = notificationmodels;
    }

    @NonNull
    @Override
    public NotificationHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(notificationcontext).inflate(R.layout.cardview,viewGroup,false);
        return new NotificationAdapter.NotificationHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationHolder notificationHolder, int i) {
        notificationHolder.Notificationtitle.setText(notificationmodels.get(i).getNotificationTitle());
        notificationHolder.Notificationmessage.setText(notificationmodels.get(i).getNotificationMessage());
        notificationHolder.Notificationtimestamp.setText(notificationmodels.get(i).getNotificationTimestamp());

//        if (notificationmodels.get(i).getNotificationprofilepic().equals("defaults")){
//            notificationHolder.Notificationpic.setImageResource(R.mipmap.ic_launcher);
//        }else {
//            Glide.with(notificationcontext).load(notificationmodels.get(i).getNotificationprofilepic()).into(notificationHolder.Notificationpic);
//        }


    }

    @Override
    public int getItemCount() {
        return notificationmodels.size();
    }

    class NotificationHolder extends RecyclerView.ViewHolder{
        private TextView Notificationmessage, Notificationtitle, Notificationtimestamp;
//        private ImageView Delete_Notification;
        //        private ImageView Notificationpic;
        public NotificationHolder(@NonNull final View itemView) {
            super(itemView);
            Notificationmessage = itemView.findViewById(R.id.notification_message);
            Notificationtitle = itemView.findViewById(R.id.notification_title);
            Notificationtimestamp = itemView.findViewById(R.id.timestamp);
//            Delete_Notification = itemView.findViewById(R.id.delete_notification);
//            Notificationpic = itemView.findViewById(R.id.notification_pic);

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (listener != null) {
//                        int i = getAdapterPosition();
//                        if (i != RecyclerView.NO_POSITION) {
//                            listener.onItemClick(i);
//                        }
//                    }
//                }
//            });
//
//            Delete_Notification.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (listener != null) {
//                        int i = getAdapterPosition();
//                        if (i != RecyclerView.NO_POSITION) {
//                            listener.onDeleteClick(i);
//                        }
//                    }
//                }
//            });
        }
    }
}