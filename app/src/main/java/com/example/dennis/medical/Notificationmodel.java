package com.example.dennis.medical;

public class Notificationmodel {
    private String notificationmessage, notificationprofilepic, notificationtitle;

    public Notificationmodel(){
    }

    public Notificationmodel(String notificationmessage, String notificationtitle) {
        this.notificationmessage = notificationmessage;
        this.notificationtitle = notificationtitle;
    }

    public String getNotificationmessage() {
        return notificationmessage;
    }

    public void setNotificationmessage(String notificationmessage) {
        this.notificationmessage = notificationmessage;
    }

    public String getNotificationtitle() {
        return notificationtitle;
    }

    public void setNotificationtitle(String notificationtitle) {
        this.notificationtitle = notificationtitle;
    }

    public String getNotificationprofilepic() {
        return notificationprofilepic;
    }
}
