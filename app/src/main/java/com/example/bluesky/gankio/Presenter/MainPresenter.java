package com.example.bluesky.gankio.Presenter;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.example.bluesky.gankio.DateBean.AIWDate;
import com.example.bluesky.gankio.Model.Date.HttpMethods;
import com.example.bluesky.gankio.Model.LoadDate;
import com.example.bluesky.gankio.View.LoadView;

/**
 * Created by localhost on 2016/11/17.
 */

public class MainPresenter implements LoadDate {
    LoadView mview;
    private static  final String TAG="MainPresenter";
    private Handler mHandler = new Handler();
    HttpMethods http;
    private  int ke;
    public MainPresenter(LoadView a, Context context,int kind){
        mview=a;
        ke=kind;
        http=new HttpMethods(this,context,kind);
    }
    //当没有网络的时候，利用该方法重新访问网络、、当也可以开启服务实时监控，一旦有网络立即开启
    public void refreshDate(){
        http.getDate(ke);
    }
    @Override
    public void getDate(AIWDate a) {
        Log.i(TAG, "getDate: "+a.isError());
        mview.loadviewshow();
        mview.refreshlist(a);
        mview.hideLoading();
    }

    @Override
    public void iserror(String k) {
        mview.showFailedError(k);
    }

    @Override
    public void getMoreDate(AIWDate a) {
        mview.LoadMore(a);
    }
}
