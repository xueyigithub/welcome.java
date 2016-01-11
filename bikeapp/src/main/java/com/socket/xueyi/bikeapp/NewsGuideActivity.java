package com.socket.xueyi.bikeapp;


import android.os.Bundle;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.socket.xueyi.base.BaseActivity;
import com.socket.xueyi.domain.CampusNews;
import com.socket.xueyi.enums.NewsTypeEnum;
import com.socket.xueyi.fragment.MyFragmentSchool;
import com.socket.xueyi.recovery.MyFragmentSchoolOut;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

import java.util.ArrayList;
import java.util.List;

//TextView+FramLayout
public class NewsGuideActivity extends BaseActivity {

    private ListView lv_newsList;
    //private MySimpleAdapter ba;
    private List<CampusNews> campusNewsData = new ArrayList<CampusNews>();
    //校内新闻
    @ViewInject(id=R.id.txt_channel,click="onClick")TextView txt_channel;
    //校外新闻
    @ViewInject(id=R.id.txt_better,click="onClick")TextView txt_better;

    @ViewInject(id=R.id.ly_content)FrameLayout ly_content;

    private MyFragmentSchool fg1;
    private FragmentManager fManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_news);

        FinalActivity.initInjectedView(this);


        fManager=getSupportFragmentManager();

        txt_channel.performClick();//模拟一次点击，既进去后选择第一项
    }


    //重置所有文本的选中状态
    private void setSelected(){
        txt_channel.setSelected(false);
        txt_better.setSelected(false);

    }

    //隐藏所有Fragment
    private void hideAllFragment(FragmentTransaction fragmentTransaction){
        if(fg1 != null)fragmentTransaction.hide(fg1);
    }


    public void onClick(View v) {
        FragmentTransaction fTransaction = fManager.beginTransaction();
        hideAllFragment(fTransaction);
        fg1= new MyFragmentSchool();
        Bundle args = new Bundle();
        switch (v.getId()){
            case R.id.txt_channel:
                setSelected();
                txt_channel.setSelected(true);
                args.putString("newsType", NewsTypeEnum.In.toString());
                fg1.setArguments(args);
                fTransaction.add(R.id.ly_content, fg1);
                break;

            case R.id.txt_better:
                setSelected();
                txt_better.setSelected(true);
                args.putString("newsType", NewsTypeEnum.Out.toString());
                fg1.setArguments(args);
                fTransaction.add(R.id.ly_content, fg1);
                break;

        }
        fTransaction.commit();
    }

    /*protected void singleCampusNews(String nid){
        Intent intent=new Intent(this,SingleNewsActivity.class);
        intent.putExtra("nid",nid);
        startActivity(intent);
    }

    public void initNewsDate(){
        String path=Consts.URL+"/bike/campusNews/listCampusNews";
        new GetNewsTask().execute(path);
    }
    private class MySimpleAdapter extends BaseAdapter {
        private Context context;
        public MySimpleAdapter(Context context) {
            this.context = context;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        *//**
         * 对listview进行了优化
         * @param position
         * @param convertView
         * @param parent
         * @return
         *//*
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
           // viewHolder.iv_newsImage.setImageResource(campusNew.getImages());
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

    *//**
     * 获取新闻异步任务类
     *
     *//*
    class GetNewsTask extends AsyncTask<String, Void, List<CampusNews>> {

        List<CampusNews> data = null;

        @Override
        protected List<CampusNews> doInBackground(String... params) {
            String result = null;
            try {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(params[0])
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
            ResponseData response = gson.fromJson(result, ResponseData.class);
            data = response.getContent();
            return data;
        }

        @Override
        protected void onPostExecute(List<CampusNews> data) {
            campusNewsData = data;
            ba.notifyDataSetChanged();
        }

    }*/
}

