package com.unidt.helper.common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateHelper {

    public static String getDateTime() {
        DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String ret = format.format(new Date());
        return ret;
    }

    public static void main(String[] args) {
        System.out.println(DateHelper.getDateTime());
    }
}
