package com.cliquet.gautier.mynews;

import com.cliquet.gautier.mynews.Utils.Utils;

import org.junit.Test;

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
}
