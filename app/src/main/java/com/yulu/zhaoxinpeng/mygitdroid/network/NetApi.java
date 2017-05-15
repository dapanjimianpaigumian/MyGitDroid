package com.yulu.zhaoxinpeng.mygitdroid.network;

import com.yulu.zhaoxinpeng.mygitdroid.content.repositories.model.RepoResult;
import com.yulu.zhaoxinpeng.mygitdroid.content.repositories.subrepoInfo.SubRepoContentResult;
import com.yulu.zhaoxinpeng.mygitdroid.content.user.model.UserResult;
import com.yulu.zhaoxinpeng.mygitdroid.login.model.AccessToken;
import com.yulu.zhaoxinpeng.mygitdroid.login.model.User;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
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

    public static final String BASE_URL = "https://api.github.com";

    // GitHub 网页端注册得到的应用信息
    String CLIENT_ID = "4751c5bcb81bb3c79511";
    String CLIENT_SECRET = "9b1fc62a97ab97883ce1343af35a213b19da79ea";

    String AUTH_SCOPE = "user,public_repo,repo";

    String AUTH_CALLBACK = "dapanjimianpaigumian";

    // 登录页面的url
    String AUTH_URL = "https://github.com/login/oauth/authorize?client_id=" + CLIENT_ID + "&scope=" + AUTH_SCOPE;

    /**
     * 获取授权登录的token值
     *
     * @param ClientId     注册应用得到的
     * @param clientSecret 注册应用得到的
     * @param code         拿到的临时授权码
     * @return
     */
    @FormUrlEncoded
    @Headers({"Accept: application/json"})
    @POST("https://github.com/login/oauth/access_token")
    Call<AccessToken> getOAuthToken(@Field("client_id") String ClientId,
                                    @Field("client_secret") String clientSecret,
                                    @Field("code") String code);

    /**
     * 获取已经授权过的用户信息
     *
     * @return
     */
    @GET("/user")
    Call<User> getUser();

    /**
     * 获取热门仓库的列表
     *
     * @param q    搜索的关键字 language:java 语言类型
     * @param page 页数
     * @return
     */
    @GET("/search/repositories")
    Call<RepoResult> searchRepo(@Query("q") String q, @Query("page") int page);


    /**
     * 获取ReadMe文件
     *
     * @param owner 仓库提供者
     * @param repo  仓库名称
     * @return
     */
    @GET("/repos/{owner}/{repo}/readme")
    Call<SubRepoContentResult> getReadMe(@Path("owner") String owner, @Path("repo") String repo);

    /**
     * 获取MarkDown文件内容：以纯文本返回，WebView加载
     *
     * @param requestBody MarkDown文件
     * @return
     */
    @Headers({"Content-Type:text/plain"})
    @POST("/markdown/raw")
    Call<ResponseBody> markdown(@Body RequestBody requestBody);

    /**
     * 获取热门开发者的请求
     * @param query 查询的条件
     * @param page 查询的页数
     * @return
     */
    @GET("/search/users")
    Call<UserResult> searchUser(@Query("q")String query,@Query("page")int page);
}
