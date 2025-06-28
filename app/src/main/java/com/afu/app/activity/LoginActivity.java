package com.afu.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.afu.app.Constant;
import com.afu.app.R;
import com.afu.app.client.OkhttpPostRequest;
import com.afu.app.client.OkhttpResponse;
import com.afu.app.module.LoginResponse;
import com.afu.app.utls.SPUtils;
import com.afu.app.utls.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;

public class LoginActivity extends Activity {

    private static final String TAG = "LoginActivity";

    private EditText et_login_name;
    private EditText et_login_pwd;
    private Button login_btn;
    private Button register_btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);
        initView();
    }

    private void initView() {
        et_login_name = findViewById(R.id.et_login_name);
        et_login_pwd = findViewById(R.id.et_login_pwd);
        login_btn = findViewById(R.id.btn_login);
        register_btn = findViewById(R.id.btn_register);

        login_btn.setOnClickListener(v -> {
            //登录
            String name = et_login_name.getText().toString();
            String pwd = et_login_pwd.getText().toString();
            login(name, pwd);
        });

        register_btn.setOnClickListener(v -> {
            //跳转注册界面
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    private void login(String name, String pwd) {
        new Thread(() -> {
            try {
                String url = "http://192.168.3.65:8080/thiNote_api/user_login";
                HashMap<String, String> param = new HashMap<>();
                param.put("username", name);
                param.put("password", pwd);
                JSONObject json = new JSONObject(param);
                String response = OkhttpPostRequest.post(url, json.toString());
                Log.d(TAG, "login response = " + response);
                Gson gson = new Gson();
                Type type = new TypeToken<OkhttpResponse<LoginResponse>>(){}.getType();
                OkhttpResponse<LoginResponse> res = gson.fromJson(response, type);
                if (res != null && res.getCode() == Constant.HTTP_SUCCESS) {
                    LoginResponse loginResponse = res.getData();
                    if (loginResponse != null) {
                        SPUtils.setString(this, Constant.SP_USER_TOKEN, loginResponse.getToken());
                        SPUtils.setString(this, Constant.SP_USER_ID, loginResponse.getUserid());
                    }
                    runOnUiThread(() -> ToastUtils.showToast(LoginActivity.this, "登录成功！", Toast.LENGTH_SHORT));
                    Intent intent = new Intent(Constant.ACTION_MAIN);
                    intent.setPackage(this.getPackageName());
                    this.startActivity(intent);
                }else {
                    runOnUiThread(() -> ToastUtils.showToast(this, res != null ? res.getMsg() : "登录异常！", Toast.LENGTH_SHORT));
                }
            } catch (Exception e) {
                Log.e(TAG, "login error, msg = " + e.getMessage());
                runOnUiThread(() -> ToastUtils.showToast(this, "登录失败！", Toast.LENGTH_SHORT));
                e.printStackTrace();
            }
        }).start();
    }
}
