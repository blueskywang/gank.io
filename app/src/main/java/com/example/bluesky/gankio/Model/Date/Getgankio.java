package com.example.bluesky.gankio.Model.Date;

import com.example.bluesky.gankio.DateBean.AIWDate;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by localhost on 2016/11/16.
 */

public interface Getgankio {
    //获取某一类型的数据
    @GET("{type}/{menu}/{time}")
    Call<AIWDate>  GetGankio(@Path("type") String type,@Path("menu")String month,@Path("time")String day);

}
