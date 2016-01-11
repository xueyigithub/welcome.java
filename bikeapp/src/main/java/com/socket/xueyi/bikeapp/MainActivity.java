package com.socket.xueyi.bikeapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.socket.xueyi.base.BaseActivity;
import com.socket.xueyi.base.CFinal;
import com.socket.xueyi.config.Consts;
import com.socket.xueyi.msg.ShowMsg;
import com.socket.xueyi.webtool.HttpCallBack;
import com.socket.xueyi.webtool.WebService;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.FinalDb;
import net.tsz.afinal.annotation.view.ViewInject;

import org.json.JSONObject;


public class MainActivity extends BaseActivity {

    @ViewInject(id=R.id.et_numb)EditText et_numb;


    @ViewInject(id=R.id.et_pwd)EditText et_pwd;


    @ViewInject(id=R.id.cb_remember)CheckBox cb_remember;



    private SharedPreferences sp;
    private Toolbar mToolbar;
    // 用户id及用户名username
    private String id;
    private String username;
    private boolean logging = true;
    private boolean isExit = false;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
            switch (msg.what) {
                case 1:
                    ShowMsg.showMsg(MainActivity.this, "尊敬的" + username + "登录成功");
                    homePageActivity(username);
                    // newsActivity(id);
                    break;
                case 2:
                    et_pwd.setText("");
                    ShowMsg.showMsg(MainActivity.this, "登录失败");
                    break;
                case 3:
                    ShowMsg.showMsg(MainActivity.this, "网络连接错误");
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FinalActivity.initInjectedView(this);

        initToolbar();

        sp = this.getSharedPreferences("info", Context.MODE_PRIVATE);
        et_numb.setText(sp.getString("numb", ""));
        et_pwd.setText(sp.getString("pwd", ""));
        cb_remember.setChecked(sp.getBoolean("ischeckbox", false));
    }

    public void homePageActivity(String username) {
        SharedPreferences.Editor edit = sp.edit();
        if (cb_remember.isChecked()) {
            edit.putString("numb", et_numb.getText().toString().trim());
            edit.putString("pwd", et_pwd.getText().toString().trim());
            edit.putBoolean("ischeckbox", true);
            ShowMsg.showMsg(this, "保存密码成功");
        }

        Intent intent = new Intent(this, HomePageActivity.class);
        intent.putExtra("name",username);
        startActivity(intent);

        edit.putBoolean("logging", true);
        edit.commit();
        finish();
    }

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        ActionBar.LayoutParams lp = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        View actionBarView = LayoutInflater.from(this).inflate(R.layout.login_bar_layout, null);
        getSupportActionBar().setCustomView(actionBarView, lp);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
    }

    public void login(View v) {
        final String numb = et_numb.getText().toString().trim();
        final String pwd = et_pwd.getText().toString().trim();
        if (TextUtils.isEmpty(numb) || TextUtils.isEmpty(pwd)) {
            ShowMsg.showMsg(this, "输入内容不能为空");
            return;
        }
        String path = Consts.URL + "/bike/user/login";

        String data = "userName=" + numb + "&password=" + pwd;
        WebService.post(path, data, new HttpCallBack() {
            @Override
            public void onFinish(String res,String data) {
                parseJSONWithJSONObject(res);
                FinalDb mFinalDb= CFinal.getCFinal().getFinalDb(MainActivity.this);
                mFinalDb.save(data);
            }

            @Override
            public void onError(Exception ex) {
                Message msg = Message.obtain();
                msg.obj = ex.toString();
                msg.what = Consts.NET_ERROR;
                handler.sendMessage(msg);
            }
        });
    }

    /**
     * 确定你要不要登录
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!isExit) {
                isExit = true;
                Toast.makeText(getApplicationContext(), "再按一次退出程序",
                        Toast.LENGTH_SHORT).show();
                // 利用handler延迟发送更改状态信息
                handler.sendEmptyMessageDelayed(0, 2000);
            } else {
                finish();
                System.exit(0);
            }
        }
        return false;
    }

    public void register(View v) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    private void parseJSONWithJSONObject(String jsonData) {
        try {
            JSONObject responseBody = new JSONObject(jsonData);
            //JSONObject user = responseBody.getJSONObject("user");
            Message msg = Message.obtain();
            if (responseBody != null && !TextUtils.isEmpty(responseBody.getString("id"))) {
                msg.what = Consts.SUCCESS;
                id = responseBody.getString("id");
                username = responseBody.getString("userName");
            } else {
                msg.what = Consts.ERROR;
            }
            handler.sendMessage(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
