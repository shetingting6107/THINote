package com.afu.app;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.afu.app.client.OkhttpPostRequest;

import org.json.JSONObject;

import java.util.HashMap;

public class RegisterActivity extends Activity {

    private TextView tv_back;
    private EditText et_register_name;
    private android.widget.EditText et_register_pwd;
    private Button register_btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_register);
        initView();
    }

    private void initView() {
        tv_back = findViewById(R.id.tv_back);
        et_register_name = findViewById(R.id.et_register_name);
        et_register_pwd = findViewById(R.id.et_register_pwd);
        register_btn = findViewById(R.id.btn_register);

        tv_back.setOnClickListener(v -> finish());

        register_btn.setOnClickListener(v -> {
            String name = et_register_name.getText().toString();
            String pwd = et_register_pwd.getText().toString();
            register(name, pwd);
        });
    }

    private void register(String name, String pwd) {
        new Thread(() -> {
            try {
//                String url = "http://192.168.3.32:8082/myapp/api/accounts";
                String url = "http://192.168.3.65:8080/thiNote_api/user_register";
                HashMap<String, String> param = new HashMap<>();
                param.put("username", name);
                param.put("password", pwd);
                JSONObject json = new JSONObject(param);
                String response = OkhttpPostRequest.post(url, json.toString());
                Log.d("POST_RESPONSE", response);
                if (response.contains("id")) {
                    runOnUiThread(() -> Toast.makeText(RegisterActivity.this, "注册成功！", Toast.LENGTH_SHORT).show());
                    finish();
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("An error occurred: " + e.getMessage());
            }
        }).start();
    }
}

