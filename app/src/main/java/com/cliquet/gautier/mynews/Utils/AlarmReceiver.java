package com.cliquet.gautier.mynews.Utils;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.cliquet.gautier.mynews.Models.Notif;
import com.cliquet.gautier.mynews.Models.PojoArticleSearch.Response;
import com.cliquet.gautier.mynews.Models.PojoCommon.PojoMaster;
import com.cliquet.gautier.mynews.R;

import static com.cliquet.gautier.mynews.MyNewsNotificationChannel.CHANNEL_NEW_ARTICLES;


public class AlarmReceiver extends BroadcastReceiver implements NYtimesCalls.Callbacks {

    Context context;

    Response response = new Response();

    String jsonQueriesHM;
    SharedPreferences preferences;

    NotificationManagerCompat notificationManager;
    String notifString;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        setPreferences();
        jsonQueriesHM = preferences.getString("notifications", "");
        notificationManager = NotificationManagerCompat.from(context);
        this.executeHttpRequestWithRetrofit();
        Log.d("OnReceive", "TEST");
    }

    private void executeHttpRequestWithRetrofit() {
        NYtimesCalls.fetchArticles(this, jsonQueriesHM, 2);
    }

    @Override
    public void onResponse(PojoMaster pojoMaster) {
        int newArticles = 0;

        //if(pojoMaster.getResponse() != null) {
        if(Integer.parseInt(pojoMaster.getResponse().getMeta().getHits()) != 0) {
            Utils utils = new Utils();
            Notif notif = new Notif();

            int iteration = 0;
            boolean boolArticles = false;

            response = pojoMaster.getResponse();
            int responseSize = response.getDocs().size();
            setPreferences();

            String newDate = response.getDocs().get(0).getPubDate();
            String lastDate = preferences.getString("last_date", "");
            preferences.edit().putString("last_date", newDate).apply();

            //the first time use of notifactions will just look if those queries are returning articles
            if (lastDate != null || lastDate.equals("")) {
                notif.firstTimeUse(responseSize);
            }
            //looking in the last 100 articles if there is a match in publication date
            while (iteration <= 100 && !boolArticles) {
                for (int i = 0; i <= responseSize - 1; i++) {
                    if (response.getDocs().get(i).getPubDate().equals(lastDate)) {
                        newArticles = iteration;
                        boolArticles = true;
                        break;
                    } else if (i == responseSize - 1) {
                        jsonQueriesHM = utils.hashMapPagination(jsonQueriesHM);
                        this.executeHttpRequestWithRetrofit();
                    }
                    iteration++;
                }
            }
            //once the searching is done this will prepare the notif text
            notif.dailyUse(newArticles);
        }
        sendingNotification();
    }

    @Override
    public void onFailure() {
        notifString = "Sorry, there was an error, we can't tell you about new articles right now...";
        sendingNotification();
    }

    private void setPreferences() {
        preferences = context.getSharedPreferences("MyNewsPreferences", Context.MODE_PRIVATE);
    }

    private void sendingNotification() {
        Notification notification = new NotificationCompat.Builder(context, CHANNEL_NEW_ARTICLES)
                .setSmallIcon(R.drawable.ic_nytlogo)
                .setContentTitle("MyNews")
                .setContentText(notifString)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build();

        notificationManager.notify(0, notification);
    }

}
