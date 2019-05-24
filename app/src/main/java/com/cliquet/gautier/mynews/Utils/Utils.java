package com.cliquet.gautier.mynews.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
}
