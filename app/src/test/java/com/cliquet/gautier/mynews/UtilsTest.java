package com.cliquet.gautier.mynews;

import com.cliquet.gautier.mynews.Utils.Utils;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

public class UtilsTest {

    @Test
    public void formatDateForQuery() {
        Utils utils = new Utils();
        String date;

        //Month is less than 10
        date = utils.dateStringFormat(2019,8, 16);
        assertEquals("20190816", date);

        //day is less than 10
        date = utils.dateStringFormat(2019,11, 2);
        assertEquals("20191102", date);

        //day and month are less than 10
        date = utils.dateStringFormat(2019,3, 7);
        assertEquals("20190307", date);

        //all is 10 or more
        date = utils.dateStringFormat(2019,12,25);
        assertEquals("20191225", date);
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
        list.add("arts");
        list.add("politics");
        list.add("business");
        
        HashMap<String, String> testHashMap = new HashMap<>();
        testHashMap.put("q", "hello");
        testHashMap.put("begin_date", "20190512");
        testHashMap.put("end_date", "20191002");
        testHashMap.put("fq", "news_desk:(\"arts\" \"politics\" \"business\")");

        HashMap = utils.creatHashMapQueries("hello", "20190512", "20191002", list);
        assertEquals(testHashMap, HashMap);
    }
}
