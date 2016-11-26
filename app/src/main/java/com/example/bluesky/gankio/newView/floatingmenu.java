package com.example.bluesky.gankio.newView;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;

import com.example.bluesky.gankio.Adapters.OnitemClick;
import com.example.bluesky.gankio.R;
import com.example.bluesky.gankio.util.ForTool;


/**
 *
 *
 * 卫星菜单的实现
 * Created by localhost on 2016/10/23.
 */

public class floatingmenu extends FrameLayout {
    private static final String TAG="floatingmenu";
    public static  final  int LEFT_TOP=0;
    public static  final  int LEFT_BOTTOM=1;
    public static  final  int RIGHT_TOP=2;
    public static  final  int RIGHT_BOTTOM=3;
    public static final  int Line_Layout=0;
    public static  final  int Circle_Layout=1;
    public int position;
    public   float distanct;//dp
    public int getLayout() {
        return layout;
    }

    public void setLayout(int layout) {
        this.layout = layout;

    }

    public float getDistanct() {
        return distanct;
    }

    public void setDistanct(float distanct) {
        this.distanct = distanct;
        invalidate();
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;

    }

    public   int layout;
    private OnitemClick monChlidClick;

    public boolean getIsopen() {
        return isopen;
    }

    public void setIsopen(boolean isopen) {
        this.isopen = isopen;
    }

    private boolean isopen=false;

