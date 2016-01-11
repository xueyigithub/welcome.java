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
import com.socket.xueyi.domain.PlayGuide;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by XUEYI on 2015/12/10.
 */

public class MyAdapterRecyPlayGuide extends RecyclerView.Adapter<MyAdapterRecyPlayGuide.ViewHolder> {

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


    private List<PlayGuide> mDataset;
    private Context context;
    public MyAdapterRecyPlayGuide(List<PlayGuide> dataset, Context context) {
        this.context=context;
        this.mDataset = dataset;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        // 创建一个View，简单起见直接使用系统提供的布局，就是一个TextView
//        View view = View.inflate(viewGroup.getContext(), R.layout.news_item, null);
        View view = LayoutInflater.from(context).inflate(R.layout.content_card_item, viewGroup, false);
        // 创建一个ViewHolder
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

   @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
       PlayGuide playGuide=mDataset.get(i);
        // 绑定数据到ViewHolder上
        viewHolder.txt_title.setText(playGuide.getTitle());
        viewHolder.txt_time.setText(playGuide.getSummary());
       if(playGuide.getTitleImage()==null){
           Picasso.with(context)
                   .load(R.drawable.a)
                   .into(viewHolder.img_icon);
       }else {
           Picasso.with(context)
                   .load(Consts.URL_IMAGE + playGuide.getTitleImage().getDownload())
                   .into(viewHolder.img_icon);
       }

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

        public TextView txt_title;
        public TextView txt_time;
        public ImageView img_icon;
        public ViewHolder(View itemView) {
            super(itemView);
            txt_title = (TextView) itemView.findViewById(R.id.txt_title);
            txt_time = (TextView) itemView.findViewById(R.id.txt_time);
            img_icon = (ImageView) itemView.findViewById(R.id.img_icon);
        }
    }

}
