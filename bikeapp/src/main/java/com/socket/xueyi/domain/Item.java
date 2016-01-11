package com.socket.xueyi.domain;

/**
 * Created by XUEYI on 2015/12/1.
 */
public class Item {
    private String IconName;
    private int IconId;
    public Item(int IconId, String IconName) {
        this.IconId = IconId;
        this.IconName = IconName;
    }

    public int getIconId() {
        return IconId;
    }

    public void setIconId(int iconid) {
        IconId = iconid;
    }

    public String getIconName() {
        return IconName;
    }

    public void setIconName(String iconName) {
        IconName = iconName;
    }
}
