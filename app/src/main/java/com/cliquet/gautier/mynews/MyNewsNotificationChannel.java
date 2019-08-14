package com.cliquet.gautier.mynews;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class MyNewsNotificationChannel extends Application {

    public static final String CHANNEL_NEW_ARTICLES = "new articles channel";

    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannel();
    }

    private void createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_NEW_ARTICLES,
                    "new articles channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            channel.setDescription("Notice if new articles are available");

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
