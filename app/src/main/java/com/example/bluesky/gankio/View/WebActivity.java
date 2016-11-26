package com.example.bluesky.gankio.View;

import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.bluesky.gankio.Adapters.OnitemClick;
import com.example.bluesky.gankio.DB.DbManager;
import com.example.bluesky.gankio.DateBean.LoveDate;
import com.example.bluesky.gankio.Model.LoadDate;
import com.example.bluesky.gankio.R;
import com.example.bluesky.gankio.newView.MarqueeTextView;
import com.example.bluesky.gankio.newView.floatingmenu;
import com.example.bluesky.gankio.util.ForTool;
import com.example.bluesky.gankio.util.StatusBarCompat;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;

/**
 * Created by localhost on 2016/11/21.
 */

public class WebActivity extends AppCompatActivity implements OnitemClick {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.progress)
    ContentLoadingProgressBar progress;
    @BindView(R.id.Wv_info)
    WebView WvInfo;
    public static final String Tag = "WebActivity";
    @BindView(R.id.toolbartitle)
    MarqueeTextView toolbartitle;
    @BindView(R.id.menu)
    floatingmenu menu;

    private LoveDate murl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.infoweb);
        StatusBarCompat.compat(this, ContextCompat.getColor(this, R.color.toolbar));
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        //取消标题toolbar的title
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //back 图标显示
        Intent intent = getIntent();
        murl = (LoveDate) intent.getParcelableExtra(Tag);
        toolbartitle.setText(murl.getDesc());
        toolbartitle.start();
        initView();
    }

    private void initView() {
        menu.setOnChildClick(this);
        WebSettings settings = WvInfo.getSettings();
        settings.setJavaScriptEnabled(true);//开启js
        settings.setUseWideViewPort(true);//宽度自适应
        settings.setLoadWithOverviewMode(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        WvInfo.setWebViewClient(new WebClient());
        WvInfo.setWebChromeClient(new WebChromeClient());
        WvInfo.loadUrl(murl.getUrl());
    }


    public class WebChromeClient extends android.webkit.WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {//进度
            if (newProgress == 100) {
                progress.setVisibility(GONE);
            } else {
                if (progress.getVisibility() == GONE)
                    progress.setVisibility(View.VISIBLE);
                progress.setProgress(newProgress);
            }
            super.onProgressChanged(view, newProgress);
        }

        //得到URL的标签  webview控件在Android6.0上有一个bug,
// 那就是onReceivedTitle()会调用两次,
// 一次为网页的url,一次为网页真正的title,
// 故这里需要做一个过滤 但标题并不是适合

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && WvInfo.canGoBack()) {
            //设置web可以回退 按返回键
            WvInfo.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private class WebClient extends WebViewClient {
        //如果不用以下的函数 将打开其他的浏览器进行访问
        //这两个函数是一样的用法 只是前一个函数不能用在24 之后了
        @SuppressWarnings("deprecation")
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @TargetApi(Build.VERSION_CODES.N)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(String.valueOf(request.getUrl()));
            return true;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.webmenu, menu);
        return true;
    }

    //调用该控件需要在不用的时候销毁
    @Override
    protected void onPause() {
        super.onPause();
        if (WvInfo != null) WvInfo.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (WvInfo != null) WvInfo.destroy();
    }

    @Override
    public void OnforitemClick(int postion) {
        switch (postion) {
            case 0:
//                ForTool m=new ForTool();
//                m.WriterShow(this).show();
                DbManager db=DbManager.getInstance(this);
                List<LoveDate> l=db.queryAllList();
                Log.i(Tag, "OnforitemClick: "+l.size());
                for (LoveDate k:l){
                  db.deleteData(k);
                }
                break;
            case 1:
                DbManager mdb=DbManager.getInstance(this);
                if(mdb.queryList(murl.getUrl())!=null&&mdb.queryList(murl.getUrl()).size()==0){
                    mdb.IntoData(murl);
                }
               Toast.makeText(this,"亲，可以在收藏中找到了",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void OnforitemLongClik(int postion) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.open:
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse(murl.getUrl());
                intent.setData(content_url);
                startActivity(intent);
                return true;
            case R.id.copy:
                ClipboardManager cmb = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
                cmb.setPrimaryClip(ClipData.newPlainText("text",murl.getUrl()));
                Toast.makeText(this,"已经复制的剪切板了",Toast.LENGTH_SHORT).show();
                return true;
            case android.R.id.home:
                finish();
                return true;
            case R.id.reload:
                WvInfo.reload();
                return true;
        }


        return super.onOptionsItemSelected(item);
    }
}
