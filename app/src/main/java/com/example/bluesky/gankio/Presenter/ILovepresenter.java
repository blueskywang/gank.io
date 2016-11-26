package com.example.bluesky.gankio.Presenter;

import com.example.bluesky.gankio.DateBean.LoveDate;

import java.util.List;

/**
 * Created by localhost on 2016/11/26.
 */

public interface ILovepresenter{
    void  getData(List<LoveDate> a);
    void  refreshData(List<LoveDate> a);
    void isError();
    void showLoadingview();
    void hideLoadView();

}
