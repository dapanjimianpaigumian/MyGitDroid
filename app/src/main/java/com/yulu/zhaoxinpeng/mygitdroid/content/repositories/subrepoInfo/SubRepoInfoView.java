package com.yulu.zhaoxinpeng.mygitdroid.content.repositories.subrepoInfo;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by Administrator on 2017/5/9.
 * 仓库详情页的视图接口
 */

public interface SubRepoInfoView extends MvpView{

    void showProgressbar();//显示进度条

    void hideProgressbar();//隐藏进度条

    void showToast(String s);//弹吐司

    void setHtmlData(String htmlData);//设置网页数据
}
