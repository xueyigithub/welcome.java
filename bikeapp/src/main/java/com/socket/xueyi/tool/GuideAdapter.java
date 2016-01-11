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
import com.socket.xueyi.domain.LifeGuide;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by XUEYI on 2015/12/11.
 */
public class GuideAdapter extends BaseAdapter {
    private Context context;
    private List<LifeGuide> lifeGuides;
    public GuideAdapter(List<LifeGuide> lifeGuides,Context context) {
        this.context = context;
        this.lifeGuides=lifeGuides;
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
            view = LayoutInflater.from(context).inflate(R.layout.content_card_item, null);
            viewHolder=new ViewHolder();
            viewHolder.txt_title=(TextView) view.findViewById(R.id.txt_title);
            viewHolder.txt_time=(TextView) view.findViewById(R.id.txt_time);
            viewHolder.img_icon=(ImageView)view.findViewById(R.id.img_icon);
            view.setTag(viewHolder);
        }else{
            view=convertView;
            viewHolder=(ViewHolder)view.getTag();
        }
        LifeGuide lifeGuide= lifeGuides.get(position);
        viewHolder.txt_title.setText(lifeGuide.getTitle());
        viewHolder.txt_time.setText(lifeGuide.getCreateTime().toString());
        Picasso.with(context)
                .load(Consts.URL_IMAGE +lifeGuide.getTitleImage().getDownload())
                .into(viewHolder.img_icon);
        return view;
    }

    @Override
    public int getCount() {
        return lifeGuides == null ? 0 : lifeGuides.size();
    }
    class ViewHolder{
        TextView txt_title;
        TextView txt_time;
        ImageView img_icon;
    }
}
