package com.socket.xueyi.domain;

/**
 * Created by XUEYI on 2015/11/30.
 * iId：GridView 图片
 * iName 名字
 */
public class Icon {
    private int iId;
    private String iName;
   /* public Icon(int iId, String iName) {
        this.iId = iId;
        this.iName = iName;
    }*/

    public int getiId() {
        return iId;
    }

    public void setiId(int iId) {
        this.iId = iId;
    }

    public String getiName() {
        return iName;
    }

    public void setiName(String iName) {
        this.iName = iName;
    }
}
