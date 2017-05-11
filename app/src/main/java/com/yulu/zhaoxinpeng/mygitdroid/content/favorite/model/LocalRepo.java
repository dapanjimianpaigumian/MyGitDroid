package com.yulu.zhaoxinpeng.mygitdroid.content.favorite.model;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.apache.commons.io.IOUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by Administrator on 2017/5/11.
 * 本地仓库表
 */
@DatabaseTable(tableName = "local_repo")
public class LocalRepo {

    private static List<LocalRepo> mLocalRepoList;
    public static final String COLUMN_GROUP_ID = "group_id";
    /**
     * id : 892275
     * name : retrofit
     * full_name : square/retrofit
     * description : Type-safe HTTP client for Android and Java by Square, Inc.
     * stargazers_count : 13283
     * forks_count : 2656
     * avatar_url : https://avatars.githubusercontent.com/u/82592?v=3
     * group : {"id":1,"name":"网络连接"}
     */

    // 主键，主键不可重复
    @DatabaseField(id = true)
    @SerializedName("id")
    private int id;

    @DatabaseField
    @SerializedName("name")
    private String name;

    //重命名表中的字段
    @DatabaseField(columnName = "full_name")
    @SerializedName("full_name")
    private String fullName;


    @SerializedName("description")
    private String description;

    @DatabaseField(columnName = "star_count")
    @SerializedName("stargazers_count")
    private int stargazersCount;

    @DatabaseField(columnName = "fork_count")
    @SerializedName("forks_count")
    private int forksCount;

    @DatabaseField
    @SerializedName("avatar_url")
    private String avatarUrl;

    @DatabaseField(columnName = COLUMN_GROUP_ID,foreign = true,canBeNull = true)
    @SerializedName("group")
    private RepoGroup group;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStargazersCount() {
        return stargazersCount;
    }

    public void setStargazersCount(int stargazersCount) {
        this.stargazersCount = stargazersCount;
    }

    public int getForksCount() {
        return forksCount;
    }

    public void setForksCount(int forksCount) {
        this.forksCount = forksCount;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public RepoGroup getGroup() {
        return group;
    }

    public void setGroup(RepoGroup group) {
        this.group = group;
    }

    // 对外提供一个方法获取本地的数据
    public static List<LocalRepo> getDefaultLocalRepo(Context context){
        if (mLocalRepoList!=null) {
            return mLocalRepoList;
        }

        try {
            InputStream inputStream = context.getAssets().open("defaultrepos.json");
            String content = IOUtil.toString(inputStream);

            //Gson解析
            mLocalRepoList=new Gson().fromJson(content,new TypeToken<List<LocalRepo>>(){}.getType());
            return mLocalRepoList;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
