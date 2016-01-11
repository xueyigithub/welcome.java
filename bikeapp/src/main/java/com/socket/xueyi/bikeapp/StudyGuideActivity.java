package com.socket.xueyi.bikeapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
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
import com.socket.xueyi.common.DateDeserializer;
import com.socket.xueyi.common.DateSerializer;
import com.socket.xueyi.config.Consts;
import com.socket.xueyi.domain.ResponseStudyData;
import com.socket.xueyi.domain.StudyGuide;
import com.socket.xueyi.layouttool.ItemDivider;
import com.socket.xueyi.tool.MyAdapterRecyStudyGuide;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StudyGuideActivity extends AppCompatActivity {

    private String name;
    @ViewInject(id=R.id.mRecyclerView)RecyclerView mRecyclerView;
    @ViewInject(id=R.id.swipyrefreshlayout)SwipyRefreshLayout mSwipyRefreshLayout;
    private MyAdapterRecyStudyGuide adapter;
    
    private List<StudyGuide> studyGuides=new ArrayList<StudyGuide>();
    private Integer page = 0;


    String path= Consts.URLSTUDY;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        FinalActivity.initInjectedView(this);
        Intent intent = this.getIntent();
        name = intent.getStringExtra("name");

        initToolbar();
        initStudyGuide();
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

        adapter = new MyAdapterRecyStudyGuide(studyGuides,StudyGuideActivity.this);
        adapter.notifyDataSetChanged();
        //对recycleview事件进行监听
        adapter.setOnItemClickLitener(new MyAdapterRecyStudyGuide.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                String title = studyGuides.get(position).getTitle();
                String nid = studyGuides.get(position).getId();
                singleLifeNews(title, nid);
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
                    initStudyGuide();
                } else {
                    new GetMoreStudyTask().execute(path);
                }
            }
        });
    }


    protected void singleLifeNews(String title,String nid){
        Intent intent=new Intent(this,SingleStudyNewsActivity.class);
        intent.putExtra("title",title);
        intent.putExtra("nid", nid);
        startActivity(intent);
    }

    private void initStudyGuide(){
        new GetStudyTask().execute(path);
    }



    class GetStudyTask extends AsyncTask<String, Void, List<StudyGuide>> {

        List<StudyGuide> data = null;

        @Override
        protected List<StudyGuide> doInBackground(String... params) {
            String result = null;
            try {
                OkHttpClient client = new OkHttpClient();
                FormEncodingBuilder builder=new FormEncodingBuilder();
                builder.add("sort", "originalTime,Desc");
                builder.add("page", page.toString());
                Request request = new Request.Builder()
                        .url(params[0])
                        .post(builder.build())
                        .build();
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
            ResponseStudyData response = gson.fromJson(result,ResponseStudyData.class);
            if(response!=null) {
                data = response.getContent();
            }
            return data;
        }

        private void stopRefresh() {
            mSwipyRefreshLayout.setRefreshing(false);
        }

        @Override
        protected void onPostExecute(List<StudyGuide> data) {
            studyGuides = data;
            page++;

            //  adapter.notifyDataSetChanged();
            initVertical();

            stopRefresh();
        }
    }


    class GetMoreStudyTask extends AsyncTask<String, Void,  List<StudyGuide>> {

        List<StudyGuide> data = null;

        @Override
        protected  List<StudyGuide> doInBackground(String... params) {
            String result = null;
            try {
                OkHttpClient client = new OkHttpClient();
                FormEncodingBuilder builder=new FormEncodingBuilder();

                builder.add("sort", "originalTime,Desc");

                builder.add("page", page.toString());
                Request request = new Request.Builder()
                        .url(params[0])
                        .post(builder.build())
                        .build();
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
            ResponseStudyData response = gson.fromJson(result, ResponseStudyData.class);
            if(response!=null) {
                data = response.getContent();
            }
            return data;
        }

        @Override
        protected void onPostExecute(List<StudyGuide> data) {
            studyGuides.addAll(data);
            page++;
            adapter.notifyDataSetChanged();
            stopRefresh();
        }
    }

    private void stopRefresh() {
        mSwipyRefreshLayout.setRefreshing(false);
    }

    private void initToolbar() {
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
        getMenuInflater().inflate(R.menu.menu_play, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:

            return true;
            case R.id.action_about:
                Toast.makeText(this, "你点了关于", Toast.LENGTH_LONG).show();

            default:
                break;
        }

        return true;
    }
}
