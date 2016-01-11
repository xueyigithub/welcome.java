package com.socket.xueyi.bikeapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.socket.xueyi.base.BaseActivity;
import com.socket.xueyi.common.DateDeserializer;
import com.socket.xueyi.common.DateSerializer;
import com.socket.xueyi.config.Consts;
import com.socket.xueyi.domain.CampusNews;
import com.socket.xueyi.domain.FileInfo;
import com.socket.xueyi.layouttool.ItemDivider;
import com.socket.xueyi.tool.MyAdapterRecySingle;
import com.socket.xueyi.webtool.HttpCallBack;
import com.socket.xueyi.webtool.WebService;
import com.squareup.okhttp.MediaType;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SingleNewsActivity extends BaseActivity {
    private String id;
    private CampusNews datas;
    private TextView response_text;
    private Toolbar mToolbar;
    private String data;
    private View view;
    private TextView summary;
    //private ImageView iv_newsImage;
    private RecyclerView mRecyclerView;
    private MyAdapterRecySingle adapter;
    private List<FileInfo> imageView=new  ArrayList<FileInfo>();


    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    private static final int ON_SUCCESS = 1;
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ON_SUCCESS:
                    response_text.setText(datas.getContent());
                    setTitle(datas.getTitle());
                    if(summary==null){
                        summary.setVisibility(View.GONE);
                        view.setVisibility(View.GONE);
                    }else {
                        summary.setText(datas.getSummary());
                    }
                    if (imageView.size() == 0) {
                        mRecyclerView.setVisibility(View.GONE);
                    }
                    initVertical();
                    break;
                default:
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_news);
        //iv_newsImage=(ImageView)findViewById(R.id.iv_newsImage);
        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerview_vertical);

        initToolbar();

        Intent intent=this.getIntent();
        id=intent.getStringExtra("nid");
        response_text=(TextView)findViewById(R.id.response_text);
        summary=(TextView)findViewById(R.id.summary);
        view=(View)findViewById(R.id.view);
        //给Textview增加滚动事件
        response_text.setMovementMethod(ScrollingMovementMethod.getInstance());
        //initNewsDate();
        /**发送请求
         *
         */
        initPost();
        initVertical();
    }
    private void initPost(){
        String path= Consts.URLFINDNEWS;
        data="id="+id;
        WebService.post(path, data, new HttpCallBack() {
            @Override
            public void onFinish(String res,String data) {
                parseJSONWithGSON(res);
                Message msg = new Message();
                msg.what = ON_SUCCESS;
                handler.sendMessage(msg);
            }

            @Override
            public void onError(Exception ex) {
                ex.printStackTrace();
            }
        });
    }
    private void initVertical(){
        // 创建一个线性布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(SingleNewsActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        // 设置布局管理器
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new ItemDivider(this, layoutManager.getOrientation()));
        adapter = new MyAdapterRecySingle(imageView,SingleNewsActivity.this);
        adapter.notifyDataSetChanged();

        // 设置Adapter
        mRecyclerView.setAdapter(adapter);
    }


    private void parseJSONWithGSON(String jsonData){
        Gson gson=new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .registerTypeAdapter(Date.class, new DateSerializer()).setDateFormat(DateFormat.LONG)
                .registerTypeAdapter(Date.class, new DateDeserializer()).setDateFormat(DateFormat.LONG)
                .create();
        datas = gson.fromJson(jsonData,CampusNews.class);
        imageView=datas.getImages();
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
       /*public void initNewsDate(){
        String path= Consts.URL+"/bike/campusNews/findCampusNews";
        data="id="+id;
        new GetNewsTask().execute(path);
    }
    异步+ok get请求
    class GetNewsTask extends AsyncTask<String,Void,SingleResData> {
       CampusNews datas=null;
        @Override
        protectedCampusNews doInBackground(String... params) {
            String result = null;
            try {
                OkHttpClient client = new OkHttpClient();
                RequestBody body = RequestBody.create(JSON, data);
                Request request = new Request.Builder()
                        .url(params[0])
                        .post(body)
                        .build();
                Response response = client.newCall(request).execute();
                result = response.body().string();
            }catch(IOException e){
                e.printStackTrace();
            }
            Gson gson=new GsonBuilder()
                    .excludeFieldsWithoutExposeAnnotation()
                    .registerTypeAdapter(Date.class, new DateSerializer()).setDateFormat(DateFormat.LONG)
                    .registerTypeAdapter(Date.class, new DateDeserializer()).setDateFormat(DateFormat.LONG)
                    .create();
            datas=gson.fromJson(result,CampusNews.class);
            return datas;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(SingleResData datas) {
            response_text.setText(datas.getContent());
            setTitle(datas.getTitle());
            Picasso.with(SingleNewsActivity.this).load(Consts.URL_IMAGE + datas.getImages().get(0).getId()).into(iv_newsImage);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }*/
}
