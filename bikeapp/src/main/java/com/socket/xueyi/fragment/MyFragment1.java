package com.socket.xueyi.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.socket.xueyi.bikeapp.LifeGuideActivity;
import com.socket.xueyi.bikeapp.NewsGuideActivity;
import com.socket.xueyi.bikeapp.PlayGuideActivity;
import com.socket.xueyi.bikeapp.R;
import com.socket.xueyi.bikeapp.ScheduleActivity;
import com.socket.xueyi.bikeapp.StudyGuideActivity;
import com.socket.xueyi.config.Consts;
import com.socket.xueyi.domain.Icon;
import com.socket.xueyi.tool.MyFraAdapter;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

import java.util.ArrayList;

public class MyFragment1 extends Fragment{
    //学习指南，生活指南
   @ViewInject(id=R.id.grid_photo,itemClick = "onItemClick")GridView grid_photo;

    private ArrayList<Icon> mData = null;
    /*private ViewFlipper viewFlipper;
    private static GestureDetector detector; //手势检测


    Animation leftInAnimation;
    Animation leftOutAnimation;
    Animation rightInAnimation;
    Animation rightOutAnimation;*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_fragment1, container, false);
        /*getActivity().setTitle(getString(R.string.tab_menu_alert));
       ((HomePageActivity)getActivity()).registerMyTouchListener(mTouchListener);*/
        FinalActivity.initInjectedView(this,view);

        mData = new ArrayList<Icon>();
        Icon icon1=new Icon();
        icon1.setiId(R.drawable.schoolnews_in);
        icon1.setiName("校园新闻");
        mData.add(icon1);

        Icon icon2=new Icon();
        icon2.setiId(R.drawable.life_guide);
        icon2.setiName("生活指南");
        mData.add(icon2);

        Icon icon3=new Icon();
        icon3.setiId(R.drawable.play_guide);
        icon3.setiName("游玩指南");
        mData.add(icon3);

        Icon icon4=new Icon();
        icon4.setiId(R.drawable.study_guide);
        icon4.setiName("就业指南");
        mData.add(icon4);


        Icon icon5=new Icon();
        icon5.setiId(R.drawable.schedule);
        icon5.setiName("学生课表");
        mData.add(icon5);

        Icon icon6=new Icon();
        icon6.setiId(R.drawable.lost_and_found);
        icon6.setiName("失物招领");
        mData.add(icon6);

        grid_photo.setAdapter(new MyFraAdapter(mData,getActivity()));

        return view;
        /* viewFlipper = (ViewFlipper)view.findViewById(R.id.viewFlipper);

        //往viewFlipper添加View
        viewFlipper.addView(getImageView(R.mipmap.iv_icon_1));
        viewFlipper.addView(getImageView(R.mipmap.iv_icon_2));
        viewFlipper.addView(getImageView(R.mipmap.iv_icon_3));
        viewFlipper.addView(getImageView(R.mipmap.iv_icon_4));
        viewFlipper.addView(getImageView(R.mipmap.iv_icon_5));
        viewFlipper.addView(getImageView(R.mipmap.iv_icon_6));

        //动画效果
        leftInAnimation = AnimationUtils.loadAnimation(MyFragment1.this.getActivity(), R.anim.left_in);
        leftOutAnimation = AnimationUtils.loadAnimation(MyFragment1.this.getActivity(), R.anim.left_out);
        rightInAnimation = AnimationUtils.loadAnimation(MyFragment1.this.getActivity(), R.anim.right_in);
        rightOutAnimation = AnimationUtils.loadAnimation(MyFragment1.this.getActivity(), R.anim.right_out);

        detector = new GestureDetector(this);*/

    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String name=mData.get(position).getiName();
        switch (position) {
            case Consts.PAGE_ONE:
                newsActivity();
                break;
            case Consts.PAGE_TWO:
                lifeActivity(Consts.URLLIFE,name);
                break;
            case Consts.PAGE_THREE:
                PlayGuideActivity(name);
                break;
            case Consts.PAGE_FOUR:
                StudyActivity(name);
                break;
            case Consts.PAGE_FIVE:
                scheduleActivity();
                break;
        }
        Toast.makeText(MyFragment1.this.getActivity(), "你点击了~" + position + "~项", Toast.LENGTH_SHORT).show();
    }


    public void newsActivity(){
        Intent intent = new Intent(getActivity(), NewsGuideActivity.class);
        startActivity(intent);
    }

    public void lifeActivity(String path ,String name){
        Intent intent = new Intent(getActivity(), LifeGuideActivity.class);
        intent.putExtra("path",path);
        intent.putExtra("name",name);
        startActivity(intent);
    }
    public void StudyActivity(String name){
        Intent intent = new Intent(getActivity(), StudyGuideActivity.class);
        intent.putExtra("name",name);
        startActivity(intent);
    }

    public void PlayGuideActivity(String name){
        Intent intent = new Intent(getActivity(), PlayGuideActivity.class);
        intent.putExtra("name",name);
        startActivity(intent);
    }

    public void scheduleActivity(){
        Intent intent = new Intent(getActivity(), ScheduleActivity.class);
        startActivity(intent);
    }



  /*  private ImageView getImageView(int id){
        ImageView imageView = new ImageView(MyFragment1.this.getActivity());
        imageView.setImageResource(id);
        return imageView;
    }*/



    /*重写viewfliper里面的方法*//*
    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        if(e1.getX()-e2.getX()>120){
            viewFlipper.setInAnimation(leftInAnimation);
            viewFlipper.setOutAnimation(leftOutAnimation);
            viewFlipper.showNext();//向右滑动
            return true;
        } else if (e1.getX() - e2.getY() < -120) {
            viewFlipper.setInAnimation(rightInAnimation);
            viewFlipper.setOutAnimation(rightOutAnimation);
            viewFlipper.showPrevious();//向左滑动
            return true;
        }
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    *//**
     * Fragment中，注册
     * 接收MainActivity的Touch回调的对象
     * 重写其中的onTouchEvent函数，并进行该Fragment的逻辑处理
     *//*
    private .MyTouchListener mTouchListener = new HomePageActivity.MyTouchListener() {
        @Override
        public boolean onTouchEvent(MotionEvent event) {
            return MyFragment1.detector.onTouchEvent(event);//
        }
    };*/
}
