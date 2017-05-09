package com.yulu.zhaoxinpeng.mygitdroid.content.repositories;

import com.hannesdorfmann.mosby.mvp.MvpView;
import com.yulu.zhaoxinpeng.mygitdroid.content.repositories.model.Repo;

import java.util.List;

/**
 * Created by Administrator on 2017/5/8.
 * 仓库列表的接口
 */

public interface SubRepositoryView extends MvpView{

    void stopRefresh();//停止刷新

    void stopLoad();//停止加载

    void showEmptyView(String s);//显示空视图

    void showErrorView();//显示错误视图

    void showToast(String s);//弹吐司

    void addRefreshData(List<Repo> repos);//设置刷新数据

    void addLoadData(List<Repo> repos);//设置加载的数据
}
