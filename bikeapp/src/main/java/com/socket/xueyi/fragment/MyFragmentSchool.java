package com.socket.xueyi.fragment;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;
import com.socket.xueyi.base.CFinal;
import com.socket.xueyi.bikeapp.R;
import com.socket.xueyi.bikeapp.SingleNewsActivity;
import com.socket.xueyi.common.DateDeserializer;
import com.socket.xueyi.common.DateSerializer;
import com.socket.xueyi.config.Consts;
import com.socket.xueyi.domain.CampusNews;
import com.socket.xueyi.domain.ResponseData;
import com.socket.xueyi.enums.NewsTypeEnum;
import com.socket.xueyi.layouttool.ItemDivider;
import com.socket.xueyi.tool.MyAdapterRecy;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import net.tsz.afinal.FinalDb;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;


public class MyFragmentSchool extends Fragment {
    private ListView lv_newsList;
    private MyAdapterRecy adapter;
    private List<CampusNews> campusNewsData = new ArrayList<CampusNews>();
    private  RecyclerView mRecyclerView;
    private SwipyRefreshLayout mSwipyRefreshLayout;
    private Integer page = 0;
    private Context mContext;
    private String string;
    String path= Consts.URLNEWS;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schoolnews,container,false);
        mRecyclerView = (RecyclerView)view.findViewById(R.id.recyclerview_vertical);
        mContext=this.getActivity();
        mSwipyRefreshLayout = (SwipyRefreshLayout) view.findViewById(R.id.swipyrefreshlayout);
        //初始化数据库打开的帮助类

       //获取到MyFragment里面的信息
        string=getArguments().getString("newsType");
        // 设置下拉刷新监听事件
        initSwipyRefresh();
        initVertical();
        //发送路径
        initNewsData();
        return view;
    }

    /**
     * 添加
     * @param
     */

   public void initSwipyRefresh(){
       mSwipyRefreshLayout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
           @Override
           public void onRefresh(SwipyRefreshLayoutDirection swipyRefreshLayoutDirection) {
               if (swipyRefreshLayoutDirection == SwipyRefreshLayoutDirection.TOP) {
                   initNewsData();
               } else {
                   new GetMoreNewsTask().execute(path);
               }
           }
       });
   }


    public void initNewsData(){
        new GetNewsTask().execute(path);
    }


    private void initVertical(){
        // 创建一个线性布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        // 设置布局管理器
        mRecyclerView.setLayoutManager(layoutManager);
        //增加RecyclerView的分界线
        mRecyclerView.addItemDecoration(new ItemDivider(getActivity(), layoutManager.getOrientation()));

        Log.e("aaa", "" + campusNewsData.size());
        adapter = new MyAdapterRecy(campusNewsData, getActivity());
        adapter.notifyDataSetChanged();
       //对recycleview事件进行监听
        adapter.setOnItemClickLitener(new MyAdapterRecy.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position)
            {
                String nid=campusNewsData.get(position).getId();
                singleCampusNews(nid);
            }
        });
        // 设置Adapter
        mRecyclerView.setAdapter(adapter);
    }

    protected void singleCampusNews(String nid){
        Intent intent=new Intent(getActivity(),SingleNewsActivity.class);
        intent.putExtra("nid", nid);
        startActivity(intent);
    }

    private void stopRefresh() {
        mSwipyRefreshLayout.setRefreshing(false);
    }

    /**
     * 获取新闻异步任务类
     *
     */
    class GetNewsTask extends AsyncTask<String, Void, List<CampusNews>> {

        List<CampusNews> data = new ArrayList<>();

        @Override
        protected List<CampusNews> doInBackground(String... params) {
            String result = null;

            try {
                OkHttpClient client = new OkHttpClient();
                FormEncodingBuilder builder=new FormEncodingBuilder();
                builder.add("newsType",string);
                builder.add("sort", "border,Desc");
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
            ResponseData res = gson.fromJson(result, ResponseData.class);
            if(res!=null) {
                data = res.getContent();
            }
            return data;
        }

        private  void sqlLite(){
        FinalDb mFinalDb=CFinal.getCFinal().getFinalDb(mContext);
        mFinalDb.save(data);
        }

        @Override
        protected void onPostExecute(List<CampusNews> data) {
            campusNewsData = data;
            page++;

          //  adapter.notifyDataSetChanged();
            initVertical();

            stopRefresh();
        }
    }
    /**
     *加载新闻异步任务类
     *
     */
    class GetMoreNewsTask extends AsyncTask<String, Void, List<CampusNews>> {

        List<CampusNews> data = null;

        @Override
        protected List<CampusNews> doInBackground(String... params) {
            String result = null;

            try {
                OkHttpClient client = new OkHttpClient();
                FormEncodingBuilder builderr=new FormEncodingBuilder();
                builderr.add("newsType", string);
                builderr.add("sort", "border,Desc");
                builderr.add("page", page.toString());
                Request request = new Request.Builder()
                        .url(params[0])
                        .post(builderr.build())
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
            ResponseData res = gson.fromJson(result, ResponseData.class);
            data = res.getContent();
            return data;
        }

        @Override
        protected void onPostExecute(List<CampusNews> data) {
            campusNewsData.addAll(data);
            page++;
            adapter.notifyDataSetChanged();
            stopRefresh();
        }
    }
}
