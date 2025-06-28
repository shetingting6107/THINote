package com.afu.app.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.afu.app.Constant;
import com.afu.app.R;
import com.afu.app.client.OkhttpPostRequest;
import com.afu.app.client.OkhttpResponse;
import com.afu.app.utls.DateUtils;
import com.afu.app.utls.SPUtils;
import com.afu.app.utls.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.Calendar;
import java.util.HashMap;

/**
 * 创建事物详情页
 * 编辑添加的事物详情，例：名称、图片、到期时间、提醒时间等
 */
public class CreateItemDetailActivity extends Activity {

    public static final String TAG = "CreateItemDetailActivity";
    private TextView mTvBack;
    private TextView mEtItemName;
    private TextView mEtItemExpireTime;
    private TextView mEtItemNoticeTime;
    private TextView mBtAdd;
    private String itemName;
    private String expireTime;
    private String noticeTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_create_item_detail);
        initView();
    }

    private void initView() {
        mTvBack = findViewById(R.id.tv_back);
        mEtItemName = findViewById(R.id.et_item_name);
        mEtItemExpireTime = findViewById(R.id.et_item_expire_time);
        mEtItemNoticeTime = findViewById(R.id.et_item_notice_time);
        mBtAdd = findViewById(R.id.bt_add);

        mTvBack.setOnClickListener(v -> onBackPressed());
        mEtItemExpireTime.setOnClickListener(v -> showTimePicker("选择到期时间", time -> {
            expireTime = time;
            mEtItemExpireTime.setText(time);
        }));
        mEtItemNoticeTime.setOnClickListener(v -> showTimePicker("选择提醒时间", time -> {
            noticeTime = time;
            mEtItemNoticeTime.setText(time);
        }));

        mBtAdd.setOnClickListener(v -> {
            itemName = mEtItemName.getText().toString();
            if (TextUtils.isEmpty(itemName)) {
                ToastUtils.showToast(CreateItemDetailActivity.this, "请输入物品名称!", Toast.LENGTH_SHORT);
                return;
            }
            if (TextUtils.isEmpty(expireTime)) {
                ToastUtils.showToast(CreateItemDetailActivity.this, "请输入物品到期时间!", Toast.LENGTH_SHORT);
                return;
            }
            if (TextUtils.isEmpty(noticeTime)) {
                Dialog dialog = new AlertDialog.Builder(CreateItemDetailActivity.this)
                        .setTitle("未选择物品到期时间")
                        .setMessage("您当前未选择物品到期时间，将自动选择当前物品到期时间的前24h，做为提醒时间。")
                        .setPositiveButton("确定", (dialog1, which) -> addItem())
                        .setNegativeButton("取消", null)
                        .create();
                dialog.show();
                return;
            }
            addItem();
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void addItem() {
        new Thread(() -> {
            try {
                String url = "http://192.168.3.65:8080/thiNote_api/item_create";
                HashMap<String, Object> param = new HashMap<>();
                String userId = SPUtils.getString(this, Constant.SP_USER_ID);
                Long expire = DateUtils.transDataStampToTime(expireTime);
                Long notice = DateUtils.transDataStampToTime(noticeTime);
                if (notice == null) {
                    notice = expire - (24 * 60 * 60 * 1000);
                }
                param.put("userId", userId);
                param.put("itemName", itemName);
                param.put("itemImageUrl", "111");//测试数据
                param.put("itemExpireTime", expire);
                param.put("itemNoticeTime", notice);
                JSONObject json = new JSONObject(param);
                String response = OkhttpPostRequest.post(url, json.toString());
                Log.d(TAG, "addItem response = " + response);
                Gson gson = new Gson();
                OkhttpResponse<String> res = gson.fromJson(response, OkhttpResponse.class);
                if (res != null && res.getCode() == Constant.HTTP_SUCCESS) {
                    runOnUiThread(() -> {
                        ToastUtils.showToast(CreateItemDetailActivity.this, "添加物品成功！", Toast.LENGTH_SHORT);
                        Intent intent = getIntent();
                        setResult(RESULT_OK, intent);
                        onBackPressed();
                    });
                }else {
                    runOnUiThread(() -> ToastUtils.showToast(this, res != null ? res.getMsg() : "添加物品异常！", Toast.LENGTH_SHORT));
                }
            } catch (Exception e) {
                Log.e(TAG, "login error, msg = " + e.getMessage());
                runOnUiThread(() -> ToastUtils.showToast(this, "添加物品异常！ msg =" + e.getMessage(), Toast.LENGTH_SHORT));
                e.printStackTrace();
            }
        }).start();
    }

    private void showTimePicker(String title, TimeResultCallback callback) {
        View view = LayoutInflater.from(this).inflate(R.layout.custom_datetime_picker, null);
        DatePicker dataPicker = view.findViewById(R.id.datePicker);
        TimePicker timePicker = view.findViewById(R.id.timePicker);
        timePicker.setIs24HourView(true);

        Dialog dialog = new AlertDialog.Builder(this)
                .setTitle(title)
                .setView(view)
                .setPositiveButton("确定", (dialog1, which) -> {
                    int year = dataPicker.getYear();
                    int month = dataPicker.getMonth();
                    int day = dataPicker.getDayOfMonth();
                    int hour = timePicker.getHour();
                    int minute = timePicker.getMinute();
                    int second = Calendar.getInstance().get(Calendar.SECOND);
                    @SuppressLint("DefaultLocale")
                    String dataTime = String.format(
                            "%04d-%02d-%02d %02d:%02d:%02d",
                            year, month, day, hour, minute, second);
                    callback.timeResult(dataTime);
                })
                .setNegativeButton("取消", null)
                .create();
        Calendar calendar = Calendar.getInstance();
        dataPicker.init(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), null);
        timePicker.setHour(calendar.get(Calendar.HOUR));
        timePicker.setMinute(calendar.get(Calendar.MINUTE));

        dialog.show();
    }

    private interface TimeResultCallback {
        public void timeResult(String time);
    }
}
