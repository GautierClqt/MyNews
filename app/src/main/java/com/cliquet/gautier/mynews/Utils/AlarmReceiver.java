package com.cliquet.gautier.mynews.Utils;

import android.app.AlarmManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.cliquet.gautier.mynews.Models.Articles;
import com.cliquet.gautier.mynews.Models.ArticlesElements;
import com.cliquet.gautier.mynews.Models.PojoArticleSearch.Response;
import com.cliquet.gautier.mynews.Models.PojoCommon.PojoMaster;

import java.util.ArrayList;
import java.util.Calendar;


public class AlarmReceiver extends BroadcastReceiver implements NYtimesCalls.Callbacks {

    ArticlesElements articlesElements = new ArticlesElements();
    Response response = new Response();

    String jsonQueriesHM;
    SharedPreferences preferences;

    String notifString;

    @Override
    public void onReceive(Context context, Intent intent) {

        //jsonQueriesHM = preferences.getString("notifications", "");

//        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "notification")
//                .setSmallIcon(R.drawable.ic_launcher_background)
//                .setContentTitle("MyNews")
//                .setContentText("There are x new articles, read them!")
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
//
//        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
//        notificationManager.notify(0, builder.build());


        //this.executeHttpRequestWithRetrofit();
        Log.d("OnReceive", "TEST");
        Toast.makeText(context, "Test", Toast.LENGTH_LONG).show();
    }

    private void executeHttpRequestWithRetrofit() {
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
//        else {
//            ArrayList<Articles> mArticles = articlesElements.settingListsPojoArticleSearch(response);
//        }
    }

    @Override
    public void onFailure() {
        notifString = "Sorry, there was an error, we can't tell you about new articles right now...";
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

}
