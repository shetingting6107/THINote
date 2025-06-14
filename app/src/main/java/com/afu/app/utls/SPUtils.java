package com.afu.app.utls;

import android.content.Context;
import android.content.SharedPreferences;

public class SPUtils {

    private static final String SP_NAME = "third_note_sp";

    public static void setString(Context context, String name, String value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(name, value);
        editor.apply();
    }

    public static String getString(Context context, String name) {
        return context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE).getString(name, "");
    }
}
