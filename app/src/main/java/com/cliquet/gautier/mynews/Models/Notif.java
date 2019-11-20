package com.cliquet.gautier.mynews.Models;

import android.content.res.Resources;
import com.cliquet.gautier.mynews.R;

public class Notif{

    private String notifString;

    public String firstTimeUse(int responseSize) {

        if(responseSize == 0) {
            notifString = Resources.getSystem().getString(R.string.no_new_article);
        }
        else if(responseSize > 0) {
            notifString = Resources.getSystem().getString(R.string.new_articles);
        }

        return notifString;
    }

    public String dailyUse(int nbrArticles) {

        if(nbrArticles == 0){
            notifString = Resources.getSystem().getString(R.string.no_new_article);
        }
        else if(nbrArticles == 1) {
            notifString = Resources.getSystem().getString(R.string.one_new_article);
        }
        else if(nbrArticles > 1) {
            notifString = Resources.getSystem().getString(R.string.x_new_articles, nbrArticles);
        }

        return notifString;
    }
}
