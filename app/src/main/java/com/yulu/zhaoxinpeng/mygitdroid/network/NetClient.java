package com.yulu.zhaoxinpeng.mygitdroid.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yulu.zhaoxinpeng.mygitdroid.login.TokenInterceptor;

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
    private GankApi mGankApi;
    private final Gson mGson;

    // 私有的构造方法
    private NetClient() {

        mGson  = new GsonBuilder()
                .setLenient()// 设置GSON的非严格模式setLenient()
                .create();
        //设置日志拦截器
        mHttpLoggingInterceptor = new HttpLoggingInterceptor();
        mHttpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        //设置Okhttp客户端
        mOkHttpClient = new OkHttpClient.Builder()
                .addInterceptor(mHttpLoggingInterceptor)
                .addInterceptor(new TokenInterceptor())//添加自定义拦截器
                .build();

        //设置Retrofit客户端
        mRetrofit = new Retrofit.Builder()
                .client(mOkHttpClient)
                .baseUrl(NetApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(mGson))
                .build();
    }

    // 共有的创建方法
    public static synchronized NetClient getInstance() {
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

    //获取Gank的Api
    public GankApi getGankApi(){
        if(mGankApi==null){
            mGankApi=mRetrofit.create(GankApi.class);
        }
        return mGankApi;
    }
}
