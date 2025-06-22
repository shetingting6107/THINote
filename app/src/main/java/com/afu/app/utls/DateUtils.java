package com.afu.app.utls;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtils {

    public static String transTimeStampToDate(Long time) {
        SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        simple.setTimeZone(TimeZone.getDefault());//使用系统时间
        return simple.format(new Date(time));
    }
}
