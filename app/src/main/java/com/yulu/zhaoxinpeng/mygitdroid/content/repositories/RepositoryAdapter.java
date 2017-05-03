package com.yulu.zhaoxinpeng.mygitdroid.content.repositories;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Administrator on 2017/5/3.
 */
public class RepositoryAdapter extends FragmentPagerAdapter{

    public RepositoryAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return new TestFragment();
    }

    @Override
    public int getCount() {
        return 6;
    }

    //设置TabLayout的每个小标题
    @Override
    public CharSequence getPageTitle(int position) {
        return "java"+position;
    }
}
