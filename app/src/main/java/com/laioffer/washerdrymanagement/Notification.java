package com.laioffer.washerdrymanagement;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class Notification extends Application {
    public static final String CHANNEL_ID = "channelmachine";
    public static final String CHANNEL_LEFTID = "channelleft";
    @Override
    public void onCreate() {
        super.onCreate();

    }
    private void createNotificationChannels(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel1 = new NotificationChannel(
                    CHANNEL_ID,
                    "channelmachine",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel1.setDescription("This is Channelmachine");
            NotificationChannel channel2 = new NotificationChannel(
                    CHANNEL_LEFTID,
                    "channelleft",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            channel2.setDescription("This is Channelleft");
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
            manager.createNotificationChannel(channel2);
        }
    }
}
