package com.socket.xueyi.bikeapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

public class SinglePlayNewsActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private String content;
    private String title;
    @ViewInject(id=R.id.web_view)WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_play);
        FinalActivity.initInjectedView(this);

        initToolbar();

        Intent intent=this.getIntent();
        content=intent.getStringExtra("content");
        title=intent.getStringExtra("title");

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(content);

        setTitle(title);
    }

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_about:
                Toast.makeText(this, "你点了关于", Toast.LENGTH_LONG).show();
            default:
                break;
        }
        return true;
    }
}
