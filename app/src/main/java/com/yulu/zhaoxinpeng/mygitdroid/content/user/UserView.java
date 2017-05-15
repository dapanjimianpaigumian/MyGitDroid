package com.yulu.zhaoxinpeng.mygitdroid.content.user;

import com.hannesdorfmann.mosby.mvp.MvpView;
import com.yulu.zhaoxinpeng.mygitdroid.login.model.User;

import java.util.List;

/**
 * Created by Administrator on 2017/5/15.
 * 热门开发者的视图接口
 */

public interface UserView extends MvpView{

    void addRefreshData(List<User> users);//添加刷新的数据

    void addLoadData(List<User> users);//添加加载的数据

    void stopMoreData();//停止刷新和加载数据

    void showEmptyView();//显示空视图

    void showErrorView();//显示错误视图

    void showToast(String s);//弹吐司
}
