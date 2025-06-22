package com.afu.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.afu.app.R;
import com.afu.app.utls.ToastUtils;

/**
 * 创建事物详情页
 * 编辑添加的事物详情，例：名称、图片、到期时间、提醒时间等
 */
public class CreateItemDetailActivity extends Activity {

    private TextView mTvBack;
    private TextView mEtItemName;
    private TextView mBtAdd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_create_item_detail);
        initView();
    }

    private void initView() {
        mTvBack = findViewById(R.id.tv_back);
        mEtItemName = findViewById(R.id.et_item_name);
        mBtAdd = findViewById(R.id.bt_add);

        mTvBack.setOnClickListener(v -> onBackPressed());

        mBtAdd.setOnClickListener(v -> {
            String name = mEtItemName.getText().toString();
            if (TextUtils.isEmpty(name)) {
                ToastUtils.showToast(CreateItemDetailActivity.this, "请输入物品名称!", Toast.LENGTH_SHORT);
                return;
            }
            Intent intent = getIntent();
            intent.putExtra("name", name);
            setResult(RESULT_OK, intent);
            onBackPressed();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
