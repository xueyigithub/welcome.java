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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;
import com.socket.xueyi.common.DateDeserializer;
import com.socket.xueyi.common.DateSerializer;
import com.socket.xueyi.config.Consts;
import com.socket.xueyi.domain.PlayGuide;
import com.socket.xueyi.domain.ResponsePlayData;
import com.socket.xueyi.dto.FormEncodingBuilderDto;
import com.socket.xueyi.enums.CityEnum;
import com.socket.xueyi.layouttool.ItemDivider;
import com.socket.xueyi.tool.MyAdapterRecyPlayGuide;
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


public class PlayGuideActivity extends AppCompatActivity {


    private String name;
    @ViewInject(id=R.id.mRecyclerView)RecyclerView mRecyclerView;
    @ViewInject(id=R.id.swipyrefreshlayout)SwipyRefreshLayout mSwipyRefreshLayout;
    private MyAdapterRecyPlayGuide adapter;

    private List<PlayGuide> playGuides=new ArrayList<PlayGuide>();
    private Integer page = 0;
    private String city=CityEnum.Jinhua.toString();

    private  String path= Consts.URLPLAY;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        FinalActivity.initInjectedView(this);
        Intent intent = this.getIntent();
        name = intent.getStringExtra("name");

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

        adapter = new MyAdapterRecyPlayGuide(playGuides,PlayGuideActivity.this);

        adapter.notifyDataSetChanged();
        //对recycleview事件进行监听
        adapter.setOnItemClickLitener(new MyAdapterRecyPlayGuide.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                String title = playGuides.get(position).getTitle();
                String content = playGuides.get(position).getContent();
                singleLifeNews(title, content);
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
                    initPlayGuide();
                } else {
                    new GetMorePlayTask().execute(path);
                }
            }
        });
    }


    protected void singleLifeNews(String title,String content){
        Intent intent=new Intent(this,SinglePlayNewsActivity.class);
        intent.putExtra("title",title);
        intent.putExtra("content", content);
        startActivity(intent);
    }

    private void initPlayGuide(){
        new GetPlayTask().execute(path);
    }



    class GetPlayTask extends AsyncTask<String, Void, List<PlayGuide>> {

        List<PlayGuide> data = null;

        @Override
        protected List<PlayGuide> doInBackground(String... params) {
            String result = null;
            try {
                OkHttpClient client = new OkHttpClient();
                FormEncodingBuilder builder = new FormEncodingBuilder();

                builder.add("city", city);
                builder.add("sort", "baiduPage,Asc");
                builder.add("sort", "id,Asc");
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
            ResponsePlayData response = gson.fromJson(result, ResponsePlayData.class);
            if (response != null) {
                data = response.getContent();
            }
            return data;
        }

        private void stopRefresh() {
            mSwipyRefreshLayout.setRefreshing(false);
        }

        @Override
        protected void onPostExecute(List<PlayGuide> data) {
            playGuides = data;
            page++;

            //adapter.notifyDataSetChanged();
            initVertical();

            stopRefresh();

        }
    }


        private String constructBuilder(List<FormEncodingBuilderDto> data,String param){

            try {

                OkHttpClient client = new OkHttpClient();

                FormEncodingBuilder builder=new FormEncodingBuilder();

               for(FormEncodingBuilderDto form:data){
                   builder.add(form.getKey(),form.getValue());
               }

                Request request = new Request.Builder()
                        .url(param)
                        .post(builder.build())
                        .build();
                Response response = client.newCall(request).execute();
                //result是传回来的数据
               return  response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }




    class GetMorePlayTask extends AsyncTask<String, Void,  List<PlayGuide>> {

        List<PlayGuide> data = null;

        @Override
        protected  List<PlayGuide> doInBackground(String... params) {


            List<FormEncodingBuilderDto> form=new ArrayList<>();
            form.add(new FormEncodingBuilderDto("city",city));
            form.add(new FormEncodingBuilderDto("sort", "baiduPage,Asc"));
            form.add(new FormEncodingBuilderDto("sort", "id,Asc"));
            form.add(new FormEncodingBuilderDto("page", page.toString()));

            String result= constructBuilder(form,params[0]);

           /* try {

                OkHttpClient client = new OkHttpClient();
                FormEncodingBuilder builder=new FormEncodingBuilder();


                builder.add("city",city);
                builder.add("sort", "baiduPage,Asc");
                builder.add("sort", "id,Asc");
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
            }*/
            Gson gson = new GsonBuilder()
                    .excludeFieldsWithoutExposeAnnotation()
                    .registerTypeAdapter(Date.class, new DateSerializer()).setDateFormat(DateFormat.LONG)
                    .registerTypeAdapter(Date.class, new DateDeserializer()).setDateFormat(DateFormat.LONG)
                    .create();
            ResponsePlayData response = gson.fromJson(result, ResponsePlayData.class);
            if(response!=null) {
                data = response.getContent();
            }
            return data;
        }

        @Override
        protected void onPostExecute(List<PlayGuide> data) {
            playGuides.addAll(data);
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

    private boolean changCity(CityEnum myCity){
        page=0;
        city=myCity.toString();
        new GetPlayTask().execute(path);
        adapter.notifyDataSetChanged();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_Jinhua:
               changCity(CityEnum.Jinhua);
                break;
            case R.id.action_Hangzhou:
                changCity(CityEnum.Hangzhou);
                break;
            case R.id.action_Ningbo:
                changCity(CityEnum.Ningbo);
                break;
            case R.id.action_Huzhou:
                changCity(CityEnum.Huzhou);
                break;
            case R.id.action_Jiaxing:
                changCity(CityEnum.Jiaxing);
                break;
            case R.id.action_Lishui:
                changCity(CityEnum.Lishui);
                break;
            case R.id.action_Quzhou:
                changCity(CityEnum.Quzhou);
                break;
            case R.id.action_Shaoxing:
                changCity(CityEnum.Shaoxing);
                break;
            case R.id.action_Taizhou:
                changCity(CityEnum.Taizhou);
                break;
            case R.id.action_Wenzhou:
                changCity(CityEnum.Wenzhou);
                break;
            default:
                break;
        }

        return true;
    }
}