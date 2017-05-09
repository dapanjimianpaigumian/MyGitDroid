package com.yulu.zhaoxinpeng.mygitdroid.network;

import com.yulu.zhaoxinpeng.mygitdroid.content.repositories.model.RepoResult;
import com.yulu.zhaoxinpeng.mygitdroid.content.repositories.subrepoInfo.SubRepoContentResult;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
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

    @GET("/repos/{owner}/{repo}/readme")

    /**
     * 获取ReadMe文件
     * @param owner 仓库提供者
     * @param repo 仓库名称
     * @return
     */
    Call<SubRepoContentResult> getReadMe(@Path("owner")String owner,@Path("repo")String repo);

    /**
     * 获取MarkDown文件内容：以纯文本返回，WebView加载
     * @param requestBody MarkDown文件
     * @return
     */
    @Headers({"Content-Type:text/plain"})
    @POST("/markdown/raw")
    Call<ResponseBody> markdown(@Body RequestBody requestBody);
}
