package com.afu.app.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.afu.app.Constant;
import com.afu.app.R;
import com.afu.app.client.OkhttpPostRequest;
import com.afu.app.client.OkhttpResponse;
import com.afu.app.utls.ToastUtils;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;

public class RegisterActivity extends Activity {

    private static final String TAG = "RegisterActivity";

    private TextView tv_back;
    private EditText et_register_name;
    private EditText et_register_pwd;
    private EditText et_register_email;
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
        et_register_email = findViewById(R.id.et_register_email);
        register_btn = findViewById(R.id.btn_register);

        tv_back.setOnClickListener(v -> finish());

        register_btn.setOnClickListener(v -> {
            String name = et_register_name.getText().toString();
            String pwd = et_register_pwd.getText().toString();
            String email = et_register_email.getText().toString();
            register(name, pwd, email);
        });
    }

    private void register(String name, String pwd, String email) {
        new Thread(() -> {
            try {
//                String url = "http://192.168.3.32:8082/myapp/api/accounts";
                String url = Constant.BASE_URL + "/thiNote_api/user_register";
                HashMap<String, String> param = new HashMap<>();
                param.put("username", name);
                param.put("password", pwd);
                param.put("email", email);
                JSONObject json = new JSONObject(param);
                String responseStr = OkhttpPostRequest.post(url, json.toString());
                Log.d("POST_RESPONSE", responseStr);
                Gson gson = new Gson();
                OkhttpResponse<String> response = gson.fromJson(responseStr, OkhttpResponse.class);
                if (response != null && response.getCode() == Constant.HTTP_SUCCESS) {
                    runOnUiThread(() -> ToastUtils.showToast(RegisterActivity.this, "注册成功！", Toast.LENGTH_SHORT));
                    finish();
                }else {
                    String msg = response == null ? "response 为空 " : response.getMsg();
                    runOnUiThread(() -> ToastUtils.showToast(RegisterActivity.this, msg, Toast.LENGTH_SHORT));
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, "register error, msg = " + e.getMessage());
                runOnUiThread(() -> ToastUtils.showToast(RegisterActivity.this, "注册异常, " + e.getMessage(), Toast.LENGTH_SHORT));
            }
        }).start();
    }
}

