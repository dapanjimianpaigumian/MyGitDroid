package com.yulu.zhaoxinpeng.mygitdroid.content.gank;

import com.hannesdorfmann.mosby.mvp.MvpView;
import com.yulu.zhaoxinpeng.mygitdroid.content.gank.model.GankItem;

import java.util.List;

/**
 * Created by Administrator on 2017/5/15.
 * 每日干货的视图接口
 */

public interface GankView extends MvpView{

    void setData(List<GankItem> list);//设置数据

    void showEmptyView();//显示 空视图

    void hideEmptyView();///隐藏空视图

    void showToast(String s);//弹吐司
}
