package com.socket.xueyi.bikeapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.socket.xueyi.base.BaseActivity;
import com.squareup.picasso.Picasso;

public  class JumpMediaActivity extends BaseActivity {
private  ImageView tz_picture;
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_jump_media);
    tz_picture=(ImageView)findViewById(R.id.tz_picture);
    Picasso.with(this)
            .load("http://i3.sinaimg.cn/hs/2011/1109/U4301P920DT20111109122008.jpg")
            .into(tz_picture);
    setLogging();
    }

public void setLogging(){
    SharedPreferences pref=getSharedPreferences("info", JumpMediaActivity.MODE_PRIVATE);
    boolean logging=pref.getBoolean("logging", true);
    String numb=pref.getString("numb","");
    Log.d("JumpMediaActivity","打印出用户名" + numb);
    if(logging){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    startActivity(new Intent(JumpMediaActivity.this, HomePageActivity.class));
                    finish();
                }catch(Exception e){
                }
            }
        }).start();
    }else {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    startActivity(new Intent(JumpMediaActivity.this, MainActivity.class));
                    finish();
                }catch(Exception e){
                }
            }
        }).start();
    }
  }
}
