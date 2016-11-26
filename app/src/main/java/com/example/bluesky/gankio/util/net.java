package com.example.bluesky.gankio.util;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.File;

import okhttp3.Cache;
import okhttp3.OkHttpClient;

/**
 * Created by localhost on 2016/11/15.
 */

public class net {
    /***
     *
     * @param context
     * @return
     */
    public static Boolean isNetworkReachable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo current = cm.getActiveNetworkInfo();
        if (current == null) {
            return false;
        }
        return (current.isAvailable());
    }
    public static void GoToNet(Context context){
        Intent intent = null;
        // 先判断当前系统版本
        if(android.os.Build.VERSION.SDK_INT > 10){  // 3.0以上
            intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
        }else{
            intent = new Intent();
            intent.setClassName("com.android.settings", "com.android.settings.WirelessSettings");
        }
        context.startActivity(intent);
    }

}
