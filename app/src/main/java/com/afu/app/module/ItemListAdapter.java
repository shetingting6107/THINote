package com.afu.app.module;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.afu.app.Constant;
import com.afu.app.R;
import com.afu.app.activity.CreateItemListActivity;
import com.afu.app.utls.DateUtils;

import java.util.List;

public class ItemListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<Item> itemList;

    public ItemListAdapter(Context context) {
        this.mContext = context;
    }

    public void setItemList(List<Item> items) {
        this.itemList = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == Item.ITEM_NORMAL) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_show_item, parent, false);
            return new ItemViewHolder(view);
        }else if (viewType == Item.ITEM_ADD) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_add_item, parent, false);
            return new AddItemViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == Item.ITEM_NORMAL) {
            ItemViewHolder itemViewHolder = (ItemViewHolder)holder;
            Item item = itemList.get(position);
            itemViewHolder.mTvItemName.setText(item.getItemName());
//            itemViewHolder.mTvExportTime.setText(DateUtils.transTimeStampToDate(item.getExportTime()));
//            itemViewHolder.mTvNoticeTime.setText(DateUtils.transTimeStampToDate(item.getNoticeTime()));
        }else if (getItemViewType(position) == Item.ITEM_ADD) {
            holder.itemView.setOnClickListener(v -> {
                //添加物品详情页
                Intent intent = new Intent(Constant.ACTION_CREATE_ITEM_DETAIL);
                intent.setPackage(mContext.getPackageName());
                ((Activity)mContext).startActivityForResult(intent, CreateItemListActivity.REQUEST_CODE_ADD);
            });
        }
    }

    @Override
    public int getItemCount() {
        return itemList != null ? itemList.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        int type = Item.ITEM_NORMAL;
        if (itemList != null) {
            Item item = itemList.get(position);
            type = item.getItemType();
        }
        return type;
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        public TextView mTvItemName;
        public ImageView mIvItemImage;
        public TextView mTvExportTime;
        public TextView mTvNoticeTime;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            mTvItemName = itemView.findViewById(R.id.tv_item_name);
            mIvItemImage = itemView.findViewById(R.id.iv_item_image);
            mTvExportTime = itemView.findViewById(R.id.tv_export_time);
            mTvNoticeTime = itemView.findViewById(R.id.tv_notice_time);
        }
    }

    public static class AddItemViewHolder extends RecyclerView.ViewHolder {

        public AddItemViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
