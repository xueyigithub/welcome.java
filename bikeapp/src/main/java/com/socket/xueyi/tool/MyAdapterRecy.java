package com.socket.xueyi.tool;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.socket.xueyi.bikeapp.R;
import com.socket.xueyi.config.Consts;
import com.socket.xueyi.domain.CampusNews;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by XUEYI on 2015/12/10.
 */

public class MyAdapterRecy extends RecyclerView.Adapter<MyAdapterRecy.ViewHolder> {

    /**
     * ItemClick的回调接口
     * @author zhy
     *
     */
    public interface OnItemClickLitener
    {
        void onItemClick(View view, int position);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener)
    {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }


    private List<CampusNews> mDataset;
    private Context context;
    private  String nid;
    public MyAdapterRecy(List<CampusNews> dataset,Context context) {
        super();
        this.context=context;
        mDataset = dataset;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        // 创建一个View，简单起见直接使用系统提供的布局，就是一个TextView
//        View view = View.inflate(viewGroup.getContext(), R.layout.news_item, null);
        View view = LayoutInflater.from(context).inflate(R.layout.news_item, viewGroup, false);
        // 创建一个ViewHolder
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

   @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        CampusNews campusNews=mDataset.get(i);
        // 绑定数据到ViewHolder上
        viewHolder.tv_newsTitle.setText(campusNews.getTitle());
        viewHolder.tv_newsDateTime.setText(campusNews.getCreateTime().toString());
       if(campusNews.getTitleImage()==null){
           Picasso.with(context)
                   .load(R.drawable.a)
                   .into(viewHolder.iv_newsImage);
       }else {
           Picasso.with(context)
                   .load(Consts.URL_IMAGE + campusNews.getTitleImage().getDownload())
                   .into(viewHolder.iv_newsImage);
       }
       nid=campusNews.getId();

       //如果设置了回调，则设置点击事件
       if (mOnItemClickLitener != null)
       {
           viewHolder.itemView.setOnClickListener(new View.OnClickListener()
           {
               @Override
               public void onClick(View v)
               {
                   mOnItemClickLitener.onItemClick(viewHolder.itemView, i);

               }
           });

       }
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView tv_newsTitle;
        public TextView tv_newsDateTime;
        public ImageView iv_newsImage;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_newsTitle = (TextView) itemView.findViewById(R.id.tv_newsTitle);
            tv_newsDateTime = (TextView) itemView.findViewById(R.id.tv_newsDateTime);
            iv_newsImage = (ImageView) itemView.findViewById(R.id.iv_newsImage);
        }
    }

}
