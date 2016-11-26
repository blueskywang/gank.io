package com.example.bluesky.gankio.Presenter;

import android.content.Context;
import android.util.Log;

import com.example.bluesky.gankio.DateBean.LoveDate;
import com.example.bluesky.gankio.Model.GetLoveData;
import com.example.bluesky.gankio.Model.LoveLoad;

import java.util.List;

/**
 * Created by localhost on 2016/11/26.
 */

public class LovePresenter implements LoveLoad {
    private ILovepresenter mLovePresent;
    private GetLoveData met;
    public   LovePresenter(ILovepresenter d, Context c){
        mLovePresent=d;
        met=new GetLoveData(this,c);
     }
    @Override
    public void loadDate(List<LoveDate> a) {

        if(a.isEmpty())mLovePresent.isError();
        mLovePresent.getData(a);
    }
    public void freshData(){
        met.initDate();
    }

}
