package com.afu.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.afu.app.Constant;
import com.afu.app.R;
import com.afu.app.client.OkhttpPostRequest;
import com.afu.app.client.OkhttpResponse;
import com.afu.app.module.Item;
import com.afu.app.module.ItemListAdapter;
import com.afu.app.module.LoginResponse;
import com.afu.app.utls.DateUtils;
import com.afu.app.utls.SPUtils;
import com.afu.app.utls.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 创建事物列表页
 * 展示添加的事物列表，以及添加按钮
 */
public class CreateItemListActivity extends Activity {

    public static final String TAG = "CreateItemListActivity";

    private TextView mTvBack;
    private EditText mEtSearch;
    private RecyclerView mRvItemList;

    private List<Item> itemList;
    private List<Item> resultList;
    private ItemListAdapter mAdapter;

    public static int REQUEST_CODE_ADD = 100;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_create_item_list);
        initView();
        getUserAllItem();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_ADD && resultCode == RESULT_OK) {
            getUserAllItem();
        }
    }

    private void initView() {
        mTvBack = findViewById(R.id.tv_back);
        mEtSearch = findViewById(R.id.et_search);
        mEtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String input = s.toString();
                search(input);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mRvItemList = findViewById(R.id.rv_item_list);
        mAdapter = new ItemListAdapter(this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRvItemList.setLayoutManager(manager);
        mRvItemList.setAdapter(mAdapter);
        updateList();
        mTvBack.setOnClickListener(v -> onBackPressed());
    }

    private void search(String input) {
        resultList = new ArrayList<>();
        if (itemList != null && !itemList.isEmpty()) {
            for (Item item : itemList) {
                if (item.getItemName().contains(input)) {
                    resultList.add(item);
                }
            }
        }
        updateList();
    }

    private void updateList() {
        if (resultList == null) {
            resultList = new ArrayList<>();
        }
        if (isAllItem(resultList)) {
            Item addItem = new Item();
            addItem.setItemType(Item.ITEM_ADD);
            resultList.add(addItem);
        }
        if (mAdapter != null) {
            mAdapter.setItemList(resultList);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void getUserAllItem() {
        new Thread(() -> {
            try {
                String url = Constant.BASE_URL + "/thiNote_api/item_search";
                HashMap<String, Object> param = new HashMap<>();
                String userId = SPUtils.getString(this, Constant.SP_USER_ID);
                param.put("userId", userId);
                JSONObject json = new JSONObject(param);
                String response = OkhttpPostRequest.post(url, json.toString());
                Log.d(TAG, "getUserAllItem response = " + response);
                Gson gson = new Gson();
                Type type = new TypeToken<OkhttpResponse<List<Item>>>(){}.getType();
                OkhttpResponse<List<Item>> res = gson.fromJson(response, type);
                if (res != null && res.getCode() == Constant.HTTP_SUCCESS) {
                    runOnUiThread(() -> {
                        itemList = res.getData();
                        resultList = new ArrayList<>(itemList);
                        updateList();
                    });
                }else {
                    runOnUiThread(() -> ToastUtils.showToast(this, res != null ? res.getMsg() : "查询物品异常！", Toast.LENGTH_SHORT));
                }
            } catch (Exception e) {
                Log.e(TAG, "login error, msg = " + e.getMessage());
                runOnUiThread(() -> ToastUtils.showToast(this, "查询物品异常！message = " + e.getMessage(), Toast.LENGTH_SHORT));
                e.printStackTrace();
            }
        }).start();
    }

    private boolean isAllItem(List<Item> items) {
        boolean result = true;
        if (!items.isEmpty()) {
            Item item = items.get(items.size() - 1);
            if (item != null && item.getItemType() == Item.ITEM_ADD) {
                return false;
            }
        }
        return result;
    }
}