    public floatingmenu(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public floatingmenu(Context context) {
        this(context,null);
    }

    public floatingmenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a=context.getTheme().obtainStyledAttributes(attrs, R.styleable.floatingmenu,defStyleAttr,0);
        position=a.getInt(R.styleable.floatingmenu_mainposition,LEFT_TOP);
        distanct= a.getDimension(R.styleable.floatingmenu_distanct, ForTool.dip2px(context,100));
        layout=a.getInt(R.styleable.floatingmenu_floatingmenulayout,Circle_Layout);
        a.recycle();
       /* initClick();*/
        //初始化过程中没有等到childview
    }
      public  void setOnChildClick(OnitemClick th){
          this.monChlidClick=th;
      }
    private void initClick() {
        int childcount=getChildCount();
        Log.i(TAG, "initClick: "+childcount);
        for(int i=0;i<childcount;i++) {
            final int k=i;
            if (i == childcount-1){
                getChildAt(i).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!getIsopen()) {
                        Log.i(TAG, "onClick: ");
                         openAnimation();
                    }
                    else closeAnimation();
                }
            });
        }
            else {
                getChildAt(i).setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(monChlidClick!=null) {
                            monChlidClick.OnforitemClick(k);
                        }
                    }
                });
            }
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        int childcount=getChildCount();
        Log.i(TAG, "onLayout: "+childcount);
        //适配了每一个view 可以不同大小
        for(int j=0;j<childcount;j++) {
            int height = getChildAt(j).getHeight();
            int width = getChildAt(j).getWidth();
            int l = 0, t = 0, r = 0, b = 0;
            switch (position) {
                case LEFT_TOP:
                    l = getPaddingLeft();
                    t = getPaddingTop();
                    r = width + getPaddingLeft();
                    b = height + getPaddingTop();
                    break;
                case LEFT_BOTTOM:
                    r = width + getPaddingLeft();
                    b = getMeasuredHeight() - getPaddingBottom();
                    l = getPaddingLeft();
                    t = getMeasuredHeight() - height - getPaddingBottom();
                    break;
                case RIGHT_TOP:
                    r = getMeasuredWidth() - getPaddingRight();
                    l = getMeasuredWidth() - width - getPaddingRight();
                    t = getPaddingTop();
                    b = height + getPaddingTop();
                    break;
                case RIGHT_BOTTOM:
                    r = getMeasuredWidth() - getPaddingRight();
                    b = getMeasuredHeight() - getPaddingBottom();
                    l = getMeasuredWidth() - width - getPaddingRight();
                    t = getMeasuredHeight() - height - getPaddingBottom();
                    break;
            }
            for (int i = 0; i < childcount; i++) {
                View view = getChildAt(i);
                view.layout(l, t, r, b);
            }
        }
        initClick();
    }
         private void openAnimation(){
             int count=getChildCount();
             isopen=true;
             openMenu(count);
             for(int i=0;i<count-1;i++){
                /* ObjectAnimator openAnimator=ObjectAnimator.ofFloat(getChildAt(i),"translationY",0f,200*(i+1));
                 Log.i(TAG, "openAnimation: "+i);*/
                 if(layout==Line_Layout) {

                         if(position==LEFT_TOP||position==RIGHT_TOP){
                             PropertyValuesHolder sx = PropertyValuesHolder.ofFloat("scaleX", 0f, 1f);
                             PropertyValuesHolder sy = PropertyValuesHolder.ofFloat("scaleY", 0f, 1f);
                             PropertyValuesHolder translationUp = PropertyValuesHolder.ofFloat("translationY", 0f, distanct * (i + 1));
                             PropertyValuesHolder tras = PropertyValuesHolder.ofFloat("rotationX", 0f, 360f);
                             PropertyValuesHolder trasy = PropertyValuesHolder.ofFloat("rotationY", 0f, 360f);
                             ObjectAnimator openAnimator = ObjectAnimator.ofPropertyValuesHolder(getChildAt(i), sx, sy, translationUp, tras, trasy).setDuration(500);
                             openAnimator.setInterpolator(new AccelerateInterpolator());
                             openAnimator.setStartDelay(300 * i);
                             openAnimator.start();
                             }
                     else {
                             PropertyValuesHolder sx = PropertyValuesHolder.ofFloat("scaleX", 0f, 1f);
                             PropertyValuesHolder sy = PropertyValuesHolder.ofFloat("scaleY", 0f, 1f);
                             PropertyValuesHolder translationUp = PropertyValuesHolder.ofFloat("translationY", 0f, -distanct*(i+1));
                             PropertyValuesHolder tras = PropertyValuesHolder.ofFloat("rotationX", 0f, 360f);
                             PropertyValuesHolder trasy = PropertyValuesHolder.ofFloat("rotationY", 0f, 360f);
                             ObjectAnimator openAnimator = ObjectAnimator.ofPropertyValuesHolder(getChildAt(i), sx, sy, translationUp, tras, trasy).setDuration(500);
                             openAnimator.setInterpolator(new AccelerateInterpolator());
                             openAnimator.setStartDelay(300 * i);
                             openAnimator.start();
                         }

                 }
                 else{
                     circleAnimator();
                 }
             }
             setIsopen(true);
         }

    private void openMenu(int n) {
        View v=getChildAt(n-1);
        ObjectAnimator scl=new ObjectAnimator().ofFloat(v,"rotation",0,-155,-135);
        scl.setDuration(1000);
        scl.start();
    }


    private  void closeAnimation() {
             int count = getChildCount();
             closeMenu(count);
             isopen=false;
             if (layout == Line_Layout) {
                 if(position==LEFT_TOP||position==RIGHT_TOP) {
                     for (int i = 0; i < count - 1; i++) {
                         PropertyValuesHolder sx = PropertyValuesHolder.ofFloat("scaleX", 1f, 0f);
                         PropertyValuesHolder sy = PropertyValuesHolder.ofFloat("scaleY", 1f, 0f);
                         PropertyValuesHolder translationUp = PropertyValuesHolder.ofFloat("translationY", distanct * (i + 1), 0f);
                         PropertyValuesHolder tras = PropertyValuesHolder.ofFloat("rotationX", 0f, 360f);
                         PropertyValuesHolder trasy = PropertyValuesHolder.ofFloat("rotationY", 0f, 360f);
                         ObjectAnimator openAnimator = ObjectAnimator.ofPropertyValuesHolder(getChildAt(i), sx, sy, translationUp, tras, trasy).setDuration(500);
                         openAnimator.setInterpolator(new AccelerateInterpolator());
                         openAnimator.setStartDelay(300 * i);
                         openAnimator.start();
                     }
                 }
                     else{
                         for (int i = 0; i < count - 1; i++) {
                             PropertyValuesHolder sx = PropertyValuesHolder.ofFloat("scaleX", 1f, 0f);
                             PropertyValuesHolder sy = PropertyValuesHolder.ofFloat("scaleY", 1f, 0f);
                             PropertyValuesHolder translationUp = PropertyValuesHolder.ofFloat("translationY", -distanct*(i+1), 0f);
                             PropertyValuesHolder tras = PropertyValuesHolder.ofFloat("rotationX", 0f, 360f);
                             PropertyValuesHolder trasy = PropertyValuesHolder.ofFloat("rotationY", 0f, 360f);
                             ObjectAnimator openAnimator = ObjectAnimator.ofPropertyValuesHolder(getChildAt(i), sx, sy, translationUp, tras, trasy).setDuration(500);
                             openAnimator.setInterpolator(new AccelerateInterpolator());
                             openAnimator.setStartDelay(300 * i);
                             openAnimator.start();
                         }
                 }

             }else{
                 ClosecircleAnimator();
             }
             setIsopen(false);
         }

    private void closeMenu(int count) {
        View v=getChildAt(count-1);
        ObjectAnimator csr=new ObjectAnimator().ofFloat(v,"rotation",-135,20,0);
        csr.setDuration(1000);
        csr.start();
    }

    private void circleAnimator() {
        int count = getChildCount() - 1;
        for(int i=0;i<count;i++) {
            PropertyValuesHolder sx = PropertyValuesHolder.ofFloat("scaleX", 0f, 1f);
            PropertyValuesHolder sy = PropertyValuesHolder.ofFloat("scaleY", 0f, 1f);
            PropertyValuesHolder rx= PropertyValuesHolder.ofFloat("rotationX", 0f, 360f);
            PropertyValuesHolder ry = PropertyValuesHolder.ofFloat("rotationY", 0f, 360f);
            float offsetX=0;
            float offsetY=0;
            int cl = (int) (distanct * Math.sin(Math.PI / 2 / (count - 1) * i));
            int ct = (int) (distanct * Math.cos(Math.PI / 2 / (count - 1) * i));
            int Yflag=1;
            int Xflag=1;
            //弧度计算
            switch (position) {
                case LEFT_TOP:
                    break;
                case LEFT_BOTTOM:
                    Yflag=-1;
                    break;
                case RIGHT_TOP:
                    Xflag=-1;
                    break;
                case RIGHT_BOTTOM:
                    Xflag=-1;
                    Yflag=-1;
                    break;
            }
            PropertyValuesHolder trY = PropertyValuesHolder.ofFloat("translationY", 0f,cl*Yflag);

            PropertyValuesHolder trX = PropertyValuesHolder.ofFloat("translationX", 0f, ct*Xflag);
            ObjectAnimator a= ObjectAnimator.ofPropertyValuesHolder(getChildAt(i),rx,ry,trX,trY,sy,sx).
                    setDuration(500);
            a.setInterpolator(new LinearInterpolator());
            a.setStartDelay(300*i);
            a.start();
        }
    }
    private void ClosecircleAnimator() {
        int count = getChildCount() - 1;
        for(int i=0;i<count;i++) {
            PropertyValuesHolder sx = PropertyValuesHolder.ofFloat("scaleX", 0f, 1f);
            PropertyValuesHolder sy = PropertyValuesHolder.ofFloat("scaleY", 0f, 1f);
            PropertyValuesHolder rx = PropertyValuesHolder.ofFloat("rotationX", 0f, 360f);
            PropertyValuesHolder ry = PropertyValuesHolder.ofFloat("rotationY", 0f, 360f);
            float offsetX=0;
            float offsetY=0;
            int cl = (int) (distanct * Math.sin(Math.PI / 2 / (count - 1) * i));
            int ct = (int) (distanct * Math.cos(Math.PI / 2 / (count - 1) * i));
            int Yflag=1;
            int Xflag=1;
            //弧度计算
            switch (position) {
                case LEFT_TOP:
                    break;
                case LEFT_BOTTOM:
                    Yflag=-1;
                    break;
                case RIGHT_TOP:
                    Xflag=-1;
                    break;
                case RIGHT_BOTTOM:
                    Xflag=-1;
                    Yflag=-1;
                    break;
            }
            PropertyValuesHolder trY= PropertyValuesHolder.ofFloat("translationY", cl*Yflag,0f);

            PropertyValuesHolder trX = PropertyValuesHolder.ofFloat("translationX", ct*Xflag, 0f);
            ObjectAnimator a= ObjectAnimator.ofPropertyValuesHolder(getChildAt(i),rx,ry,trX,trY,sy,sx).
                    setDuration(500);
            a.setInterpolator(new LinearInterpolator());
            a.setStartDelay(300*i);
            a.start();
        }
    }

    public void setMainView(View v){
        addView(v,getChildCount());

    }
    public void closeAll(){
        if(isopen){
            closeAnimation();
        }
    }
}
