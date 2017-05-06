package com.yulu.zhaoxinpeng.mygitdroid.content;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yulu.zhaoxinpeng.mygitdroid.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/5/3.
 * 最热门的仓库主界面
 */

public class RepositoryFragment extends Fragment {

    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    Unbinder unbinder;
    private RepositoryAdapter mRepositoryAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hot_repo, container, false);

        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Viewpager切换设置适配器:注意：getChildFragmentManager()
        mRepositoryAdapter = new RepositoryAdapter(getChildFragmentManager(),getContext());
        mViewPager.setAdapter(mRepositoryAdapter);

        //绑定Tablayout与ViewPager
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
