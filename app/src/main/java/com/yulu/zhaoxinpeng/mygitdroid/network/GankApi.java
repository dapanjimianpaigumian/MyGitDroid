package com.yulu.zhaoxinpeng.mygitdroid.network;

import com.yulu.zhaoxinpeng.mygitdroid.content.gank.model.GankResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Administrator on 2017/5/15.
 * 每日干货的接口
 */

public interface GankApi {

    /**
     * 每日干货接口Api
     *
     * 每日数据： http://gank.io/api/day/年/月/日
     * 例：http://gank.io/api/day/2015/08/06
     * @param year 年
     * @param month 月
     * @param day 日
     * @return
     */
    @GET("http://gank.io/api/day/{year}/{month}/{day}")
    Call<GankResult> getDailyData(
            @Path("year")int year,
            @Path("month")int month,
            @Path("day")int day);
}
