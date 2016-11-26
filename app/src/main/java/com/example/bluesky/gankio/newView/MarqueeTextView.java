package com.example.bluesky.gankio.newView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

/**
 *参照了网上的实现方案 其实还有一种实现方法 就是在ondraw函数中将text的位置不断移动位置画出来
 * 这是实现在toolbar上方的文字跑马灯的实现
 * Created by localhost on 2016/11/22.
 */


public class MarqueeTextView extends TextView  {
    private static final String TAG="MarqueeTextView";
    //活动距离
    private  int Scollx=-100;
    private   boolean isStop=false;
    private  float TextWidth;
    private int speed=1;
    //走马灯的速度 默认为1
    public MarqueeTextView(Context context) {
        this(context,null);
    }

    public MarqueeTextView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MarqueeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @SuppressWarnings("ResourceType")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint p=getPaint();
        //获取字符串长度
        TextWidth=p.measureText(getText().toString());
//        scrollTo(-100,0);
//
//      也可以在这里移动

    }


   /* public void run() {
        Scollx=Scollx-speed;
        scrollTo(Scollx,0);
        if(isStop)return;
        Log.i(TAG, "run: "+Scollx+" getWidth"+getWidth());
        if(Scollx<=(-getWidth())){
            scrollTo((int)TextWidth,0);
            Scollx=(int)TextWidth;
        }
        //将
        postDelayed(this,5);
    }*/
    /**
     *
     * 设置跑马灯的停止
     *
      */
    public  void setIsStop(boolean f){
        isStop=f;
    }
    public void start() {
        Scollx = 0;
        startScroll();
    }

    @SuppressWarnings("ResourceType")
    private void startScroll() {
        isStop=false;
//        scrollTo(-100,0); 这个调用的时候是不起作用的
        this.post(new Runnable() {
            @Override
            public void run() {
                    Scollx=Scollx-speed;
                    scrollTo(Scollx,0);//这个不会改变view的真实坐标
                    if(isStop)return;
                    if(Scollx<=(-getWidth())){
                        scrollTo((int)TextWidth,0);
                        Scollx=(int)TextWidth;
                    }
                //无限的循环实现
                    postDelayed(this,5);
                }
        });
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
