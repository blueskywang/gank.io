package com.example.bluesky.gankio.Model;

import com.example.bluesky.gankio.DateBean.AIWDate;

/**
 * Created by localhost on 2016/11/16.
 */

public interface LoadDate {
    void getDate(AIWDate a);
    void  iserror(String k);
    void getMoreDate(AIWDate a);
}
