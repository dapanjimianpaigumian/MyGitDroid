package com.yulu.zhaoxinpeng.mygitdroid.content.repositories.subrepoInfo;

import android.util.Base64;

import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;
import com.yulu.zhaoxinpeng.mygitdroid.content.repositories.model.Repo;
import com.yulu.zhaoxinpeng.mygitdroid.network.NetClient;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/5/9.
 * 仓库详情页的业务类
 * 1.获取ReadMe文件
 * 2.获取MarkDown文件内容
 */

public class SubRepoInfoPresenter extends MvpNullObjectBasePresenter<SubRepoInfoView> {

    public void getReadMe(Repo repo) {

        //进行网络请求时
        getView().showProgressbar();

        String owner = repo.getOwner().getLogin();
        String repoName = repo.getName();
        Call<SubRepoContentResult> repoContentResultCall = NetClient.getInstance().getNetApi().getReadMe(owner, repoName);
        repoContentResultCall.enqueue(mReadMeCallback);
    }

    // 获取ReadMe的回调
    private Callback<SubRepoContentResult> mReadMeCallback = new Callback<SubRepoContentResult>() {
        @Override
        public void onResponse(Call<SubRepoContentResult> call, Response<SubRepoContentResult> response) {
            // 加密后的文件内容，需要进行base64的解密
            String content = response.body().getContent();

            // Base64.encode(); 加密操作
            // Base64.decode(); 解密操作
            // 解密的操作,返回解密后的byte[]
            byte[] bytes = Base64.decode(content, Base64.DEFAULT);

            // 拿到解密后的数据之后，直接根据当前的内容去获取MarkDown文件的Html形式的纯文本
            RequestBody requestBody = RequestBody.create(null, bytes);
            Call<ResponseBody> responseBodyCall = NetClient.getInstance().getNetApi().markdown(requestBody);
            responseBodyCall.enqueue(mMarkDownCallback);

        }

        @Override
        public void onFailure(Call<SubRepoContentResult> call, Throwable t) {
            getView().hideProgressbar();
            getView().showToast("获取ReadMe请求失败："+t.getMessage());
        }
    };

    // 获取HTML纯文本内容的回调
    private Callback<ResponseBody> mMarkDownCallback = new Callback<ResponseBody>() {
        @Override
        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            //当最终获取到MarkDown文件时，隐藏进度条
            getView().hideProgressbar();
            if (response.isSuccessful()) {
                try {
                    String html_MarkDown = response.body().string();

                    // 拿到真正的数据了，设置给WebView来展示
                    getView().setHtmlData(html_MarkDown);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onFailure(Call<ResponseBody> call, Throwable t) {
            getView().hideProgressbar();
            getView().showToast("获取MarkDown文件内容失败："+t.getMessage());
        }
    };
}
