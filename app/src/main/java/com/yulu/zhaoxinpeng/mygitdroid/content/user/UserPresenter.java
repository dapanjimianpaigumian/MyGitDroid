package com.yulu.zhaoxinpeng.mygitdroid.content.user;

import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;
import com.yulu.zhaoxinpeng.mygitdroid.content.user.model.UserResult;
import com.yulu.zhaoxinpeng.mygitdroid.login.model.User;
import com.yulu.zhaoxinpeng.mygitdroid.network.NetClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/5/15.
 * 热门开发者的业务类
 */

public class UserPresenter extends MvpNullObjectBasePresenter<UserView>{

    private int mNextPage=1;

    //刷新的业务
    public void refresh(){
        mNextPage=1;
        Call<UserResult> refreshCall = NetClient.getInstance().getNetApi().searchUser("followers:>1000", mNextPage);
        refreshCall.enqueue(refreshCallback);

    }

    private Callback<UserResult> refreshCallback=new Callback<UserResult>() {
        @Override
        public void onResponse(Call<UserResult> call, Response<UserResult> response) {
            getView().stopMoreData();

            if (response.isSuccessful()) {
                UserResult userResult = response.body();
                if (userResult==null) {
                    //显示错误视图
                    getView().showErrorView();
                    return;
                }
                List<User> userList = userResult.getUsers();
                if (userList==null) {
                    getView().showEmptyView();
                    return;
                }
                getView().addRefreshData(userList);
                mNextPage=2;
                return;
            }
            getView().showErrorView();
        }

        @Override
        public void onFailure(Call<UserResult> call, Throwable t) {
            getView().stopMoreData();
            getView().showErrorView();
            getView().showToast("刷新数据请求失败："+t.getMessage());
        }
    };

    //加载的业务
    public void load(){
        Call<UserResult> loadCall = NetClient.getInstance().getNetApi().searchUser("followers:>1000", mNextPage);
        loadCall.enqueue(loadCallback);
    }

    private Callback<UserResult> loadCallback=new Callback<UserResult>() {
        @Override
        public void onResponse(Call<UserResult> call, Response<UserResult> response) {
            getView().stopMoreData();
            if (response.isSuccessful()) {
                UserResult body = response.body();
                if (body==null) {
                    getView().showErrorView();
                    return;
                }
                List<User> userList = body.getUsers();
                if (userList==null) {
                    getView().showEmptyView();
                    return;
                }
                getView().addLoadData(userList);
                mNextPage++;
                return;
            }
        }

        @Override
        public void onFailure(Call<UserResult> call, Throwable t) {
            getView().stopMoreData();
            getView().showErrorView();
            getView().showToast("加载数据请求失败："+t.getMessage());
        }
    };
}
