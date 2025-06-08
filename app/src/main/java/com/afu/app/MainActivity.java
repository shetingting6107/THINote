package com.afu.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

/**
 * 首页，登录后进入此页面
 */
public class MainActivity extends Activity {

    private Button mBtnMain;
    private Button mBtnTalk;
    private Button mBtnMine;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initView() {
        mBtnMain = findViewById(R.id.btn_main);
        mBtnTalk = findViewById(R.id.btn_talk);
        mBtnMine = findViewById(R.id.btn_mine);

        mBtnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转至添加物品页面
            }
        });
    }

}
