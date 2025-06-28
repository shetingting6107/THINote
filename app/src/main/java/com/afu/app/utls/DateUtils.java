package com.afu.app.utls;

import android.text.TextUtils;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtils {

    public static final String TAG = "DateUtils";

    public static String transTimeStampToDate(Long time) {
        SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        simple.setTimeZone(TimeZone.getDefault());//使用系统时间
        return simple.format(new Date(time));
    }

    public static Long transDataStampToTime(String time) {
        if (TextUtils.isEmpty(time)) {
            return null;
        }
        SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        simple.setTimeZone(TimeZone.getDefault());//使用系统时间
        Date date = null;
        try {
            date = simple.parse(time);
        } catch (ParseException e) {
            Log.e(TAG, "parse data to time error , msg =" + e.getMessage());
        }
        return date != null ? date.getTime() : 0L;
    }
}
