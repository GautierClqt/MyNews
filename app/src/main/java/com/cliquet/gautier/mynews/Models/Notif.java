package com.cliquet.gautier.mynews.Models;

import com.cliquet.gautier.mynews.Models.PojoArticleSearch.Response;

public class Notif {

    private String notifString;
    private int nbrArticles;


    public String firstTimeUse(int responseSize) {
        if(responseSize == 0) {
            notifString = "There is no new article, maybe later!";
        }
        else if(responseSize > 0) {
            notifString = "There is new articles, check them out!";
        }

        return notifString;
    }

    public String dailyUse(int nbrArticles) {
        this.nbrArticles = nbrArticles;

        if(nbrArticles == 0){
            notifString = "There is no new article, maybe later.";
        }
        else if(nbrArticles == 1) {
            notifString = "There is 1 new article, check it!";
        }
        else if(nbrArticles > 1) {
            notifString = "There are " + nbrArticles + " new articles, check them!";
        }

        return notifString;
    }

    //determine how much new articles taking account of pagination
    public int countingNewArticles(int i, int pageNbr) {
        nbrArticles = pageNbr*10 + i;

        return nbrArticles;
    }
}
