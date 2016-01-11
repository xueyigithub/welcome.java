package com.socket.xueyi.schedule;

import android.database.Cursor;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.socket.xueyi.bikeapp.R;
import com.socket.xueyi.schedule.entity.DateDay;
import com.socket.xueyi.schedule.util.ToDoDB;

public class RemindInsert extends AppCompatActivity {
    private ToDoDB toDoDB;
    private Cursor cursor;
    private EditText et1;
    private String strTimeNow,timeStr;//strTimeNow第几周，timeStr当前月日
    private int timeInt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remind_insert);

        //获取数据库
        toDoDB=new ToDoDB(this);
        cursor=toDoDB.selectRemind();

        /**
         * 显示的时间
         */
        DateDay dd=new DateDay(this);
        TextView tv2=(TextView)this.findViewById(R.id.remind_insert_tv2);
        strTimeNow=("第"+dd.getWeedDay()+"周 "+dd.getDays1()+" "+"   "+dd.getMonth3()+"月"+dd.getDate()+"日 ");
      //  Typeface tf=Typeface.createFromAsset(getAssets(), "fonts/Roboto-ThinItalic.ttf");
      //  tv2.setTypeface(tf);
        tv2.setTextColor(getResources().getColor(R.color.blue));
        tv2.setText(strTimeNow);
        timeStr=dd.getCurrentTime();

        ImageButton bt2=(ImageButton)findViewById(R.id.remind_insert_bt2);

        et1=(EditText)findViewById(R.id.remind_insert_et1);

        bt2.setOnClickListener(new ButtonListener2());

    }


    public class ButtonListener2 implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            finish();
            //都是在返回的时候调用的
            overridePendingTransition(R.anim.slide_up_in, R.anim.slide_down_out);
        }
        // TODO Auto-generated method stub

    }
    private void addTodo()
    {
        if (et1.getText().toString().equals("")) return;
	    /* 新增数据到数据库 */
        toDoDB.insertRemind(et1.getText().toString(),strTimeNow,timeStr);
	    /* 重新查询 */
        cursor.requery();
        cursor.close();
        toDoDB.close();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //如果按下的是返回键，并且没有重复
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            addTodo();
            TodayDateRemind.cursor.requery();
            TodayDateRemind.lv.invalidateViews();
            finish();
            // Activity的切换动画指的是从一个activity跳转到另外一个activity时的动画
            overridePendingTransition(R.anim.slide_up_in, R.anim.slide_down_out);
            return false;
        }
        return false;
    }

}
