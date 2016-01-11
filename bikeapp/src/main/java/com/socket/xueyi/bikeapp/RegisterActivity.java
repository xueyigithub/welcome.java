package com.socket.xueyi.bikeapp;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.socket.xueyi.base.BaseActivity;
import com.socket.xueyi.config.Consts;
import com.socket.xueyi.msg.ShowMsg;
import com.socket.xueyi.webtool.HttpCallBack;
import com.socket.xueyi.webtool.WebService;

import org.json.JSONObject;


/**
 * 用户名et_renumb，密码et_repwd
 */

public class RegisterActivity extends BaseActivity {
    private EditText et_renumb;
    private EditText et_repwd;
    private Toolbar mToolbar;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    ShowMsg.showMsg(RegisterActivity.this, "注册成功了");
                case 2:
                    ShowMsg.showMsg(RegisterActivity.this, "注册失败了");
                case 3:
                    ShowMsg.showMsg(RegisterActivity.this, "网络连接错误");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        et_renumb = (EditText) findViewById(R.id.et_renumb);
        et_repwd = (EditText) findViewById(R.id.et_repwd);
        initToolbar();
    }

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("注册");
        setSupportActionBar(mToolbar);
        //加入了箭头指标
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_about:
                Toast.makeText(this, "你点了关于", Toast.LENGTH_LONG).show();
            default:
                break;
        }
        return true;
    }

    public void register(View v) {
        String renumb = et_renumb.getText().toString().trim();
        String repwd = et_repwd.getText().toString().trim();
        if (TextUtils.isEmpty(renumb) || TextUtils.isEmpty(repwd)) {
            ShowMsg.showMsg(this, "输入内容不能为空");
            return;
        }
        String path = Consts.URL + "/bike/user/register";
        String data = "userName=" + renumb + "&password=" + repwd;
        WebService.post(path, data, new HttpCallBack() {
            @Override
            public void onFinish(String res, String data) {
                parseJSONWithJSONObject(res);
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

    private void parseJSONWithJSONObject(String jsonData) {
        try {
            JSONObject responseBody = new JSONObject(jsonData);
            boolean success = responseBody.getBoolean("success");
            Message msg = Message.obtain();
            if (success) {
                msg.what = Consts.SUCCESS;
            } else {
                msg.what = Consts.ERROR;
            }
            handler.sendMessage(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
