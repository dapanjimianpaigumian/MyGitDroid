package com.yulu.zhaoxinpeng.mygitdroid.content.repositories;

import android.util.Log;

import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;
import com.yulu.zhaoxinpeng.mygitdroid.content.repositories.model.Language;
import com.yulu.zhaoxinpeng.mygitdroid.content.repositories.model.Repo;
import com.yulu.zhaoxinpeng.mygitdroid.content.repositories.model.RepoResult;
import com.yulu.zhaoxinpeng.mygitdroid.network.NetClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/5/8.
 * 仓库列表的业务类：刷新和加载的网络请求
 */

public class SubRepositoryPresenter extends MvpNullObjectBasePresenter<SubRepositoryView> {

    private int mNextPage = 1;
    private Language mLanguage;
    private Call<RepoResult> mRefreshCall;
    private Call<RepoResult> mLoadCall;

    //构造方法
    public SubRepositoryPresenter(Language language) {
        mLanguage = language;
    }

    //解绑视图
    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        if (mRefreshCall != null) mRefreshCall.cancel();
        if (mLoadCall != null) mLoadCall.cancel();
    }

    //刷新的业务逻辑
    public void refresh() {
        mNextPage = 1;
        mRefreshCall = NetClient.getInstance().getNetApi().searchRepo("language:" + mLanguage.getPath(), mNextPage);
        mRefreshCall.enqueue(mRefreshCallback);
    }

    //刷新的回调
    private Callback<RepoResult> mRefreshCallback = new Callback<RepoResult>() {
        @Override
        public void onResponse(Call<RepoResult> call, Response<RepoResult> response) {
            //刷新结束
            getView().stopRefresh();
            if (response.isSuccessful()) {
                RepoResult repoResult = response.body();
                if (repoResult == null) {
                    getView().showEmptyView("刷新数据为null");
                    return;
                }
                if (repoResult.getTotalCount() <= 0) {
                    getView().showEmptyView("没有更多数据可供刷新");
                    return;
                }
                //获取列表数据
                List<Repo> repoList = repoResult.getItems();
                Log.e("repolist", repoList.size() + "");
                if (repoList != null) {
                    getView().addRefreshData(repoList);
                    //第一次刷新后将页数设置为第二页，当第一次加载时直接加载第二页
                    mNextPage=2;
                    return;
                }
            }
            getView().showErrorView();
        }

        @Override
        public void onFailure(Call<RepoResult> call, Throwable t) {
            getView().stopRefresh();
            getView().showToast("刷新数据时失败：" + t.getMessage());
        }
    };

    //加载的业务逻辑
    public void load() {
        mLoadCall = NetClient.getInstance().getNetApi().searchRepo("language:" + mLanguage.getPath(), mNextPage);
        mLoadCall.enqueue(mLoadCallback);
    }

    //加载的回调
    public Callback<RepoResult> mLoadCallback=new Callback<RepoResult>() {
        @Override
        public void onResponse(Call<RepoResult> call, Response<RepoResult> response) {
            getView().stopLoad();
            if (response.isSuccessful()) {
                RepoResult repoResult = response.body();
                if (repoResult==null) {
                    getView().showEmptyView("加载数据为null");
                    return;
                }
                if (repoResult.getTotalCount()<=0) {
                    getView().showEmptyView("没有更多数据可供加载");
                    return;
                }
                List<Repo> repoList = repoResult.getItems();
                if (repoList!=null) {
                    //将加载回来的数据设置给L
                    getView().addLoadData(repoList);
                    //页数的增加的变化
                    mNextPage++;
                    return;
                }
            }

            ///显示错误视图
            getView().showErrorView();
        }

        @Override
        public void onFailure(Call<RepoResult> call, Throwable t) {
            getView().stopLoad();
            getView().showToast("加载数据时失败："+t.getMessage());
        }
    };

}
