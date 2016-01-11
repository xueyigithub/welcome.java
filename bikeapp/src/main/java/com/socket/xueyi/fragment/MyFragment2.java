package com.socket.xueyi.fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.socket.xueyi.bikeapp.R;
import com.socket.xueyi.domain.Item;
import com.socket.xueyi.tool.MyAdapter;

import java.util.ArrayList;


public class MyFragment2 extends Fragment {
   /* private ArrayList<Item> menuLists;
    private MyAdapter<Item> myAdapter = null;
    private ListView lv_fg2List;*/
   private WebView webView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_fragment2, container, false);
        /*lv_fg2List = (ListView) view.findViewById(R.id.lv_fg2List);
        menuLists = new ArrayList<Item>();
        menuLists.add(new Item(R.mipmap.iv_icon_7, "秦雨辰"));
        menuLists.add(new Item(R.mipmap.iv_icon_7, "刘柠"));
        menuLists.add(new Item(R.mipmap.iv_icon_7, "方飞"));
        menuLists.add(new Item(R.mipmap.iv_icon_7, "薛艺"));
        myAdapter = new MyAdapter<Item>(menuLists,R.layout.fg2_item_list) {
            @Override
            public void bindView(ViewHolder holder, Item obj) {
                holder.setImageResource(R.id.img_icon,obj.getIconId());
                holder.setText(R.id.txt_icon, obj.getIconName());
            }
        };
        lv_fg2List.setAdapter(myAdapter);
        lv_fg2List.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });*/

        webView=(WebView) view.findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("http://1-blog.com/weather");
        return view;
    }
}
