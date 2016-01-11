package com.socket.xueyi.tool;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.socket.xueyi.bikeapp.R;
import com.socket.xueyi.config.Consts;
import com.socket.xueyi.domain.CampusNews;
import com.socket.xueyi.domain.FileInfo;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by XUEYI on 2015/12/14.
 */
public class MyAdapterRecySingle extends RecyclerView.Adapter<MyAdapterRecySingle.ViewHolder>{


    private List<FileInfo> mDataset;
    private Context context;
    public MyAdapterRecySingle (List<FileInfo> dataset,Context context) {
        super();
        this.context=context;
        mDataset = dataset;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        // 创建一个View，简单起见直接使用系统提供的布局，就是一个TextView
//        View view = View.inflate(viewGroup.getContext(), R.layout.news_item, null);
        View view = LayoutInflater.from(context).inflate(R.layout.sigle_news_item, viewGroup, false);
        // 创建一个ViewHolder
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        FileInfo fileInfo=mDataset.get(i);
        // 绑定数据到ViewHolder上

            Picasso.with(context)
                    .load(Consts.URL_IMAGE + fileInfo.getDownload())
                    .into(viewHolder.iv_newsImage);

        }


    @Override
    public int getItemCount() {
        Log.e("hhhhhhhhhhhkllllll", "" + mDataset.size());
        return mDataset.size();

    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView iv_newsImage;
        public ViewHolder(View itemView) {
            super(itemView);
            iv_newsImage = (ImageView) itemView.findViewById(R.id.iv_single_news);
        }
    }
}
