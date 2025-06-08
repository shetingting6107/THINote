package com.afu.app;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

/**
 * 创建事物详情页
 * 编辑添加的事物详情，例：名称、图片、到期时间、提醒时间等
 */
public class CreateItemDetailActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_create_item_detail);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
