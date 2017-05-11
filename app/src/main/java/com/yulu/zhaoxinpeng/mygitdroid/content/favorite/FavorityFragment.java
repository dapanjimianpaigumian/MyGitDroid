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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        List<RepoGroup> repoGroupList = mRepoGroupDao.queryAll();
        List<LocalRepo> localRepoList = mLocalRepoDao.queryAll();

        Log.e("TAG","仓库类别"+repoGroupList.size()+"本地仓库"+localRepoList.size());

        for (LocalRepo localRepo :
                localRepoList) {
            Log.e("TBG", "仓库信息" + localRepo.getName());
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
