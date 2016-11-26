package com.example.bluesky.gankio.Model;

import android.content.Context;

import com.example.bluesky.gankio.DB.DbManager;
import com.example.bluesky.gankio.DateBean.LoveDate;

import java.util.List;

/**
 * Created by localhost on 2016/11/26.
 */

public class GetLoveData {
    private LoveLoad mload;
    private DbManager mdbManage;
    public GetLoveData(LoveLoad a, Context c){
        mload=a;
        mdbManage=DbManager.getInstance(c);
        initDate();
    }

    public void initDate() {
        List<LoveDate> m=mdbManage.queryAllList();
        mload.loadDate(m);
    }

}
