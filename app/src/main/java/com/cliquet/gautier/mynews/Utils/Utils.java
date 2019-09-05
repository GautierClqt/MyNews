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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class Utils {

    public String simplifyDateFormat(String rawDate) throws ParseException {
        Date simpleDate;
        if(rawDate != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.FRANCE);
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
            dateLayoutFormat = (dateLayoutFormat+"/0"+(month+1));
        }
        else {
            dateLayoutFormat = (dateLayoutFormat+"/"+(month+1));
        }

        if(dayOfMonth < 10) {
            dateLayoutFormat = (dateLayoutFormat+"/0"+dayOfMonth);
        }
        else {
            dateLayoutFormat = (dateLayoutFormat+"/"+dayOfMonth);
        }

        return dateLayoutFormat;
    }

    public String dateStringFormat(int year, int month, int dayOfMonth) {
        String dateLayoutFormat;

        dateLayoutFormat = (year+"");

        if(month < 10) {
            dateLayoutFormat = (dateLayoutFormat+"0"+(month+1));
        }
        else {
            dateLayoutFormat = (dateLayoutFormat+(month+1));
        }

        if(dayOfMonth < 10) {
            dateLayoutFormat = (dateLayoutFormat+"0"+dayOfMonth);
        }
        else {
            dateLayoutFormat = (dateLayoutFormat+dayOfMonth);
        }

        return dateLayoutFormat;
    }

    public String notificationBeginDate() {
        String beginDate;
        Calendar calendar = Calendar.getInstance();

        beginDate = createNotificationDate(calendar);

        return beginDate;
    }

    public String notificationEndDate() {
        String endDate;
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);

        endDate = createNotificationDate(calendar);

        return endDate;
    }

    public String createNotificationDate(Calendar calendar) {
        String date;
        int year;
        int month;
        int dayOfMonth;

        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        date = this.dateStringFormat(year, month+1, dayOfMonth);

        return date;
    }

    public HashMap<String, String> creatHashMapQueries (String termsEdittext, String beginDate, String endDate, List<String> queryParamCheckboxes) {
        HashMap<String, String> queriesHashMap = new HashMap<>();

        //put each setted queries in queriesHM.
        String queryCheckboxes = "news_desk:(";

        if(!termsEdittext.equals("")){
            queriesHashMap.put("q", termsEdittext);
        }
        if(!beginDate.equals("")) {
            queriesHashMap.put("begin_date", beginDate);
        }
        if(!endDate.equals("")) {
            queriesHashMap.put("end_date", endDate);
        }
        if(queryParamCheckboxes.size() > 0) {
            for (int i = 0; i < queryParamCheckboxes.size(); i++) {
                if (i == 0) {
                    queryCheckboxes = queryCheckboxes + "\"" + queryParamCheckboxes.get(i) + "\"";
                } else {
                    queryCheckboxes = queryCheckboxes + " \"" + queryParamCheckboxes.get(i) + "\"";
                }
            }
            queryCheckboxes = queryCheckboxes + ")";
            queriesHashMap.put("fq", queryCheckboxes);
        }

        return queriesHashMap;
    }


    public Calendar setMinMaxDate(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.getTimeInMillis();

        return calendar;
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

    String hashMapPagination(String jsonQueries) {
        Gson gson = new Gson();
        HashMap<String, String> searchQueries;

        searchQueries = gson.fromJson(jsonQueries, new TypeToken<HashMap<String, String>>(){}.getType());

        if(searchQueries.get("page") != null) {
            int page = Integer.parseInt(searchQueries.get("page"));
            page++;
            searchQueries.put("page", String.valueOf(page));
        }
        jsonQueries = gson.toJson(searchQueries);

        return jsonQueries;
    }

}
