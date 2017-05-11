package com.yulu.zhaoxinpeng.mygitdroid.content.favorite.dao;

import com.j256.ormlite.dao.Dao;
import com.yulu.zhaoxinpeng.mygitdroid.content.favorite.model.LocalRepo;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Administrator on 2017/5/11.
 * 本地仓库操作类
 */

public class LocalRepoDao {

    //创建Dao
    private Dao<LocalRepo,Long> mDao;

    //初始化
    public LocalRepoDao(DBHelper dbHelper){
        try {
            mDao=dbHelper.getDao(LocalRepo.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //添加和更新单个数据
    public void createOrUpdate(LocalRepo localRepo){
        try {
            mDao.createOrUpdate(localRepo);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //添加和更新多条数据
    public void createOrUpdate(List<LocalRepo> repoList){
        for (LocalRepo localRepo:
                repoList
             ) {
            createOrUpdate(localRepo);
        }
    }

    //删除一条数据
    public void delete(LocalRepo localRepo){
        try {
            mDao.delete(localRepo);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //删除多条数据
    public void delete(List<LocalRepo> repoList){
        for (LocalRepo localRepo :
                repoList) {
            delete(localRepo);
        }
    }

    //查询所有的数据
    public List<LocalRepo> queryAll(){
        try {
            return mDao.queryForAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //查询未分类的仓库
    public List<LocalRepo> queryNoGroup(){
        try {
            return mDao.queryBuilder().where().isNull(LocalRepo.COLUMN_GROUP_ID).query();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //查询：根据本地仓库表的外键(仓库类别表)的id，查询不同类别的仓库信息
    public List<LocalRepo> queryGroupId(int groupId){
        try {
            return mDao.queryForEq(LocalRepo.COLUMN_GROUP_ID,groupId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
