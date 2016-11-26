package com.example.bluesky.gankio.newView;

import android.animation.AnimatorSet;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import com.example.bluesky.gankio.R;


/**
 *
 *
 * surfaceView 实现的一个加载动画
 * 但切记，下载该开源的项目的开发者
 * 不用像我这样用 你会发现APP其实在加载的时候不一定能停下来
 * 之所以没用更换这个view 主要是考虑这个APP为学习用途，那么这个是不影响实现的
 * 而且该view可以作为surfaceview 的实现学习
 * Created by localhost on 2016/11/19.
 */

public class LoadingView extends SurfaceView implements Runnable,SurfaceHolder.Callback{

    private Context mContext;
    private SurfaceHolder holder;//增加对于线程的控制
    private Canvas mcanvas;
    private boolean isdraw;
    private Paint mpaint;
    private Paint mthPaint;
    private Path mpath;
    private AnimatorSet mSet=null;
    private float startX;
    private float startY;
    private float ofx;
    private float ofy;
    float bi;
    private float eny;
    private int speed=5;
    private  float PaintWidth;
    //标志符
    private final  int leftTopdown=0;//从开始向下
    private final  int leftDownTop=1;//从左下方向上
    private final  int righttop=2;//从右上方向下
    private final  int returnleft=3;//回到原点
    private final  int goback=4;
    private  int sign=0;
    private int painColor;
    public LoadingView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LoadingView(Context context) {
        this(context,null);
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext=context;
        TypedArray a=context.obtainStyledAttributes(attrs,R.styleable.LoadingView,defStyleAttr,0);
        speed=a.getInt(R.styleable.LoadingView_Loadspeed,10);
        PaintWidth=a.getDimension(R.styleable.LoadingView_Loadwidth,8);
        painColor=a.getColor(R.styleable.LoadingView_LoadColor,ContextCompat.getColor(context,R.color.green));
        initView();
    }
//初始化操作
    private void initView() {
        setZOrderOnTop(true);//使surfaceview放到最顶层
        getHolder().setFormat(PixelFormat.TRANSLUCENT);//使窗口支持透明度
        holder=getHolder();
        holder.addCallback(this);
       // setKeepScreenOn(true);//设置屏幕长亮
        mthPaint=new Paint();
        mthPaint.setAntiAlias(true);
        mthPaint.setStyle(Paint.Style.STROKE);
        mthPaint.setColor(painColor);
        mpath=new Path();
    }

    @Override
    public void run() {
        startX=(getWidth()/8);
        startY=(getHeight()/8);
        ofx=startX;
        ofy=startY;
        eny= (float) (getHeight()*7/8);
         bi= (float) ((getHeight()*0.75)/(getWidth()*0.72));
        mthPaint.setStrokeWidth(PaintWidth);
        mthPaint.setStrokeCap(Paint.Cap.ROUND);
        mpath.moveTo(startX,startY);
        while (isdraw)
         doDraw();
    }
//实现view的绘制
    private void doDraw() {
        try {
            mcanvas = holder.lockCanvas();
            mcanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);//绘制透明色
            //在多种判断是switch 要比if好用
            //画布颜色默认是黑s色
            switch (sign){
                case leftTopdown:

                    ofy = ofy + speed;
                    if (ofy >(eny)){
                        sign=leftDownTop;
                    }
                    mpath.lineTo(ofx, ofy);
                    break;
                //下转折往上的时候
                case leftDownTop:
                        ofx=ofx+speed;
                        ofy=(float)(ofy-speed*bi);
                        mpath.lineTo(ofx,ofy);
                    if(ofy<=startY)sign=righttop;
                    break;
                case righttop:
                    ofy=ofy+speed;
                    if(ofy>=eny)sign=returnleft;
                    mpath.lineTo(ofx,ofy);
                    break;
                case returnleft:
                    ofx=ofx-speed;
                    ofy=ofy-speed*bi;
                    if(ofy<=startY)sign=goback;
                    Log.i("ofy", "doDraw: "+ofy);
                    mpath.lineTo(ofx,ofy);
                    break;
                case goback:
                    mpath.reset();
                    mpath.moveTo(startX,startY);
                    ofx=startX;
                    ofy=startY;
                    sign=leftTopdown;
            }
            mcanvas.drawPath(mpath,mthPaint);
            }catch(Exception e){
                e.printStackTrace();
            }finally{
                if (mcanvas != null)
                    holder.unlockCanvasAndPost(mcanvas);
            }


    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
            isdraw=true;
           new Thread(this).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                isdraw=false;
    }
    public void setIsDraw(boolean f){
        isdraw=f;
    }
}
