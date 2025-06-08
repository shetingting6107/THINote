package com.afu.app;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

/**
 * 创建事物列表页
 * 展示添加的事物列表，以及添加按钮
 */
public class CreateItemListActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_create_item_list);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
