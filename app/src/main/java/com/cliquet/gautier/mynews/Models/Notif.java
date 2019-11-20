package com.cliquet.gautier.mynews.Models;

public class Notif{

    private String notifString;

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

        if(nbrArticles == 0){
            notifString = "There is no new article, maybe later!";
        }
        else if(nbrArticles == 1) {
            notifString = "There is 1 new article, check it!";
        }
        else if(nbrArticles > 1) {
            notifString = "There are "+ nbrArticles +" new articles, check them!";
        }

        return notifString;
    }
}
