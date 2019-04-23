package com.example.dennis.medical;

import android.app.Notification;

public class Notificationmodel {
//    private String notificationmessage, notificationprofilepic, notificationtitle;
//    private String notificationuserId;

    private String Message, Title;

    public Notificationmodel(){

    }

    public Notificationmodel(String message, String title) {
        Message = message;
        Title = title;
    }

    public String getnotificationMessage() {
        return Message;
    }

    public String getnotificationTitle() {
        return Title;
    }



    //    public Notificationmodel(String notificationmessage, String notificationtitle, String userId) {
//        this.notificationmessage = notificationmessage;
//        this.notificationtitle = notificationtitle;
//    }

//    public String getNotificationmessage() {
//        return notificationmessage;
//    }
//
//    public void setNotificationmessage(String notificationmessage) {
//        this.notificationmessage = notificationmessage;
//    }
//
//    public String getNotificationtitle() {
//        return notificationtitle;
//    }
//
//    public void setNotificationtitle(String notificationtitle) {
//        this.notificationtitle = notificationtitle;
//    }
//
//    public String getNotificationprofilepic() {
//        return notificationprofilepic;
//    }
//
//    public String getNotificationuserId() {
//        return notificationuserId;
//    }

}
