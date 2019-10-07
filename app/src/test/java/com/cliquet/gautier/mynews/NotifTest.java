package com.cliquet.gautier.mynews;

import com.cliquet.gautier.mynews.Models.Notif;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class NotifTest {

    @Test
    public void checkDocsSize() {
    }

    @Test
    public void generateNotificationFirstTimeUse() {
        Notif notif = new Notif();
        String notifString;

        notifString = notif.firstTimeUse(0);
        assertEquals("There is no new article, maybe later!", notifString);

        notifString = notif.firstTimeUse(1);
        assertEquals("There is new articles, check them out!", notifString);
    }

    @Test
    public void generateNotificationDailyUse() {
        Notif notif = new Notif();
        String notifString;

        notifString = notif.dailyUse(0);
        assertEquals("There is no new article, maybe later.", notifString);

        notifString = notif.dailyUse(1);
        assertEquals("There is 1 new article, check it!", notifString);

//        notifString = notif.dailyUse(16);
//        assertEquals("There are 16 new articles, check them!", notifString);

        notifString = notif.dailyUse(17);
        assertEquals("There are 16 new articles, check them!", notifString);
    }

    @Test
    public void calcNbrArticles() {
        Notif notif = new Notif();
        int nbrArticles;

        nbrArticles = notif.countingNewArticles(6, 2);
        assertEquals(26, nbrArticles);

        nbrArticles = notif.countingNewArticles(4, 0);
        assertEquals(4, nbrArticles);
    }
}