package com.gpstracking.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtil {
    public static final String FORMAT_DD_MM_YYYY_HH_MM_SS = "dd/MM/YYYY HH:mm:ss";
    public static String convertDateToString(Date date, String pattern) {
        if (date == null) {
            return null;
        }

        SimpleDateFormat sf = new SimpleDateFormat(pattern);
        return sf.format(date);
    }
}
