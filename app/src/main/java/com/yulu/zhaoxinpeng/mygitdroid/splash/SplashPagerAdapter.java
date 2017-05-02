package com.yulu.zhaoxinpeng.mygitdroid.splash;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.yulu.zhaoxinpeng.mygitdroid.splash.pager.Pager0;
import com.yulu.zhaoxinpeng.mygitdroid.splash.pager.Pager1;
import com.yulu.zhaoxinpeng.mygitdroid.splash.pager.Pager2;

/**
 * Created by Administrator on 2017/5/2.
 * 欢迎页上的Fragment的适配器
 */
public class SplashPagerAdapter extends PagerAdapter{

    private final View[] mViews;

    public SplashPagerAdapter(Context context) {
        //在构造器中初始化数据
        mViews = new View[]{new Pager0(context),new Pager1(context),new Pager2(context)};
    }

    @Override
    public int getCount() {
        return mViews==null?0:mViews.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mViews[position]);
        return mViews[position];
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViews[position]);
    }
}
