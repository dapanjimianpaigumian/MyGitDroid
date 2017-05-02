package com.yulu.zhaoxinpeng.mygitdroid.splash;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.yulu.zhaoxinpeng.mygitdroid.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.relex.circleindicator.CircleIndicator;

/**
 * 欢迎页上的 Fragment
 */
public class SplashPagerFragment extends Fragment {

    // 动画：AndroidViewAnimation 开源库，github上搜一下

    @BindView(R.id.ivPhoneBlank)
    ImageView mIvPhoneBlank;
    @BindView(R.id.ivPhoneFont)
    ImageView mIvPhoneFont;
    @BindView(R.id.layoutPhoneInner)
    FrameLayout mLayoutPhoneInner;
    @BindView(R.id.layoutPhone)
    FrameLayout mLayoutPhone;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @BindView(R.id.content)
    FrameLayout mContent;
    @BindView(R.id.indicator)
    CircleIndicator mCircleIndicator;
    private Unbinder bind;
    private SplashPagerAdapter mSplashPagerAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_splash_pager, container, false);
        bind = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //视图的初始化
        mSplashPagerAdapter = new SplashPagerAdapter(getContext());
        mViewPager.setAdapter(mSplashPagerAdapter);
        //为Viewpager设置圆点指示器
        mCircleIndicator.setViewPager(mViewPager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }

}
