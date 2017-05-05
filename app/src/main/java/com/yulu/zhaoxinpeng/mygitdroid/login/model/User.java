package com.yulu.zhaoxinpeng.mygitdroid.login.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by gqq on 2016/12/8.
 */

// 用户信息的实体类
public class User implements Serializable {

    /**
     * login : octocat
     * id : 1
     * avatar_url : https://github.com/images/error/octocat_happy.gif
     * name : monalisa octocat
     */

    private String login;
    private int id;
    @SerializedName("avatar_url")
    private String avatar;
    private String name;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", id=" + id +
                ", avatar='" + avatar + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
