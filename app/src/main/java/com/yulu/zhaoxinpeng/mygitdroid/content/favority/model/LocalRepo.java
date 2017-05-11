package com.yulu.zhaoxinpeng.mygitdroid.content.favority.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/5/11.
 * 本地仓库表
 */

public class LocalRepo {

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

    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("full_name")
    private String fullName;
    @SerializedName("description")
    private String description;
    @SerializedName("stargazers_count")
    private int stargazersCount;
    @SerializedName("forks_count")
    private int forksCount;
    @SerializedName("avatar_url")
    private String avatarUrl;
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

}
