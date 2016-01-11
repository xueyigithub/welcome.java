package com.socket.xueyi.schedule;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;



import com.socket.xueyi.bikeapp.R;
import com.socket.xueyi.tool.FolksAdapter;
import com.socket.xueyi.schedule.util.ToDoDB;

public class TodayDateRemind extends AppCompatActivity implements View.OnClickListener {
    private static ToDoDB toDoDB;
    public static Cursor cursor;
    public static ListView lv ;
    private int _id;
    private Button bt1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_date_remind);

        lv=(ListView)this.findViewById(R.id.myListView);
        bt1=(Button)this.findViewById(R.id.app_remind_bt1);

        bt1.setOnClickListener(this);

        toDoDB=new ToDoDB(this);

        cursor=toDoDB.selectRemind();

        FolksAdapter adapter=new FolksAdapter(TodayDateRemind.this, cursor);
        lv.setAdapter(adapter);

        //设置隐藏列表分割线
        lv.setDividerHeight(0);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView arg0, View arg1, int arg2, long arg3) {
                Context context = getApplicationContext();
	    	/* 将cursor移到所点选的值 */
                cursor.moveToPosition(arg2);
	        /* 取得字段_id的值 */
                _id = cursor.getInt(0);
	        /* 打开RemindEdit */
                //Log.i("", "已点击");
                Intent intent = new Intent();
                intent.putExtra("ARG2", arg2);
                //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setClass(context, RemindEdit.class);
                TodayDateRemind.this.startActivity(intent);
            }
        });

        lv .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView arg0, View arg1, int arg2, long arg3) {
	        /* getSelectedItem所取得的是SQLiteCursor */
                SQLiteCursor sc = (SQLiteCursor) arg0.getSelectedItem();
                _id = sc.getInt(0);
                //Log.i("", "已选择");
            }

            @Override
            public void onNothingSelected(AdapterView arg0) {
            }
        });
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        Intent intent=new Intent(this, RemindInsert.class);
        TodayDateRemind.this.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        cursor.close();
        toDoDB.close();
    }
}
