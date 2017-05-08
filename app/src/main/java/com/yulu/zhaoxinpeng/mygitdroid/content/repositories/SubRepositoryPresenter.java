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
 * 仓库列表的业务类
 */

public class SubRepositoryPresenter extends MvpNullObjectBasePresenter<SubRepositoryView> {

    private int mNextPage = 1;
    private Language mLanguage;
    private Call<RepoResult> repoResultCall;

    //构造方法
    public SubRepositoryPresenter(Language language) {
        mLanguage = language;
    }

    //解绑视图
    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        if (repoResultCall != null) repoResultCall.cancel();
    }

    //刷新的业务逻辑
    public void refresh() {
        mNextPage = 1;
        repoResultCall = NetClient.getInstance().getNetApi().searchRepo("language:" + mLanguage.getPath(), mNextPage);
        repoResultCall.enqueue(mRefreshCallback);
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
                    getView().showEmptyView();
                    return;
                }
                if (repoResult.getTotalCount() <= 0) {
                    getView().showEmptyView();
                    return;
                }
                //获取列表数据
                List<Repo> repoList = repoResult.getItems();
                Log.e("repolist",repoList.size()+"");
                if (repoList != null) {
                    getView().addRefreshData(repoList);
                    //mNextPage=2;
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
}
