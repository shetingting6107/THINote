package com.afu.app.utls;

import android.content.Context;
import android.widget.Toast;

import com.afu.app.activity.RegisterActivity;

public class ToastUtils {

    public static void showToast(Context context, String msg, int showType) {
        Toast.makeText(context, msg, showType).show();
    }
}
