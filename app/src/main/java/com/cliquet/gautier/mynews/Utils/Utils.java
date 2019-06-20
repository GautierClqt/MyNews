package com.cliquet.gautier.mynews.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
}
