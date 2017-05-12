package com.yulu.zhaoxinpeng.mygitdroid.content.favorite;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.yulu.zhaoxinpeng.mygitdroid.R;
import com.yulu.zhaoxinpeng.mygitdroid.content.favorite.dao.DBHelper;
import com.yulu.zhaoxinpeng.mygitdroid.content.favorite.dao.LocalRepoDao;
import com.yulu.zhaoxinpeng.mygitdroid.content.favorite.dao.RepoGroupDao;
import com.yulu.zhaoxinpeng.mygitdroid.content.favorite.model.LocalRepo;
import com.yulu.zhaoxinpeng.mygitdroid.content.favorite.model.RepoGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/5/11.
 * 我的收藏
 */

public class FavorityFragment extends Fragment {

    @BindView(R.id.tvGroupType)
    TextView mTvGroupType;
    @BindView(R.id.btnFilter)
    ImageButton mBtnFilter;
    @BindView(R.id.listView)
    ListView mListView;
    Unbinder unbinder;
    private RepoGroupDao mRepoGroupDao;
    private LocalRepoDao mLocalRepoDao;
    private FavoriteAdapter mFavoriteAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 分别创建用于操作数据库表的操作类
        mRepoGroupDao = new RepoGroupDao(DBHelper.getInstance(getContext()));
        mLocalRepoDao = new LocalRepoDao(DBHelper.getInstance(getContext()));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //设置ListView适配器
        mFavoriteAdapter = new FavoriteAdapter();
        mListView.setAdapter(mFavoriteAdapter);

        // 默认显示的数据(全部)，根据不同的分组(以菜单的item的id)来显示不同的数据，单独写一个方法
        setFavoriteData(R.id.repo_group_all);
    }

    //设置数据：根据不同的分组Id设置数据
    private void setFavoriteData(int groupId) {
        switch (groupId) {
            //全部类型
            case R.id.repo_group_all:
                //设置本地仓库里面的所有类别为数据源
                mFavoriteAdapter.setDatas(mLocalRepoDao.queryAll());
                break;
            //未分类
            case R.id.repo_group_no:
                // 查询出本地仓库表里面所有的未分类的仓库
                mFavoriteAdapter.setDatas(mLocalRepoDao.queryNoGroup());
                break;
            //其他所有分组
            default:
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btnFilter)
    public void onViewClicked() {
    }
}
