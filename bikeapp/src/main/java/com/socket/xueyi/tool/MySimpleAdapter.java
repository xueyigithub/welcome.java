package com.socket.xueyi.tool;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.socket.xueyi.bikeapp.R;
import com.socket.xueyi.config.Consts;
import com.socket.xueyi.domain.CampusNews;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by XUEYI on 2015/12/11.
 */
public class MySimpleAdapter extends BaseAdapter {
    private Context context;
    private List<CampusNews> campusNewsData;
    public MySimpleAdapter(List<CampusNews> campusNewsData,Context context) {
        this.context = context;
        this.campusNewsData=campusNewsData;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    /**
     * 对listview进行了优化
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder viewHolder;
        if(convertView==null){
            view = LayoutInflater.from(context).inflate(R.layout.news_item, null);
            viewHolder=new ViewHolder();
            viewHolder.tv_newsTitle=(TextView) view.findViewById(R.id.tv_newsTitle);
            viewHolder.tv_newsDateTime=(TextView) view.findViewById(R.id.tv_newsDateTime);
            viewHolder.iv_newsImage=(ImageView)view.findViewById(R.id.iv_newsImage);
            view.setTag(viewHolder);
        }else{
            view=convertView;
            viewHolder=(ViewHolder)view.getTag();
        }
        CampusNews campusNew= campusNewsData.get(position);
        viewHolder.tv_newsTitle.setText(campusNew.getTitle());
        viewHolder.tv_newsDateTime.setText(campusNew.getCreateTime().toString());

        if(campusNew.getTitleImage()==null){
            Picasso.with(context)
                    .load(R.drawable.a)
                    .into(viewHolder.iv_newsImage);
        }else {
            Picasso.with(context)
                    .load(Consts.URL_IMAGE + campusNew.getTitleImage().getDownload())
                    .into(viewHolder.iv_newsImage);
        }
        return view;
    }

    @Override
    public int getCount() {
        return campusNewsData == null ? 0 : campusNewsData.size();
    }
    class ViewHolder{
        TextView tv_newsTitle;
        TextView tv_newsDateTime;
        ImageView iv_newsImage;
    }
}
