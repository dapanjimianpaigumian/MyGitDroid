package com.yulu.zhaoxinpeng.mygitdroid.content.favorite.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.yulu.zhaoxinpeng.mygitdroid.content.favorite.model.LocalRepo;
import com.yulu.zhaoxinpeng.mygitdroid.content.favorite.model.RepoGroup;

import java.sql.SQLException;

/**
 * Created by Administrator on 2017/5/11.
 * ORMlite帮助类
 */

public class DBHelper extends OrmLiteSqliteOpenHelper {

    private static final String DB_NAME="repo_favorite.db";
    private static final int DB_VERSION=1;
    private static DBHelper mDbHelper;
    private Context context;

    //自定义私有构造方法
    private DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context=context;
    }

    //创建单例方法
    public static synchronized DBHelper getInstance(Context context){
        if(mDbHelper==null){
            mDbHelper = new DBHelper(context.getApplicationContext());
        }
        return mDbHelper;
    }

    //创建表
    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        // 对表进行创建、一创建就往表里面填充数据
        // 表的工具类：可以对表进行创建、清空和删除等
        try {
            // 仓库类别表和本地仓库表的创建
            TableUtils.createTableIfNotExists(connectionSource, RepoGroup.class);
            TableUtils.createTableIfNotExists(connectionSource, LocalRepo.class);

            // 将本地的数据直接填充到数据库表里面
            new RepoGroupDao(this).createOrUpdate(RepoGroup.getDefaultGroup(context));
            new LocalRepoDao(this).createOrUpdate(LocalRepo.getDefaultLocalRepo(context));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //更新表
    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        // 对表进行更新：先删除再创建
        try {
            // 仓库类别表的删除
            TableUtils.dropTable(connectionSource,RepoGroup.class,true);
            // 本地仓库表的删除
            TableUtils.dropTable(connectionSource,LocalRepo.class,true);

            //重新创建
            onCreate(database,connectionSource);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
