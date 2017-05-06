package com.yulu.zhaoxinpeng.mygitdroid.network;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2017/5/5.
 * 网络模块
 */

public class NetClient {

    private static NetClient mNetClient;
    private OkHttpClient mOkHttpClient;
    private HttpLoggingInterceptor mHttpLoggingInterceptor;
    private Retrofit mRetrofit;
    private NetApi mNetApi;

    private NetClient() {

        //设置日志拦截器
        mHttpLoggingInterceptor = new HttpLoggingInterceptor();
        mHttpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        //设置Okhttp客户端
        mOkHttpClient = new OkHttpClient.Builder()
                .addInterceptor(mHttpLoggingInterceptor)
                .build();

        //设置Retrofit客户端
        mRetrofit = new Retrofit.Builder()
                .client(mOkHttpClient)
                .baseUrl(NetApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private static synchronized NetClient getInstance() {
        if (mNetClient == null) {
            mNetClient = new NetClient();
        }
        return mNetClient;
    }

    // 获取接口API
    public synchronized NetApi getNetApi() {
        if (mNetApi == null) {
            mNetApi = mRetrofit.create(NetApi.class);
        }
        return mNetApi;
    }
}
