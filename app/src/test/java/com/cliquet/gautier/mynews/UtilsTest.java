package com.cliquet.gautier.mynews;

import com.cliquet.gautier.mynews.Utils.Utils;
import com.google.gson.Gson;

import org.junit.Test;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

public class UtilsTest {

    @Test
    public void changeDateFormat() {
        Utils utils = new Utils();
        String inDate;
        String outDate;

        inDate = "2007-07-25T18:28:21";
        try {
            outDate = utils.simplifyDateFormat(inDate);
            assertEquals("25-07-2007", outDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void formatDateForQuery() {
        Utils utils = new Utils();
        String queryDate;

        //Month is less than 10
        queryDate = utils.dateStringFormat(2019,8, 16);
        assertEquals("20190916", queryDate);

        //day is less than 10
        queryDate = utils.dateStringFormat(2019,11, 2);
        assertEquals("20191202", queryDate);

        //day and month are less than 10
        queryDate = utils.dateStringFormat(2019,3, 7);
        assertEquals("20190407", queryDate);

        //all is 10 or more
        queryDate = utils.dateStringFormat(2019,6,25);
        assertEquals("20190725", queryDate);
    }

    @Test
    public void formatDateForNotification() {
        Utils utils = new Utils();
        String notificationDate;

        Calendar calendar = Calendar.getInstance();

        //basic test
        calendar.set(Calendar.YEAR, 2019);
        calendar.set(Calendar.MONTH, 2);
        calendar.set(Calendar.DAY_OF_MONTH, 3);
        calendar.getTimeInMillis();

        notificationDate = utils.createNotificationDate(calendar);
        assertEquals("20190303", notificationDate);
        
        //test focused on month and day
        calendar.set(Calendar.YEAR, 2019);
        calendar.set(Calendar.MONTH, 9);
        calendar.set(Calendar.DAY_OF_MONTH, 10);
        calendar.getTimeInMillis();

        notificationDate = utils.createNotificationDate(calendar);
        assertEquals("20191010", notificationDate);
    }

    @Test
    public void formatDateForDisplay() {
        Utils utils = new Utils();
        String displayedDate;

        displayedDate = utils.dateStringLayoutFormat(2018, 5, 10);
        assertEquals("10/06/2018", displayedDate);

        displayedDate = utils.dateStringLayoutFormat(2016, 9, 15);
        assertEquals("15/10/2016", displayedDate);

        displayedDate = utils.dateStringLayoutFormat(2019, 11, 9);
        assertEquals("09/12/2019", displayedDate);
    }

    @Test
    public void intToCalendar() {
        Utils utils = new Utils();
        Calendar testCalendar;
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.YEAR, 2019);
        calendar.set(Calendar.MONTH, 2);
        calendar.set(Calendar.DAY_OF_MONTH, 3);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.getTimeInMillis();

        testCalendar = utils.setMinMaxDate(2019, 2, 3);
        assertEquals(calendar, testCalendar);
    }

    @Test
    public void hasmMapConstruction() {
        Utils utils = new Utils();
        HashMap<String, String> HashMap;
        List<String> list = new ArrayList<>();
        HashMap<String, String> testHashMap = new HashMap<>();

        list.add("arts");
        list.add("politics");
        list.add("business");

        testHashMap.put("q", "hello");
        testHashMap.put("begin_date", "20190512");
        testHashMap.put("end_date", "20191002");
        testHashMap.put("fq", "news_desk:(\"arts\" \"politics\" \"business\")");
        testHashMap.put("page", "3");

        HashMap = utils.creatHashMapQueries("hello", "20190512", "20191002", list, 3);
        assertEquals(testHashMap, HashMap);
    }

    @Test public void testHashMapPagination() {
        Utils utils = new Utils();
        Gson gson = new Gson();
        String testJson;
        String json;

        HashMap<String, String> testHashMap = new HashMap<>();
        testHashMap.put("page", "3");
        testJson = gson.toJson(testHashMap);

        json = "{\"page\":\"2\"}";

        json = utils.hashMapPagination(json);
        assertEquals(json, testJson);
    }
}
