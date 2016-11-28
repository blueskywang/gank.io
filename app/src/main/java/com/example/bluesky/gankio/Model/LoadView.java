package com.example.bluesky.gankio.Model;

import com.example.bluesky.gankio.DateBean.AIWDate;

/**
 * Created by localhost on 2016/11/17.
 */

public interface LoadView {
    void loadviewshow();
    void hideLoading();
    void showFailedError(String g);
    void refreshlist(AIWDate m);
    void LoadMore(AIWDate m);
    void getImageurl(String url);

}
