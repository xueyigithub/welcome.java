package com.socket.xueyi.fragment;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;
import com.socket.xueyi.bikeapp.R;
import com.socket.xueyi.bikeapp.SinglePlayNewsActivity;
import com.socket.xueyi.common.DateDeserializer;
import com.socket.xueyi.common.DateSerializer;
import com.socket.xueyi.config.Consts;
import com.socket.xueyi.domain.PlayGuide;
import com.socket.xueyi.domain.ResponsePlayData;
import com.socket.xueyi.enums.CityEnum;
import com.socket.xueyi.layouttool.ItemDivider;
import com.socket.xueyi.tool.MyAdapterRecyPlayGuide;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by XUEYI on 2015/12/20.
 */
public class MyFragmentPlay extends Fragment{

    @ViewInject(id=R.id.mRecyclerView)RecyclerView mRecyclerView;
    @ViewInject(id=R.id.swipyrefreshlayout)SwipyRefreshLayout mSwipyRefreshLayout;
    private MyAdapterRecyPlayGuide adapter;
    private List<PlayGuide> playGuides=new ArrayList<PlayGuide>();
    private Integer page = 0;
    private String city;

    String path= Consts.URLPLAY;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_play_fragment,container,false);

        FinalActivity.initInjectedView(this,view);

        city=getArguments().getString("city");

        initSwipyRefresh();
        initVertical();
        initPlayGuide();
        return view;
    }


    private void initVertical(){
        // 创建一个线性布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        // 设置布局管理器
        mRecyclerView.setLayoutManager(layoutManager);
        //增加RecyclerView的分界线
        mRecyclerView.addItemDecoration(new ItemDivider(getActivity(), layoutManager.getOrientation()));

        adapter = new MyAdapterRecyPlayGuide(playGuides,getActivity());
        adapter.notifyDataSetChanged();
        //对recycleview事件进行监听
        adapter.setOnItemClickLitener(new MyAdapterRecyPlayGuide.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                String nid = playGuides.get(position).getId();
                String content = playGuides.get(position).getContent();
                singleLifeNews(nid, content);
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


    protected void singleLifeNews(String nid,String content){
        Intent intent=new Intent(getActivity(),SinglePlayNewsActivity.class);
        intent.putExtra("nid", nid);
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
                FormEncodingBuilder builder=new FormEncodingBuilder();
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
            ResponsePlayData response = gson.fromJson(result,ResponsePlayData.class);
            if(response!=null) {
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

            //  adapter.notifyDataSetChanged();
            initVertical();

            stopRefresh();
        }
    }


    class GetMorePlayTask extends AsyncTask<String, Void,  List<PlayGuide>> {

        List<PlayGuide> data = null;

        @Override
        protected  List<PlayGuide> doInBackground(String... params) {
            String result = null;
            try {
                OkHttpClient client = new OkHttpClient();
                FormEncodingBuilder builder=new FormEncodingBuilder();
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


}
