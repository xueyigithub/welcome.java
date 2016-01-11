package com.socket.xueyi.bikeapp;

import android.app.LocalActivityManager;
import android.app.TabActivity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.socket.xueyi.schedule.TodayDateAbout;
import com.socket.xueyi.schedule.TodayDateRemind;
import com.socket.xueyi.schedule.TodayDateSchedule;
import com.socket.xueyi.schedule.TodayDateSetting;
import com.socket.xueyi.tool.MyPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ScheduleActivity extends TabActivity {
    private ViewPager mPager;//主要显示内容的动画
    private List<View> listViews;//Tab页面列表
    private ImageView cursor;//动画图片
    private Button bt1,bt2;
    private int offset=0;//动画图片偏移量
    private int currIndex = 0;// 当前页卡编号
    private int bmpW;// 动画图片宽度

    private LayoutInflater mLayoutInflater;
    private TabHost tabHost;
    private LocalActivityManager manager = null;
    public static String mTextviewArray[] = { "备忘录", "课程表" };
    public static Class mTabClassArray[] = { TodayDateRemind.class,
            TodayDateSchedule.class };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        init(savedInstanceState);
        InitImageView();
        InitTextView();
        InitViewPager();
    }

    /**
     * 初始化动画
     */
    private void InitImageView(){
        cursor=(ImageView)findViewById(R.id.cursor);
        bmpW=BitmapFactory.decodeResource(getResources(),R.drawable.a)
                     .getWidth();//获取图片宽度
        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW=dm.widthPixels;//获取分辨率宽度
        offset=(screenW/2-bmpW)/2;//计算偏移量
        Matrix matrix=new Matrix();
        matrix.postTranslate(offset, 0);
        cursor.setImageMatrix(matrix);//设置动画初始位置
    }

    /**
     * 初始化头标
     */
    private void InitTextView() {
        bt1 = (Button) findViewById(R.id.bt1);
        bt2 = (Button) findViewById(R.id.bt2);

        bt1.setOnClickListener(new MyOnClickListener(0));
        bt2.setOnClickListener(new MyOnClickListener(1));

    }
    /**
     * 初始化ViewPager
     */
    private void InitViewPager() {
        mPager = (ViewPager) findViewById(R.id.vPager);
        listViews = new ArrayList<View>();
        MyPagerAdapter mpAdapter = new MyPagerAdapter(listViews);
        Intent intent = new Intent(this, TodayDateRemind.class);
        listViews.add(getView("Remind", intent));
        Intent intent2 = new Intent(this, TodayDateSchedule.class);
        listViews.add(getView("Schedule", intent2));
        mPager.setAdapter(mpAdapter);
        mPager.setCurrentItem(0);
        mPager.setOnPageChangeListener(new MyOnPageChangeListener());// 为mPage设置了另一个监听

    }

    /**
     * 开始不同的页面
     * @param savedInstanceState
     */
    private void init(Bundle savedInstanceState){
        tabHost = getTabHost();
        tabHost.addTab(tabHost.newTabSpec("备忘录").setIndicator("")
                .setContent(new Intent(this,TodayDateRemind.class)));
        tabHost.addTab(tabHost.newTabSpec("课程表").setIndicator("")
                .setContent(new Intent(this,TodayDateSchedule.class)));

        tabHost.setCurrentTab(0);

        manager = new LocalActivityManager(this, true);
        manager.dispatchCreate(savedInstanceState);

    }

    /**
     * 头标点击监听
     */
    public class MyOnClickListener implements View.OnClickListener {
        private int index = 0;

        public MyOnClickListener(int i) {
            index = i;
        }

        @Override
        public void onClick(View v) {
            mPager.setCurrentItem(index);
            switch (index) {
                case 0:
                    bt1.setBackgroundDrawable(getResources().getDrawable(
                            R.drawable.remind_title_f_s1_f));
                    bt2.setBackgroundDrawable(getResources().getDrawable(
                            R.drawable.remind_title_f_s2_p));
                    break;

                case 1:
                    bt1.setBackgroundDrawable(getResources().getDrawable(
                            R.drawable.remind_title_f_s1_p));
                    bt2.setBackgroundDrawable(getResources().getDrawable(
                            R.drawable.remind_title_f_s2_f));
                    break;

                default:
                    break;
            }

        }
    };
    /**
     * 页卡切换监听,下面的横线
     */
    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        int one = offset * 2 + bmpW;// 页卡1 -> 页卡2 偏移量
        int two = one * 2;// 页卡1 -> 页卡3 偏移量

        @Override
        public void onPageSelected(int arg0) {
            Animation animation = null;

            switch (arg0) {
                case 0:
                    bt1.setBackgroundDrawable(getResources().getDrawable(
                            R.drawable.remind_title_f_s1_f));
                    bt2.setBackgroundDrawable(getResources().getDrawable(
                            R.drawable.remind_title_f_s2_p));
                    tabHost.setCurrentTab(0);
                    if (currIndex == 1) {
                        animation = new TranslateAnimation(one, 0, 0, 0);
                    } else if (currIndex == 2) {
                        animation = new TranslateAnimation(two, 0, 0, 0);
                    }
                    break;
                case 1:
                    bt1.setBackgroundDrawable(getResources().getDrawable(
                            R.drawable.remind_title_f_s1_p));
                    bt2.setBackgroundDrawable(getResources().getDrawable(
                            R.drawable.remind_title_f_s2_f));
                    tabHost.setCurrentTab(1);
                    if (currIndex == 0) {
                        animation = new TranslateAnimation(offset, one, 0, 0);
                    } else if (currIndex == 2) {
                        animation = new TranslateAnimation(two, one, 0, 0);
                    }
                    break;
                case 2:
                    if (currIndex == 0) {
                        animation = new TranslateAnimation(offset, two, 0, 0);
                    } else if (currIndex == 1) {
                        animation = new TranslateAnimation(one, two, 0, 0);
                    }
                    break;
            }

            currIndex = arg0;
            animation.setFillAfter(true);// True:图片停在动画结束位置
            animation.setDuration(300);
            cursor.startAnimation(animation);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    }

    private View getTabItemView(int index) {

        // 定义了item那一栏的view分布,不采用原始tab的视图
        View view = mLayoutInflater.inflate(R.layout.tab_item_view, null);

        TextView textView = (TextView) view.findViewById(R.id.textview);

        textView.setText(mTextviewArray[index]);

        return view;
    }

    //菜单
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        // MenuInflater inflater = getMenuInflater();

        menu.add(1, 1, 3, "退出");
        menu.add(1, 3, 1, "关于");
        menu.add(1, 2, 2, "设置");

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub

        switch (item.getItemId()) {
            case 1:
                finish();
                break;

            case 2:
                Intent intent = new Intent(this, TodayDateSetting.class);
                this.startActivity(intent);
                overridePendingTransition(R.anim.slide_up_in, R.anim.slide_down_out);
                break;

            case 3:
                Intent intent2 = new Intent(this, TodayDateAbout.class);
                this.startActivity(intent2);
                overridePendingTransition(R.anim.slide_up_in, R.anim.slide_down_out);
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }



    private View getView(String id, Intent intent) {
        return manager.startActivity(id, intent).getDecorView();
    }

}
