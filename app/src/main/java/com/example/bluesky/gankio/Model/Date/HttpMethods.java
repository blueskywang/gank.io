package com.example.bluesky.gankio.Model.Date;

import android.content.Context;
import android.util.Log;

import com.example.bluesky.gankio.DateBean.AIWDate;
import com.example.bluesky.gankio.Model.LoadDate;
import com.example.bluesky.gankio.util.net;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by localhost on 2016/11/15.
 */

public class HttpMethods {
    public static final String BASE_URL = "http://gank.io/api/data/";
    private  static final String TAG="HttpMethods";
    Getgankio service;
    LoadDate mloadDate;
    private Context context;
    private Retrofit retrofit;

    private String[] tab = new String[]{"Android", "iOS", "前端", "拓展资源", "瞎推荐"};
    public  HttpMethods(LoadDate m, Context context,int k) {
        this.context=context;
        initHttp();
        mloadDate=m;

       /* Retrofit retrofit = new Retrofit.Builder()  //01:获取Retrofit对象
                .baseUrl(BASE_URL) //02采用链式结构绑定Base url
                .addConverterFactory(GsonConverterFactory.create())
                .build();//03执行操作
            mloadDate=m;
            service = retrofit.create(Getgankio.class);//04获取API接口的实现类的实例对象
            getDate();*/
        getDate(k);
    }
    private void initHttp() {
        File httpCacheDirectory = new File(context.getCacheDir(), "responses");
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(httpCacheDirectory, cacheSize);
        OkHttpClient httpClient=new OkHttpClient.Builder()
                .addInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
                .build();
       retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(Getgankio.class);//04获取API接口的实现类的实例对象
    }

    public void getDate(int kind){
        String kindname=tab[0];
       switch (kind){
           case 0:kindname=tab[0];break;
           case 1 :kindname=tab[1];break;
           case 2:kindname=tab[2];break;
           case 3:kindname=tab[3];break;
           case 4:kindname=tab[4];break;
       }
        Log.i(TAG, "getDate: "+kind);
        Log.i(TAG, "getDate: "+kindname);
        Call<AIWDate> k=service.GetGankio(kindname,"10","1");
       k.enqueue(new Callback<AIWDate>() {
           @Override
           public void onResponse(Call<AIWDate> call, Response<AIWDate> response) {
               Log.i(TAG, "onResponse: "+response.body());
               mloadDate.getDate(response.body());
           }

           @Override
           public void onFailure(Call<AIWDate> call, Throwable t) {
               mloadDate.iserror(t.toString());
               Log.i(TAG, "onFailure: "+t.toString());
           }
       });
    }
    Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR =new Interceptor() {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            CacheControl.Builder cacherControl =new CacheControl.Builder();
            cacherControl.maxAge(0, TimeUnit.SECONDS);
            cacherControl.maxStale(365,TimeUnit.DAYS);
            CacheControl cacheC=cacherControl.build();
            Request request=chain.request();
            if(!net.isNetworkReachable(context)){
                request=request.newBuilder()
                        .cacheControl(cacheC)
                        .build();
            }
            okhttp3.Response response=chain.proceed(request);
            if(net.isNetworkReachable(context)){
                int maxAge=0;
                return response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public ,max-age=" + maxAge)
                        .build();
            }else {
                int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
                return response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
        }
    };
}

