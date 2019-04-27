package com.example.dennis.medical;

import android.app.Notification;

public class Notificationmodel {
//    private String notificationmessage, notificationprofilepic, notificationtitle;
//    private String notificationuserId;

    private String Message, Title, Timestamp;

    public Notificationmodel(){

    }

    public Notificationmodel(String Message, String Title, String Timestamp) {
        this.Message = Message;
        this.Title = Title;
        this.Timestamp = Timestamp;
    }

    public String getnotificationMessage() {
        return Message;
    }

    public String getnotificationTitle() {
        return Title;
    }

    public String getnotificationTimestamp() {
        return Timestamp;
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
