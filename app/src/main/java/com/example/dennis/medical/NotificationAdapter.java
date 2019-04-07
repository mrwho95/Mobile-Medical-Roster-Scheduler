package com.example.dennis.medical;

import android.content.Context;
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

    public NotificationAdapter(Context nc, ArrayList<Notificationmodel> nms)
    {
        notificationcontext = nc;
        notificationmodels = nms;
    }

    @NonNull
    @Override
    public NotificationHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new NotificationHolder(LayoutInflater.from(notificationcontext).inflate(R.layout.cardview,viewGroup,false));

    }

    @Override
    public void onBindViewHolder(@NonNull NotificationHolder notificationHolder, int i) {
            notificationHolder.Notificationtitle.setText(notificationmodels.get(i).getNotificationtitle());
            notificationHolder.Notificationmessage.setText(notificationmodels.get(i).getNotificationmessage());

        if (notificationmodels.get(i).getNotificationprofilepic().equals("defaults")){
            notificationHolder.Notificationpic.setImageResource(R.mipmap.ic_launcher);
        }else {
            Glide.with(notificationcontext).load(notificationmodels.get(i).getNotificationprofilepic()).into(notificationHolder.Notificationpic);
        }

    }

    @Override
    public int getItemCount() {
        return notificationmodels.size();
    }

    class NotificationHolder extends RecyclerView.ViewHolder{
        private TextView Notificationmessage, Notificationtitle;
        private ImageView Notificationpic;
        public NotificationHolder(@NonNull View itemView) {
            super(itemView);
            Notificationmessage = itemView.findViewById(R.id.notification_message);
            Notificationtitle = itemView.findViewById(R.id.notification_title);
            Notificationpic = itemView.findViewById(R.id.notification_pic);

        }
    }
}
