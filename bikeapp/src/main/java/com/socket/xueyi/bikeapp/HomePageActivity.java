package com.socket.xueyi.bikeapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.socket.xueyi.base.BaseActivity;
import com.socket.xueyi.config.Consts;
import com.socket.xueyi.domain.Item;
import com.socket.xueyi.tool.MyAdapter;
import com.socket.xueyi.tool.MyFragmentPagerAdapter;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

import java.util.ArrayList;

public class HomePageActivity extends BaseActivity {
    /* ViewPager*/
    private RadioGroup mGroup;
    private RadioButton rb_channel;
    private RadioButton rb_message;
    private RadioButton rb_better;
    private ViewPager vpager;

    private Toolbar mToolbar;

    /* DrawerLayout*/
    /*private DrawerLayout drawer_layout;*/
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView list_left_drawer;
    private ArrayList<Item> menuLists;
    private MyAdapter<Item> myAdapter = null;

    public ArrayList<MyTouchListener> listeners = new ArrayList<MyTouchListener>();

    @ViewInject(id = R.id.tv_userName)
    TextView userName;

    @ViewInject(id = R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        FinalActivity.initInjectedView(this);
        // 初始化Toolbar
        initToolbar();

        Intent intent = this.getIntent();
        String name = intent.getStringExtra("name");
        //userName.setText(name);

        //隐藏
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        bindViews();
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }


    //对DrawerLayout进行监听
    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.nav_found:
                                if (vpager.getCurrentItem() != Consts.PAGE_ONE) {
                                    vpager.setCurrentItem(Consts.PAGE_ONE, true);
                                }

                                break;

                            case R.id.nav_weather:
                                if (vpager.getCurrentItem() != Consts.PAGE_TWO) {
                                    vpager.setCurrentItem(Consts.PAGE_TWO, true);
                                }

                                break;

                            case R.id.nav_mine:
                                if (vpager.getCurrentItem() != Consts.PAGE_THREE) {
                                    vpager.setCurrentItem(Consts.PAGE_THREE, true);
                                }
                                break;

                        }
                        // menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }


    private void bindViews() {
        mGroup = (RadioGroup) findViewById(R.id.rg_tab_bar);
        rb_channel = (RadioButton) findViewById(R.id.rb_channel);
        rb_message = (RadioButton) findViewById(R.id.rb_message);
        rb_better = (RadioButton) findViewById(R.id.rb_better);
        vpager = (ViewPager) findViewById(R.id.vpager);

        mGroup.setOnCheckedChangeListener(onCheckedChangeListener);

         /* 获取第一个单选按钮，并设置其为选中状态*/
        rb_channel.setChecked(true);

        vpager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager()));

        vpager.addOnPageChangeListener(new PageChangeListener());

        vpager.setCurrentItem(0);

        vpager.setOffscreenPageLimit(3);
    }


    /*rb_channel:发现
    * rb_message:好友
    * rb_better:我的*/
    private RadioGroup.OnCheckedChangeListener onCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        //    private class CheckedChangeListener implements RadioGroup.OnCheckedChangeListener{
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            Log.d("RadioGroup", "OnCheckedChangeListener!!!");
            switch (checkedId) {
                case R.id.rb_channel:
                    if (vpager.getCurrentItem() != Consts.PAGE_ONE) {
                        vpager.setCurrentItem(Consts.PAGE_ONE, true);
                    }
                    break;
                case R.id.rb_message:
                    if (vpager.getCurrentItem() != Consts.PAGE_TWO) {
                        vpager.setCurrentItem(Consts.PAGE_TWO, true);
                    }
                    break;
                case R.id.rb_better:
                    if (vpager.getCurrentItem() != Consts.PAGE_THREE) {
                        vpager.setCurrentItem(Consts.PAGE_THREE, true);
                    }
                    break;
                default:
                    break;
            }
        }
    };


    /*重写ViewPager页面切换的处理方法*/
    private class PageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageSelected(int position) {
            switch (position) {
                case Consts.PAGE_ONE:
                    rb_channel.setChecked(true);
                    break;
                case Consts.PAGE_TWO:
                    rb_message.setChecked(true);
                    break;
                case Consts.PAGE_THREE:
                    rb_better.setChecked(true);
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        /*state的状态有三个，0表示什么都没做，1正在滑动，2滑动完毕*/
            if (state == 2) {
                switch (vpager.getCurrentItem()) {
                    case Consts.PAGE_ONE:
                        rb_channel.setChecked(true);
                        break;
                    case Consts.PAGE_TWO:
                        rb_message.setChecked(true);
                        break;
                    case Consts.PAGE_THREE:
                        rb_better.setChecked(true);
                        break;
                }
            }
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }
    }

    /*menu*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.action_about:
                Toast.makeText(this, "你点了关于", Toast.LENGTH_LONG).show();
            default:
                break;
        }
        return true;
    }


    //将toolbar中的文字居中
    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setTitle("骑车宝");
        mToolbar.setNavigationIcon(R.mipmap.ic_menu);
        setSupportActionBar(mToolbar);


        /*getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        ActionBar.LayoutParams lp = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        View actionBarView = LayoutInflater.from(this).inflate(R.layout.action_bar_layout, null);
        getSupportActionBar().setCustomView(actionBarView, lp);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP|ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);*/
    }


    /**
     * viewFliper回调接口，fragment里面没有onTouchEvent
     *
     * @author zhaoxin5
     */
    public interface MyTouchListener {
        public boolean onTouchEvent(MotionEvent event);
    }

    /*
     * 保存MyTouchListener接口的列表
     */
    private ArrayList<MyTouchListener> myTouchListeners = new ArrayList<HomePageActivity.MyTouchListener>();

    /**
     * 提供给Fragment通过getActivity()方法来注册自己的触摸事件的方法
     *
     * @param listener
     */
    public void registerMyTouchListener(MyTouchListener listener) {
        myTouchListeners.add(listener);
    }

    /**
     * 提供给Fragment通过getActivity()方法来取消注册自己的触摸事件的方法
     *
     * @param listener
     */
    public void unRegisterMyTouchListener(MyTouchListener listener) {
        myTouchListeners.remove(listener);
    }

    /**
     * 分发触摸事件给所有注册了MyTouchListener的接口
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // TODO Auto-generated method stub
        for (MyTouchListener listener : myTouchListeners) {
            listener.onTouchEvent(ev);
        }
        return super.dispatchTouchEvent(ev);
    }
}
