package com.yulu.zhaoxinpeng.mygitdroid.content.repositories.model;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.commons.io.IOUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/5/5.
 * 语言模块，负责解析assets目录下的langs.json文件
 */

public class Language implements Serializable{
    private static List<Language> mLanguages;

    /**
     * path : java
     * name : Java
     */

    private String path;
    private String name;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 要用的语言是一个集合，在HotRepoAdapter里面使用，语言的数据在本地的assets里面
     * 读取出来，转换成集合
     * 对外提供一个获取的方法，可以直接拿到集合
     */
    public static List<Language> getLanguages(Context context){
        if (mLanguages!=null){
            return mLanguages;
        }

        try {
            // 读取本地的文件
            InputStream inputStream = context.getAssets().open("langs.json");

            // 通过第三方库将IO流转换为字符串
            String json = IOUtil.toString(inputStream);

            // 转换成集合：Gson
            Gson gson = new Gson();

            // 根据第二个参数给定的类型，转换为集合
            mLanguages = gson.fromJson(json,new TypeToken<List<Language>>(){}.getType());

            // 将数据返回出去
            return mLanguages;

        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
