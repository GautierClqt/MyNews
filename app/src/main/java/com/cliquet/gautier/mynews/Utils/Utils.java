package com.cliquet.gautier.mynews.Utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.TimePicker;
import android.widget.Toast;

import com.cliquet.gautier.mynews.controllers.Activities.SearchQueriesSelection;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Utils {

    public String simplifyDateFormat(String rawDate) throws ParseException {
        Date simpleDate;
        if(rawDate != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            SimpleDateFormat sdfOut = new SimpleDateFormat("dd-MM-yyyy");
            simpleDate = sdf.parse(rawDate);
            rawDate = sdfOut.format(simpleDate);
        }
        return rawDate;
    }

    public String dateStringLayoutFormat(int year, int month, int dayOfMonth) {
        String dateLayoutFormat;

        dateLayoutFormat = (year+"");

        if(month < 10) {
            dateLayoutFormat = (dateLayoutFormat+"/0"+month);
        }
        else {
            dateLayoutFormat = (dateLayoutFormat+"/"+month);
        }

        if(dayOfMonth < 10) {
            dateLayoutFormat = (dateLayoutFormat+"/0"+dayOfMonth);
        }
        else {
            dateLayoutFormat = (dateLayoutFormat+"/"+dayOfMonth);
        }

        return dateLayoutFormat;
    }

    public String dateStringParamFormat(int year, int month, int dayOfMonth) {
        String dateLayoutFormat;

        dateLayoutFormat = (year+"");

        if(month < 10) {
            dateLayoutFormat = (dateLayoutFormat+"0"+month);
        }
        else {
            dateLayoutFormat = (dateLayoutFormat+month);
        }

        if(dayOfMonth < 10) {
            dateLayoutFormat = (dateLayoutFormat+"0"+dayOfMonth);
        }
        else {
            dateLayoutFormat = (dateLayoutFormat+dayOfMonth);
        }

        return dateLayoutFormat;
    }

    public long setMinDate(Calendar calendar) {
        long minDate = calendar.getTimeInMillis();

        return minDate;
    }

    private void startAlarm(Calendar calendar) {
        //Toast.makeText(SearchQueriesSelection.class, "Salut", Toast.LENGTH_LONG).show();

//        AlarmManager alarmManager;
//        PendingIntent alarmIntent;
//
//        calendar = Calendar.getInstance();
//        calendar.setTimeInMillis(System.currentTimeMillis());
//        calendar.set(Calendar.HOUR_OF_DAY, 18);
//
//        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, alarmIntent);
//
//        startAlarm(calendar);
//
//        alarmManager = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
    }

    public String hashMapPagination(String jsonQueries) {
        Gson gson = new Gson();
        HashMap<String, String> searchQueries;

        searchQueries = gson.fromJson(jsonQueries, new TypeToken<HashMap<String, String>>(){}.getType());

        searchQueries.put("page", searchQueries.get("page")+1);
        jsonQueries = gson.toJson(searchQueries);

        return jsonQueries;
    }

}
