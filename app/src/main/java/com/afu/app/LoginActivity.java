package com.afu.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.afu.app.client.OkhttpPostRequest;

import org.json.JSONObject;

import java.util.HashMap;

public class LoginActivity extends Activity {

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
                Log.d("POST_RESPONSE", response);
                if (response.contains("\"code\":200")) {
                    runOnUiThread(() -> Toast.makeText(LoginActivity.this, "登录成功！", Toast.LENGTH_SHORT).show());
                    Intent intent = new Intent(Constant.ACTION_MAIN);
                    intent.setPackage(this.getPackageName());
                    this.startActivity(intent);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
