package com.socket.xueyi.bikeapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;
import com.socket.xueyi.base.BaseActivity;
import com.socket.xueyi.common.DateDeserializer;
import com.socket.xueyi.common.DateSerializer;
import com.socket.xueyi.config.Consts;
import com.socket.xueyi.domain.LifeGuide;
import com.socket.xueyi.domain.ResponseLifeData;
import com.socket.xueyi.layouttool.ItemDivider;
import com.socket.xueyi.tool.MyAdapterRecyLifeGuide;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.CookieHandler;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LifeGuideActivity extends BaseActivity {
    private RecyclerView  mRecyclerView;
    private SwipyRefreshLayout mSwipyRefreshLayout;
    private MyAdapterRecyLifeGuide adapter;
    private List<LifeGuide>  lifeGuides=new ArrayList<LifeGuide>();
    private String path;
    private String name;
    private String findPath;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life);
        mRecyclerView=(RecyclerView)findViewById(R.id. mRecyclerView);
        mSwipyRefreshLayout = (SwipyRefreshLayout) findViewById(R.id.swipyrefreshlayout);

        Intent intent=getIntent();

        path=intent.getStringExtra("path");
        name=intent.getStringExtra("name");

       /* 获取listLifeGuide
       public static final String URLLIFE="http://10.7.90.201:8080/bike/lifeGuide/listLifeGuide";

        if (path.indexOf("/") > -1) {
            firstName = firstName.substring(firstName.lastIndexOf("/") + 1);
        }*/

        if(name.equals("生活指南")){
            findPath=Consts.URLFINDLIFE;
        }else if(name.equals("学习指南")){
            findPath=Consts.URLFINDSTUDY;
        }

        initToolbar();
        initPlayGuide();
        initSwipyRefresh();
        initVertical();
    }
    private void initVertical(){
        // 创建一个线性布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        // 设置布局管理器
        mRecyclerView.setLayoutManager(layoutManager);
        //增加RecyclerView的分界线
        mRecyclerView.addItemDecoration(new ItemDivider(this, layoutManager.getOrientation()));

        adapter = new MyAdapterRecyLifeGuide(lifeGuides,LifeGuideActivity.this);
        adapter.notifyDataSetChanged();
        //对recycleview事件进行监听
        adapter.setOnItemClickLitener(new MyAdapterRecyLifeGuide.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                String nid = lifeGuides.get(position).getId();
                singleLifeNews(nid,findPath);
            }
        });
        // 设置Adapter
        mRecyclerView.setAdapter(adapter);
    }

    public void initSwipyRefresh(){
        mSwipyRefreshLayout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection swipyRefreshLayoutDirection) {
                if (swipyRefreshLayoutDirection == SwipyRefreshLayoutDirection.TOP) {

                }
            }
        });
    }


    protected void singleLifeNews(String nid,String findPath){
        Intent intent=new Intent(this,SingleLifeNewsActivity.class);
        intent.putExtra("nid", nid);
        intent.putExtra("findPath", findPath);

        startActivity(intent);
    }



    private void initPlayGuide(){
        new GetLifeTask().execute(path);
    }

    class GetLifeTask extends AsyncTask<String, Void, List<LifeGuide>> {

        List<LifeGuide> data = null;

        @Override
        protected List<LifeGuide> doInBackground(String... params) {
            String result = null;
            try {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(params[0])
                        .build();

                CookieHandler cookieHandler = client.getCookieHandler();


                Response response = client.newCall(request).execute();

                //result是传回来的数据
                result = response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Gson gson = new GsonBuilder()
                    .excludeFieldsWithoutExposeAnnotation()
                    .registerTypeAdapter(Date.class, new DateSerializer()).setDateFormat(DateFormat.LONG)
                    .registerTypeAdapter(Date.class, new DateDeserializer()).setDateFormat(DateFormat.LONG)
                    .create();
            ResponseLifeData response = gson.fromJson(result, ResponseLifeData.class);
            if(response!=null) {
                data = response.getContent();
            }
            return data;
        }
        private void stopRefresh() {

            mSwipyRefreshLayout.setRefreshing(false);
        }

        @Override
        protected void onPostExecute(List<LifeGuide> data) {
            lifeGuides = data;
            initVertical();
            stopRefresh();
        }
    }


    private void initToolbar(){
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loadBackdrop();
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(name);
    }

    private void loadBackdrop() {
        final ImageView imageView = (ImageView) findViewById(R.id.backdrop);
        Picasso.with(this)
                .load(R.drawable.cheese_1)
                .into(imageView);
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
