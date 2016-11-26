package com.example.bluesky.gankio.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.bluesky.gankio.Adapters.MyFragmentPagerAdapter;
import com.example.bluesky.gankio.Adapters.OnitemClick;
import com.example.bluesky.gankio.R;
import com.example.bluesky.gankio.newView.floatingmenu;
import com.example.bluesky.gankio.util.ForTool;
import com.example.bluesky.gankio.util.StatusBarCompat;
import com.example.bluesky.gankio.util.net;

import java.lang.reflect.Method;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements OnitemClick {
    private static final String TAG = "Main";
    @BindView(R.id.menu)
    floatingmenu menu;
    private ForTool mTool = new ForTool();
    private ViewPager mpage;
    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        StatusBarCompat.compat(this, ContextCompat.getColor(this, R.color.toolbar));
        //设置沉浸式状态栏
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        menu.setOnChildClick(this);
        mpage = (ViewPager) findViewById(R.id.mviewpage);
        mpage.setOffscreenPageLimit(3);
        mTabLayout = (TabLayout) findViewById(R.id.mTab);
        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(),
                this);
        mpage.setAdapter(adapter);
        if (!net.isNetworkReachable(this)) {
            ForTool mt = new ForTool();
            mt.ShowShackBar(mTabLayout, MainActivity.this);
          /*  Snackbar.make(mTabLayout,"没有网络",Snackbar.LENGTH_INDEFINITE)
                    .setAction("设置", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            net.GoToNet(MainActivity.this);
                        }
                    })
                    .show();*/
        }
        mTabLayout.setupWithViewPager(mpage);
    }
//显示溢出菜单图片 但图片有了更难看 所以没加图片 这个这是保留以防以后用的
    //这是一个学习的app
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (menu != null) {
            if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
                try{
                    Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e) {

                }
            }
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        mTool.setIconEnable(menu, true);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_settings:
                return true;
            case R.id.writer:
                mTool.WriterShow(this);
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void OnforitemClick(int postion) {
        switch (postion){
            case 1:
                menu.closeAll();
                Intent intent=new Intent();
                intent.setClass(this,LoveAcitivity.class);
                startActivity(intent);
                break;
            case 0:
                menu.closeAll();
                mTool.WriterShow(this).show();
                break;
        }
    }

    @Override
    public void OnforitemLongClik(int postion) {

    }
}
