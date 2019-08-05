package com.cliquet.gautier.mynews.Utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.cliquet.gautier.mynews.R;

import static com.cliquet.gautier.mynews.MyNewsNotificationChannel.CHANNEL_NEW_ARTICLES;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "notification")
//                .setSmallIcon(R.drawable.ic_launcher_background)
//                .setContentTitle("MyNews")
//                .setContentText("There are x new articles, read them!")
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
//
//        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
//        notificationManager.notify(0, builder.build());

        Toast.makeText(context, "Test", Toast.LENGTH_LONG).show();
    }
}
