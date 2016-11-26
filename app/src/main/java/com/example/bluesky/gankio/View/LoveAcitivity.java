package com.example.bluesky.gankio.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.example.bluesky.gankio.Adapters.LoveAdapter;
import com.example.bluesky.gankio.Adapters.OnitemClick;
import com.example.bluesky.gankio.DB.DbManager;
import com.example.bluesky.gankio.DateBean.LoveDate;
import com.example.bluesky.gankio.Presenter.ILovepresenter;
import com.example.bluesky.gankio.Presenter.LovePresenter;
import com.example.bluesky.gankio.R;
import com.example.bluesky.gankio.util.StatusBarCompat;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by localhost on 2016/11/24.
 */

public class LoveAcitivity extends AppCompatActivity implements ILovepresenter, OnitemClick {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.Rv_love)
    RecyclerView RvLove;
    @BindView(R.id.LL_noShow)
    LinearLayout LLNoShow;
    private List<LoveDate> mLoveDates;
    private LoveAdapter arr;
    private LovePresenter lovePresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.love_activity);
        StatusBarCompat.compat(this, ContextCompat.getColor(this, R.color.toolbar));
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        RvLove.setHasFixedSize(true);
        RvLove.setLayoutManager(new LinearLayoutManager(this));
        //必须设置 否则不显示
        RvLove.setItemAnimator(new DefaultItemAnimator());
        arr = new LoveAdapter(this);
        arr.setOnitemClick(this);
        lovePresenter = new LovePresenter(this, this);
        RvLove.setAdapter(arr);
        LLNoShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent();
                intent.setClass(LoveAcitivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void OnforitemClick(int postion) {
        Intent intent = new Intent();
        intent.putExtra(WebActivity.Tag, mLoveDates.get(postion));
        intent.setClass(this, WebActivity.class);
        startActivity(intent);
    }

    @Override
    public void OnforitemLongClik(int postion) {
        popWindow(postion);
    }

    @Override
    public void getData(List<LoveDate> a) {

        if (!a.isEmpty() && a != null) {
            LLNoShow.setVisibility(View.GONE);
            mLoveDates = a;
            arr.addList(mLoveDates);
        } else {
            mLoveDates.clear();
            arr.addList(mLoveDates);
            LLNoShow.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void refreshData(List<LoveDate> a) {

    }

    @Override
    public void isError() {

    }

    @Override
    public void showLoadingview() {

    }

    @Override
    public void hideLoadView() {

    }

    @SuppressLint("NewApi")
    private void popWindow(final int postion) {
        LayoutInflater inflater = LayoutInflater.from(this);//获取一个填充器
        View view = inflater.inflate(R.layout.popwindow, null);//填充我们自定义的布局
        Display display = getWindowManager().getDefaultDisplay();//得到当前屏幕的显示器对象
        Point size = new Point();//创建一个Point点对象用来接收屏幕尺寸信息
        display.getSize(size);//Point点对象接收当前设备屏幕尺寸信息
        int width = size.x;//从Point点对象中获取屏幕的宽度(单位像素)
        int height = size.y;//从Point点对象中获取屏幕的高度(单位像素)
        //创建一个PopupWindow对象，第二个参数是设置宽度的，用刚刚获取到的屏幕宽度乘以2/3，取该屏幕的2/3宽度，从而在任何设备中都可以适配，高度则包裹内容即可，最后一个参数是设置得到焦点
        final PopupWindow popWindow = new PopupWindow(view, 2 * width / 3, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        popWindow.setBackgroundDrawable(new ColorDrawable());//设置PopupWindow的背景为一个空的Drawable对象，如果不设置这个，那么PopupWindow弹出后就无法退出了
        popWindow.setOutsideTouchable(true);//设置是否点击PopupWindow外退出PopupWindow
        WindowManager.LayoutParams params = getWindow().getAttributes();//创建当前界面的一个参数对象
        params.alpha = 0.8f;//设置参数的透明度为0.8，透明度取值为0~1，1为完全不透明，0为完全透明，因为android中默认的屏幕颜色都是纯黑色的，所以如果设置为1，那么背景将都是黑色，设置为0，背景显示我们的当前界面
        getWindow().setAttributes(params);//把该参数对象设置进当前界面中
        popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {//设置PopupWindow退出监听器
            @Override
            public void onDismiss() {//如果PopupWindow消失了，即退出了，那么触发该事件，然后把当前界面的透明度设置为不透明
                WindowManager.LayoutParams params = getWindow().getAttributes();
                params.alpha = 1.0f;//设置为不透明，即恢复原来的界面
                getWindow().setAttributes(params);
            }
        });
        //第一个参数为父View对象，即PopupWindow所在的父控件对象，第二个参数为它的重心，后面两个分别为x轴和y轴的偏移量
        popWindow.showAtLocation(inflater.inflate(R.layout.love_activity, null), Gravity.CENTER, 0, 0);
        Button nodelete = (Button) view.findViewById(R.id.nodelete);
        nodelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popWindow.dismiss();
            }
        });
        Button delete = (Button) view.findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DbManager ba = DbManager.getInstance(getApplicationContext());
                ba.deleteData(mLoveDates.get(postion));
                lovePresenter.freshData();
                popWindow.dismiss();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.lovemenu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            case R.id.share:
                DbManager bd=DbManager.getInstance(this);
                String text=bd.queryAllString();
                Log.i("kdkkd", "onOptionsItemSelected: "+text);
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, text);
                shareIntent.setType("text/plain");
                //设置分享列表的标题，并且每次都显示分享列表
                startActivity(Intent.createChooser(shareIntent, "分享到"));
        }

        return super.onOptionsItemSelected(item);
    }
}
