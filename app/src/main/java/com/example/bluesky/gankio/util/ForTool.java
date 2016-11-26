package com.example.bluesky.gankio.util;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.bluesky.gankio.R;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by localhost on 2016/11/15.
 */

public class ForTool {

    /**
     *
     * @param mContext
     * @return 状态栏的高度
     */
    public int getStatusBarHeight(Context mContext) {
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            return mContext.getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    public Dialog ShowProgress(Context c){
        Dialog progressDialog = new Dialog(c,R.style.progress_dialog);
        progressDialog.setContentView(R.layout.juse);
        progressDialog.setCancelable(true);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        return progressDialog;
    }
    public AlertDialog WriterShow(Context c){
        LayoutInflater inflater = LayoutInflater.from(c);
        View v=inflater.inflate(R.layout.writerinfo,null);
        final AlertDialog dialog = new AlertDialog.Builder(c).create();
        dialog.show();//setContentView必须在show之前
        //圆角的时候要去掉AlertDialog的背景但图片会显示不出 并不知道原因
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.writerinfo);
        //设置边框为圆角的
        dialog.getWindow().setContentView(v)  ; // 自定义dialog

        return dialog;
    }
    public  AlertDialog ShowDialog(Context c,String url){
        LayoutInflater inflater = LayoutInflater.from(c);
        View v=inflater.inflate(R.layout.showimage,null);
        final AlertDialog dialog = new AlertDialog.Builder(c,R.style.dialog).create();
        ImageView img = (ImageView)v.findViewById(R.id.Iv_show);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        ForTool m=new ForTool();
        m.loadImage(url,img,c);
        dialog.setView(v); // 自定义dialog
        dialog.show();
        return dialog;
    }


    //加载图片的
    public   void loadImage(List<String> url , ImageView view, Context c){
        if(url==null) Glide.with(c).load(R.mipmap.pic).centerCrop().into(view);
        else {
            Glide.with(c)
                    .load(url.get(0))
                    .error(R.mipmap.pic)
                    .centerCrop()
                    .into(view);
        }
    }
    //加载图片的
    public   void loadImage(String url , ImageView view, Context c){
        if(url==null) Glide.with(c).load(R.mipmap.pic).centerCrop().into(view);
        else {
            Log.i("url", "loadImage: "+url);
            Glide.with(c)
                    .load(url)
                    .placeholder(R.mipmap.wait)
                    .error(R.mipmap.error)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .fitCenter()
                    .into(view);

        }
    }


    public  void ShowShackBar(View view,final Context c){
        Snackbar.make(view,"没有网络",Snackbar.LENGTH_SHORT)
                .setAction("设置", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        net.GoToNet(c);
                    }
                })
                .show();
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    /**
     * 通过反射更改toolbar无法显示图片的问题
     * @param menu
     * @param enable
     */
    public void setIconEnable(Menu menu, boolean enable)
    {
        try
        {
            Class<?> clazz = Class.forName("com.android.internal.view.menu.MenuBuilder");
            Method m = clazz.getDeclaredMethod("setOptionalIconsVisible", boolean.class);
            m.setAccessible(true);
            //下面传入参数
            m.invoke(menu, enable);

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    public static Long getTime(){
        Date dt = new Date();
        System.out.println(dt.toString());   //java.util.Date的含义
        return dt.getTime() / 1000;
    }

}
