package com.socket.xueyi.webtool;

public interface HttpCallBack {
    void onFinish(String res,String data);
    void onError(Exception ex);
}
