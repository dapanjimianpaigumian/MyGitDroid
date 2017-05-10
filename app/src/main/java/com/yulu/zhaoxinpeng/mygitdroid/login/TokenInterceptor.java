package com.yulu.zhaoxinpeng.mygitdroid.login;

import com.yulu.zhaoxinpeng.mygitdroid.commons.LogUtils;
import com.yulu.zhaoxinpeng.mygitdroid.login.model.UserRepo;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/5/10.
 * token拦截器：自定义一个拦截器，将token值添加到所有的retrofit的请求头信息里面
 */

public class TokenInterceptor implements Interceptor{
    /**自定义拦截器的目的：
     * 1. 获取拦截下来的请求
     * 2. 重新构建：将token信息添加到请求头信息里面
     * 3. 继续去执行此请求进而拿到响应
     */

    @Override
    public Response intercept(Chain chain) throws IOException {

        //1.拿到拦截的请求
        Request mRequest = chain.request();

        //2.拿到重新构建的builder
        Request.Builder mBuilder = mRequest.newBuilder();

        //3.往builder里面添加请求头信息：token
        //判断是否已经授权拿到token了
        if (UserRepo.hasAccessToken()) {
            //添加头信息
            mBuilder.header("Authorization","token "+UserRepo.getAccessToken());
        }

        //4.继续去执行请求然后得到响应
        Response mResponse = chain.proceed(mBuilder.build());

        //5.继续做判断
        if (mResponse.isSuccessful()) {
            return mResponse;
        }

        // 处理一些其他情况：如果授权用户不存在或禁止
        if (mResponse.code()==401||mResponse.code()==403) {
            LogUtils.e(mResponse.body().string());
            throw new IOException("授权未成功的，请求有限制");
        }else {
            throw new IOException("响应码："+mResponse.code());
        }
    }
}
