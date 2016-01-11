package com.socket.xueyi.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.socket.xueyi.bikeapp.MainActivity;
import com.socket.xueyi.bikeapp.R;
import com.socket.xueyi.uploadImage.clipheadphoto.ClipActivity;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class MyFragment3 extends Fragment {

    @ViewInject(id=R.id.head,click="onClick")ImageView head;
    @ViewInject(id=R.id.exit,click="onClick") Button exit;


    private PopupWindow popWindow;
    private LayoutInflater layoutInflater;
    private TextView photograph,albums;
    private LinearLayout cancel;

    public static final int PHOTOZOOM = 0; // 相册/拍照
    public static final int PHOTOTAKE = 1; // 相册/拍照
    public static final int IMAGE_COMPLETE = 2; // 结果
    public static final int CROPREQCODE = 3; // 截取
    private String photoSavePath;//保存路径
    private String photoSaveName;//图pian名
    private String path;//图片全路径
    private Context mContext;
    private SharedPreferences pref;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_my_fragment3, container, false);

        layoutInflater = (LayoutInflater)getActivity().getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);

        mContext=this.getActivity();

        FinalActivity.initInjectedView(this,view);

        File file = new File(Environment.getExternalStorageDirectory(), "ClipHeadPhoto/cache");
        if (!file.exists())
            file.mkdirs();
        photoSavePath=Environment.getExternalStorageDirectory()+"/ClipHeadPhoto/cache/";
        photoSaveName =System.currentTimeMillis()+ ".png";

        pref=mContext.getSharedPreferences("info", mContext.MODE_PRIVATE);


      /*  head.setOnClickListener(this);
        exit.setOnClickListener(this);*/
        return view;
    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.head:
                showPopupWindow(head);
                break;
            case R.id.exit:
                SharedPreferences.Editor editor=pref.edit();
                editor.putBoolean("logging", false);
               // editor.clear();
                editor.commit();
                startActivity(new Intent(mContext, MainActivity.class));
                getActivity().finish();
                break;

            default:
                break;
        }
    }

        @SuppressWarnings("deprecation")
        private void showPopupWindow(View parent){
            if (popWindow == null) {
                View view = layoutInflater.inflate(R.layout.pop_select_photo,null);
                popWindow = new PopupWindow(view,LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT,true);
                initPop(view);
            }
            popWindow.setAnimationStyle(android.R.style.Animation_InputMethod);
            popWindow.setFocusable(true);
            popWindow.setOutsideTouchable(true);
            popWindow.setBackgroundDrawable(new BitmapDrawable());
            popWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            popWindow.showAtLocation(parent, Gravity.CENTER, 0, 0);
        }



    public void initPop(View view){
        photograph = (TextView) view.findViewById(R.id.photograph);//拍照
        albums = (TextView) view.findViewById(R.id.albums);//相册
        cancel= (LinearLayout) view.findViewById(R.id.cancel);//取消
        photograph.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                popWindow.dismiss();
                photoSaveName =String.valueOf(System.currentTimeMillis()) + ".png";
                Uri imageUri = null;
                Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                imageUri = Uri.fromFile(new File(photoSavePath,photoSaveName));
                openCameraIntent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
                openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(openCameraIntent, PHOTOTAKE);
            }
        });
        albums.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                popWindow.dismiss();
                Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
                openAlbumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(openAlbumIntent, PHOTOZOOM);
            }
        });
        cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                popWindow.dismiss();

            }
        });
    }



    /**
     * 图片选择及拍照结果
     */
    public static final int RESULT_OK  = -1;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }
        Uri uri = null;
        switch (requestCode) {
            case PHOTOZOOM://相册
                if (data==null) {
                    return;
                }
                uri = data.getData();
                String[] proj = { MediaStore.Images.Media.DATA };
                Cursor cursor =getActivity().managedQuery(uri, proj, null, null,null);
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                path = cursor.getString(column_index);// 图片在的路径
                Intent intent3=new Intent(getActivity(), ClipActivity.class);
                intent3.putExtra("path", path);
                startActivityForResult(intent3, IMAGE_COMPLETE);
                break;
            case PHOTOTAKE://拍照
                path=photoSavePath+photoSaveName;
                uri = Uri.fromFile(new File(path));
                Intent intent2=new Intent(getActivity(), ClipActivity.class);
                intent2.putExtra("path", path);
                startActivityForResult(intent2, IMAGE_COMPLETE);
                break;
            case IMAGE_COMPLETE:
                final String temppath = data.getStringExtra("path");
                head.setImageBitmap(getLoacalBitmap(temppath));
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * @param url
     * @return
     */
    public static Bitmap getLoacalBitmap(String url) {
        try {
            FileInputStream fis = new FileInputStream(url);
            return BitmapFactory.decodeStream(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

}
