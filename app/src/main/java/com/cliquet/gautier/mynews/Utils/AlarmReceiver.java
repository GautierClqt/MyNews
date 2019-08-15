package com.cliquet.gautier.mynews.Utils;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.cliquet.gautier.mynews.Models.Articles;
import com.cliquet.gautier.mynews.Models.ArticlesElements;
import com.cliquet.gautier.mynews.Models.PojoArticleSearch.Response;
import com.cliquet.gautier.mynews.Models.PojoCommon.PojoMaster;
import com.cliquet.gautier.mynews.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;
import static com.cliquet.gautier.mynews.MyNewsNotificationChannel.CHANNEL_NEW_ARTICLES;


public class AlarmReceiver extends BroadcastReceiver implements NYtimesCalls.Callbacks {

    Context context;

    ArticlesElements articlesElements = new ArticlesElements();
    Response response = new Response();

    String jsonQueriesHM;
    SharedPreferences preferences;

    NotificationManagerCompat notificationManager;
    String notifString;

    Gson gson = new Gson();

    @Override
    public void onReceive(Context context, Intent intent) {

        this.context = context;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        //SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        //preferences = context.getSharedPreferences("preferences", Context.MODE_PRIVATE | Context.MODE_MULTI_PROCESS);
        //jsonQueriesHM = preferences.getString("notifications", "");



        notificationManager = NotificationManagerCompat.from(context);

        this.executeHttpRequestWithRetrofit();



        Log.d("OnReceive", "TEST");
        Toast.makeText(context, "Test", Toast.LENGTH_LONG).show();
    }

    private void executeHttpRequestWithRetrofit() {
        //-------FOR TEST!!!!!
        int TEST = 151515151;
        HashMap<String, String> queriesHM = new HashMap<>();
        queriesHM.put("begin_date", "politics");
        String jsonQueriesHM = gson.toJson(queriesHM);
        //END TEST!!!!!-----------
        NYtimesCalls.fetchArticles(this, jsonQueriesHM, 3);
    }

    @Override
    public void onResponse(PojoMaster pojoMaster) {
        if (pojoMaster != null) {
            Utils utils = new Utils();
            response = pojoMaster.getResponse();
            String newDate = response.getDocs().get(0).getPubDate();
            String lastDate = preferences.getString("last_date", "");
            preferences.edit().putString("last_date", newDate).apply();
            int responseSize = response.getDocs().size();
            int articlesNbr = 0;
            int newArticles = 0;

            //the first time use of notifactions will just look if those queries are returning articles
            if (lastDate != null && lastDate.equals("")) {
                this.firstTimeUse(response);
            }

            //looking in the last 100 articles if there is a match in publication date
            while(responseSize > 0 && newArticles == 0 && articlesNbr <= 100) {
                for (int i = 0; i <= responseSize; i++) {
                    if (response.getDocs().get(i).getPubDate().equals(lastDate)) {
                        newArticles = articlesNbr + i;
                        break;
                    } else if (i == responseSize && !(response.getDocs().get(i).getPubDate().equals(lastDate))) {
                        articlesNbr = articlesNbr + i;
                        jsonQueriesHM = utils.hashMapPagination(jsonQueriesHM);
                        this.executeHttpRequestWithRetrofit();
                    }
                }
            }

            //once the searching is done this will prepare the notification text
            this.notificationString(newArticles);
        }
        else if(response.getDocs().size() == 0){
            this.onFailure();
        }
        int TEST = 0;
//        else {
//            ArrayList<Articles> mArticles = articlesElements.settingListsPojoArticleSearch(response);
//        }
        sendingNotification();
    }

    @Override
    public void onFailure() {
        notifString = "Sorry, there was an error, we can't tell you about new articles right now...";
        sendingNotification();
    }

    private void firstTimeUse(Response response) {
        if(response.getDocs().size() == 0) {
            notifString = "There is no new article, maybe later!";
        }
        else if(response.getDocs().size() > 0) {
            notifString = "There is new articles, check them out!";
        }
    }

    private void notificationString(int newArticles) {
        if(newArticles == 0){
            notifString = "There is no new article, maybe later.";
        }
        else if(newArticles == 1) {
            notifString = "There is 1 new article, check it!";
        }
        else if(newArticles > 1) {
            notifString = "There are" + newArticles + "new articles, check them!";
        }
    }

    private void sendingNotification() {
        Notification notification = new NotificationCompat.Builder(context, CHANNEL_NEW_ARTICLES)
                .setSmallIcon(R.drawable.ic_nytlogo)
                .setContentTitle("MyNews")
                //.setContentText("There are x new articles, read them!")
                .setContentText(notifString)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build();

        notificationManager.notify(0, notification);
    }

}
