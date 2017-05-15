package com.yulu.zhaoxinpeng.mygitdroid.content.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.hannesdorfmann.mosby.mvp.MvpFragment;
import com.yulu.zhaoxinpeng.mygitdroid.R;
import com.yulu.zhaoxinpeng.mygitdroid.commons.ActivityUtils;
import com.yulu.zhaoxinpeng.mygitdroid.login.model.User;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by Administrator on 2017/5/3.
 * 热门开发者界面
 */

public class UserFragment extends MvpFragment<UserView,UserPresenter> implements UserView{

    @BindView(R.id.lvRepos)
    ListView mLvRepos;
    @BindView(R.id.ptrClassicFrameLayout)
    PtrClassicFrameLayout mPtrClassicFrameLayout;
    @BindView(R.id.emptyView)
    TextView mEmptyView;
    @BindView(R.id.errorView)
    TextView mErrorView;
    Unbinder unbinder;
    private ActivityUtils mActivityUtils;
    private UserAdapter mUserAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hot_user, container, false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mUserAdapter.getCount()==0) {
            mPtrClassicFrameLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mPtrClassicFrameLayout.autoRefresh();
                }
            },200);
        }
    }

    @Override
    public UserPresenter createPresenter() {
        return new UserPresenter();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        mActivityUtils = new ActivityUtils(this);
        mUserAdapter = new UserAdapter();
        mLvRepos.setAdapter(mUserAdapter);

        initPullToRefresh();

    }

    private void initPullToRefresh() {
        mPtrClassicFrameLayout.setLastUpdateTimeRelateObject(this);
        mPtrClassicFrameLayout.setDurationToClose(1000);
        mPtrClassicFrameLayout.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                //  加载更多
                presenter.load();
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                //  刷新更多
                presenter.refresh();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    //-----------------------------------------视图方法的具体实现---------------------------------------------
    @Override
    public void addRefreshData(List<User> users) {
        mUserAdapter.clear();
        mUserAdapter.addAll(users);
        mUserAdapter.notifyDataSetChanged();
    }

    @Override
    public void addLoadData(List<User> users) {
        mUserAdapter.addAll(users);
        mUserAdapter.notifyDataSetChanged();
    }

    @Override
    public void stopMoreData() {
        mPtrClassicFrameLayout.refreshComplete();
    }

    @Override
    public void showEmptyView() {
        mEmptyView.setVisibility(View.VISIBLE);
        mPtrClassicFrameLayout.setVisibility(View.GONE);
        mErrorView.setVisibility(View.GONE);
    }

    @Override
    public void showErrorView() {
        mPtrClassicFrameLayout.setVisibility(View.GONE);
        mErrorView.setVisibility(View.VISIBLE);
        mEmptyView.setVisibility(View.GONE);
    }

    @Override
    public void showToast(String s) {
        mActivityUtils.showToast(s);
    }
}
