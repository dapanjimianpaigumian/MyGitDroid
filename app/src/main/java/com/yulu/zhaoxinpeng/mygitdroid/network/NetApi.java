package com.yulu.zhaoxinpeng.mygitdroid.network;

import com.yulu.zhaoxinpeng.mygitdroid.content.repositories.model.RepoResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2017/5/5.
 */
// 服务器的接口构建
public interface NetApi {

    public static final String BASE_URL="https://api.github.com";

    /**
     *获取热门仓库的列表
     * @param q 搜索的关键字 language:java 语言类型
     * @param page 页数
     * @return
     */
    @GET("/search/repositories")
    Call<RepoResult> searchRepo(@Query("q") String q, @Query("page") int page);

}
