package com.yulu.zhaoxinpeng.mygitdroid.content.favorite.dao;

import com.j256.ormlite.dao.Dao;
import com.yulu.zhaoxinpeng.mygitdroid.content.favorite.model.RepoGroup;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Administrator on 2017/5/11.
 * 仓库类别的操作类
 */

public class RepoGroupDao {

    private final Dao<RepoGroup, Long> mRepoGroupDao;

    public RepoGroupDao(DBHelper dbHelper){
        try {
            mRepoGroupDao = dbHelper.getDao(RepoGroup.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //添加和更新单条数据
    public void createOrUpdate(RepoGroup repoGroup){
        try {
            mRepoGroupDao.createOrUpdate(repoGroup);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //添加和更新多条数据
    public void createOrUpdate(List<RepoGroup> repoGroupList){
        for (RepoGroup repoGroup:
             repoGroupList) {
            createOrUpdate(repoGroup);
        }
    }

    //查询所有的数据
    public List<RepoGroup> queryAll(){
        try {
            return mRepoGroupDao.queryForAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //根据id查询仓库类别
    public RepoGroup queryForId(long id){
        try {
            return mRepoGroupDao.queryForId(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
