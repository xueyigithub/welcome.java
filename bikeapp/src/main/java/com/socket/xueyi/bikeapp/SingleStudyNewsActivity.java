package com.socket.xueyi.bikeapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.socket.xueyi.common.DateDeserializer;
import com.socket.xueyi.common.DateSerializer;
import com.socket.xueyi.config.Consts;
import com.socket.xueyi.domain.StudyGuide;
import com.socket.xueyi.webtool.HttpCallBack;
import com.socket.xueyi.webtool.WebService;

import java.text.DateFormat;
import java.util.Date;

public class SingleStudyNewsActivity extends AppCompatActivity {
    private String id;
    private StudyGuide datas;
    private TextView response_text;
    private Toolbar mToolbar;
    private String data;
    private ImageView iv_newsImage;

    private static final int ON_SUCCESS = 1;
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ON_SUCCESS:
                    response_text.setText(datas.getContent());
                    setTitle(datas.getTitle());

                    break;
                default:
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_study);

        initToolbar();
        Intent intent=this.getIntent();
        id=intent.getStringExtra("nid");
        response_text=(TextView)findViewById(R.id.response_text);
        //initNewsDate();
        String path= Consts.URLFINDSTUDY;

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
    private void parseJSONWithGSON(String jsonData){
        Gson gson=new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .registerTypeAdapter(Date.class, new DateSerializer()).setDateFormat(DateFormat.LONG)
                .registerTypeAdapter(Date.class, new DateDeserializer()).setDateFormat(DateFormat.LONG)
                .create();
        datas=gson.fromJson(jsonData, StudyGuide.class);
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
        SingleResData datas=null;
        @Override
        protected SingleResData doInBackground(String... params) {
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
            datas=gson.fromJson(result, SingleResData.class);
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
