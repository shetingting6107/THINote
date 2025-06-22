package com.afu.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.afu.app.R;
import com.afu.app.module.Item;
import com.afu.app.module.ItemListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建事物列表页
 * 展示添加的事物列表，以及添加按钮
 */
public class CreateItemListActivity extends Activity {

    private TextView mTvBack;
    private RecyclerView mRvItemList;

    private List<Item> itemList;
    private ItemListAdapter mAdapter;

    public static int REQUEST_CODE_ADD = 100;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_create_item_list);
        initView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_ADD && resultCode == RESULT_OK) {
            String name = data.getStringExtra("name");
            Item item = new Item();
            item.setItemName(name);
            if (itemList == null) {
                itemList = new ArrayList<>();
            }
            itemList.add(0, item);
            updateList();
        }
    }

    private void initView() {
        mTvBack = findViewById(R.id.tv_back);
        mRvItemList = findViewById(R.id.rv_item_list);
        mAdapter = new ItemListAdapter(this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRvItemList.setLayoutManager(manager);
        mRvItemList.setAdapter(mAdapter);
        updateList();
        mTvBack.setOnClickListener(v -> onBackPressed());
    }

    private void updateList() {
        if (itemList == null) {
            itemList = new ArrayList<>();
        }
        if (isAllItem(itemList)) {
            Item addItem = new Item();
            addItem.setItemType(Item.ITEM_ADD);
            itemList.add(addItem);
        }
        if (mAdapter != null) {
            mAdapter.setItemList(itemList);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
