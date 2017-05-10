package com.yulu.zhaoxinpeng.mygitdroid.login;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by Administrator on 2017/5/10.
 * 登录的视图接口
 */

public interface LoginView extends MvpView {

    void showToast(String s);

    void showProgressbar();

    void resetWeb();//重新加载，重新登录和请求

    void navigateToMain();//跳转到主页
}
