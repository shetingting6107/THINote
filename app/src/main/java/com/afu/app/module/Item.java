package com.afu.app.module;

import java.io.Serializable;

public class Item implements Serializable {

    public static int ITEM_NORMAL = 0;
    public static int ITEM_ADD = 1;

    private String itemId; //物品id
    private String itemName;//物品名称
    private String itemImageUrl;//物品图片地址
    private Long itemExpireTime;//物品到期时间
    private Long itemNoticeTime;//物品到期提醒时间

    private int itemType = ITEM_NORMAL; //item类型，用于前端显示

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getImageUrl() {
        return itemImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.itemImageUrl = imageUrl;
    }

    public Long getExportTime() {
        return itemExpireTime;
    }

    public void setExportTime(Long exportTime) {
        this.itemExpireTime = exportTime;
    }

    public Long getNoticeTime() {
        return itemNoticeTime;
    }

    public void setNoticeTime(Long noticeTime) {
        this.itemNoticeTime = noticeTime;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }
}
