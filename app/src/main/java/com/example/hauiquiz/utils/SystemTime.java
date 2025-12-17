package com.example.hauiquiz.utils;

import java.util.Calendar;

public class SystemTime {
    private static final Calendar calendar = Calendar.getInstance();

    public static String getTime() {
        int min = calendar.get(Calendar.MINUTE);
        return calendar.get(Calendar.HOUR) + ":" + (min < 10 ? ("0" + min) : min);
    }

    public static String getDate() {
        return calendar.get(Calendar.DAY_OF_MONTH) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.YEAR);
    }

    public static String[] getHourAndMin(String s) {
        return s.split(":");
    }
}
