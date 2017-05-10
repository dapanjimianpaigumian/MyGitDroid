package com.yulu.zhaoxinpeng.mygitdroid.login;

import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;
import com.yulu.zhaoxinpeng.mygitdroid.login.model.AccessToken;
import com.yulu.zhaoxinpeng.mygitdroid.login.model.User;
import com.yulu.zhaoxinpeng.mygitdroid.login.model.UserRepo;
import com.yulu.zhaoxinpeng.mygitdroid.network.NetApi;
import com.yulu.zhaoxinpeng.mygitdroid.network.NetClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/5/10.
 * 登录的业务类
 */

public class LoginPresenter extends MvpNullObjectBasePresenter<LoginView> {

    private Call<AccessToken> tokenCall;
    private Call<User> userCall;

    //解绑视图时，取消网络请求
    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        if (tokenCall != null) tokenCall.cancel();
        if (userCall != null) userCall.cancel();
    }

    public void login(String code) {
        /**
         * 1. 获取token值
         * 2. 获取用户信息
         */

        getView().showProgressbar();

        tokenCall = NetClient.getInstance().getNetApi().getOAuthToken(
                NetApi.CLIENT_ID,
                NetApi.CLIENT_SECRET, code);
        tokenCall.enqueue(mTokenCallback);
    }

    // 获取token的回调
    private Callback<AccessToken> mTokenCallback = new Callback<AccessToken>() {
        @Override
        public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
            if (response.isSuccessful()) {
                AccessToken mAccessToken = response.body();
                //获取返回的token值
                String mToken = mAccessToken.getAccessToken();

                //将token存储在本地仓库，方便调用
                UserRepo.setAccessToken(mToken);

                getView().showProgressbar();

                //获取用户信息
                userCall = NetClient.getInstance().getNetApi().getUser();
                userCall.enqueue(mUserCallback);
            }
        }

        @Override
        public void onFailure(Call<AccessToken> call, Throwable t) {
            // 显示信息
            getView().showToast("请求token失败：" + t.getMessage());
            // 可以在失败之后，让WebView重新加载、重新请求
            getView().resetWeb();
            // 重新展示进度动画
            getView().showProgressbar();
        }
    };

    // 获取user的回调
    private Callback<User> mUserCallback = new Callback<User>() {
        @Override
        public void onResponse(Call<User> call, Response<User> response) {
            if (response.isSuccessful()) {

                // 响应的用户信息存储一下
                User user = response.body();
                UserRepo.setUser(user);

                //提示登录成功
                getView().showToast("登录成功");

                //跳转主页面
                getView().navigateToMain();
            }
        }

        @Override
        public void onFailure(Call<User> call, Throwable t) {

            getView().showToast("请求登录失败：" + t.getMessage());

            getView().resetWeb();

            getView().showProgressbar();
        }
    };
}
