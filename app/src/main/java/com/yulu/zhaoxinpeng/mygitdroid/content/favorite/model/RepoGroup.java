package com.yulu.zhaoxinpeng.mygitdroid.content.favorite.model;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.misc.IOUtils;
import com.j256.ormlite.table.DatabaseTable;

import org.apache.commons.io.IOUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by gqq on 2017/5/11.
 */
// 指定他是数据库表
@DatabaseTable(tableName = "repostiory_group")
public class RepoGroup {

    private static List<RepoGroup> repoGroupList;

    // 指定他是数据库表里面的一个字段
    @DatabaseField(id = true)// true 指定他是主键id，主键是唯一的标识，不能重复的
    private int id;

    @DatabaseField(columnName = "NAME")// 修改在数据库表里面显示的字段名
    private String name;

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

    // 对外提供一个方法获取本地的数据
    public static List<RepoGroup> getDefaultGroup(Context context){
        if (repoGroupList!=null) {
            return repoGroupList;
        }

        try {
            //读取本地资源
            InputStream inputStream = context.getAssets().open("repogroup.json");
            //输入流转换为字符串
            String content = IOUtil.toString(inputStream);
            //利用Gson解析
            repoGroupList= new Gson().fromJson(content,new TypeToken<List<RepoGroup>>(){}.getType());
            return repoGroupList;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
