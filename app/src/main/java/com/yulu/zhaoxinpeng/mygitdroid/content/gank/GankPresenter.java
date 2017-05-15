package com.yulu.zhaoxinpeng.mygitdroid.content.gank;

import android.util.Log;

import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;
import com.yulu.zhaoxinpeng.mygitdroid.content.gank.model.GankItem;
import com.yulu.zhaoxinpeng.mygitdroid.content.gank.model.GankResult;
import com.yulu.zhaoxinpeng.mygitdroid.network.NetClient;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/5/15.
 * 每日干货的业务类
 */

public class GankPresenter extends MvpNullObjectBasePresenter<GankView>{

    private Call<GankResult> gankCall;

    //解绑视图
    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        if(gankCall!=null) gankCall.cancel();
    }

    //获取数据的业务逻辑
    public void getGank(Date date){

        //拿到日历类
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;//月数是从0开始的
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        gankCall = NetClient.getInstance().getGankApi().getDailyData(year, month, day);
        gankCall.enqueue(gankResultCallback);
    }

    //请求回调
    private Callback<GankResult> gankResultCallback = new Callback<GankResult>() {
        @Override
        public void onResponse(Call<GankResult> call, Response<GankResult> response) {
            if (response.isSuccessful()) {
                GankResult gankResult = response.body();
                if (gankResult==null) {
                    getView().showToast("数据是空的");
                    return;
                }
                if (gankResult.isError()||gankResult.getResult()==null||
                        gankResult.getResult().getAndroidItems()==null||
                        gankResult.getResult().getAndroidItems().isEmpty()) {
                    getView().showEmptyView();
                    return;
                }

                //排除掉错误的情况且取到数据后
                List<GankItem> itemList = gankResult.getResult().getAndroidItems();
                //隐藏空视图：之前如果发生错误，会展示空视图；如果此时获取到了数据，那么就隐藏掉之前的空视图
                getView().hideEmptyView();
                //设置数据
                getView().setData(itemList);
            }
        }

        @Override
        public void onFailure(Call<GankResult> call, Throwable t) {
            //弹一个请求失败的吐司
            getView().showToast("干货请求失败:"+t.getMessage());
        }
    };
}
