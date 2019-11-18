package com.cliquet.gautier.mynews.Utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

public class AlarmStartStop {

    private  AlarmManager mAlarmManager;
    private PendingIntent mAlarmIntent;

    //set the alarm to send a notification at 6pm everyday
    public void startAlarm(Context context) {

        this.setUpManager(context);

        Calendar calendar;
        calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 18);
        calendar.set(Calendar.MINUTE, 0);

        mAlarmManager.setRepeating(AlarmManager.RTC, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, mAlarmIntent);
    }

    public void stopAlarm(Context context) {

        this.setUpManager(context);
        mAlarmManager.cancel(mAlarmIntent);
    }

    private void setUpManager(Context context) {

        mAlarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent mIntent = new Intent(context, AlarmReceiver.class);
        mAlarmIntent = PendingIntent.getBroadcast(context, 0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
