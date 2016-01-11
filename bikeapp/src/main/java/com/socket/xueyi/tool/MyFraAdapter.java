package com.socket.xueyi.tool;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.socket.xueyi.bikeapp.R;
import com.socket.xueyi.domain.Icon;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by XUEYI on 2015/12/11.
 */
public class MyFraAdapter  extends BaseAdapter {
    private Context context;
    private ArrayList<Icon> mData;
    public MyFraAdapter(ArrayList<Icon> mData,Context context) {
        this.context = context;
        this.mData=mData;
    }
    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder viewHolder;
        if(convertView==null){
            view = LayoutInflater.from(context).inflate(R.layout.item_grid_icon, null);
            viewHolder=new ViewHolder();
            viewHolder.img_icon=(ImageView) view.findViewById(R.id.img_icon);
            viewHolder.txt_icon=(TextView) view.findViewById(R.id.txt_icon);
            view.setTag(viewHolder);
        }else{
            view=convertView;
            viewHolder=(ViewHolder)view.getTag();
        }
        Icon icon= mData.get(position);
        //viewHolder.img_icon.setImageResource(icon.getiId());
        Picasso.with(context).load(icon.getiId()).into(viewHolder.img_icon);
        viewHolder.txt_icon.setText(icon.getiName());
        return view;
    }


    class ViewHolder{
        ImageView img_icon;
        TextView txt_icon;
    }


}


