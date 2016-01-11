package com.socket.xueyi.tool;

import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import java.util.List;

/**
 * ViewPager适配器
 * Created by XUEYI on 2015/12/13.
 */
public class MyPagerAdapter extends PagerAdapter {
    public List<View> mListViews;

    public MyPagerAdapter(List<View> listViews) {
        this.mListViews = listViews;
    }

    @Override
    public void destroyItem(View arg0, int arg1, Object arg2) {
        Log.i("destroyItem", "destroyItem");
        ((ViewPager) arg0).removeView(mListViews.get(arg1));

    }

    @Override
    public void finishUpdate(View arg0) {
    }

    @Override
    public int getCount() {
        Log.i("getCount", "getCount");
        return mListViews.size();
    }

    @Override
    public Object instantiateItem(View arg0, int arg1) {
        Log.i("instantiateItem", "instantiateItem");
        ((ViewPager) arg0).addView(mListViews.get(arg1), 0);
        return mListViews.get(arg1);
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == (arg1);
    }

    @Override
    public void restoreState(Parcelable arg0, ClassLoader arg1) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public void startUpdate(View arg0) {
    }

    @Override
    public void notifyDataSetChanged() {
        // TODO Auto-generated method stub
        super.notifyDataSetChanged();
    }

}
