package com.yulu.zhaoxinpeng.mygitdroid.content.favority.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by gqq on 2017/5/11.
 */
// 指定他是数据库表
@DatabaseTable(tableName = "repostiory_group")
public class RepoGroup {

    // 指定他是数据库表里面的一个字段
    @DatabaseField(id = true)// 指定他是主键id，主键是唯一的标识，不能重复的
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
}
