package com.example.dennis.medical;

import android.app.Notification;

public class Notificationmodel {
//    private String notificationmessage, notificationprofilepic, notificationtitle;
//    private String notificationuserId;

      private String NotificationMessage, NotificationTimestamp, NotificationTitle;


      public Notificationmodel(){

      }

    public Notificationmodel(String notificationMessage, String notificationTimestamp, String notificationTitle) {
        NotificationMessage = notificationMessage;
        NotificationTimestamp = notificationTimestamp;
        NotificationTitle = notificationTitle;
    }

    public String getNotificationMessage() {
        return NotificationMessage;
    }

    public void setNotificationMessage(String notificationMessage) {
        NotificationMessage = notificationMessage;
    }

    public String getNotificationTimestamp() {
        return NotificationTimestamp;
    }

    public void setNotificationTimestamp(String notificationTimestamp) {
        NotificationTimestamp = notificationTimestamp;
    }

    public String getNotificationTitle() {
        return NotificationTitle;
    }

    public void setNotificationTitle(String notificationTitle) {
        NotificationTitle = notificationTitle;
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
