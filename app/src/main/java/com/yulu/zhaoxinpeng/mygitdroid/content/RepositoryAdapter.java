package com.yulu.zhaoxinpeng.mygitdroid.content;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.yulu.zhaoxinpeng.mygitdroid.content.repositories.SubRepositoryFragment;
import com.yulu.zhaoxinpeng.mygitdroid.content.repositories.model.Language;

import java.util.List;

/**
 * Created by Administrator on 2017/5/3.
 * 多个仓库的适配器
 */
public class RepositoryAdapter extends FragmentPagerAdapter{

    private List<Language> data;

    public RepositoryAdapter(FragmentManager fm, Context context) {
        super(fm);
        //在构造方法中完成数据填充
        data=Language.getLanguages(context);
    }

    @Override
    public Fragment getItem(int position) {
        return SubRepositoryFragment.getInstance(data.get(position));
    }

    @Override
    public int getCount() {
        return data==null?0:data.size();
    }

    //设置TabLayout的每个小标题
    @Override
    public CharSequence getPageTitle(int position) {
        return data.get(position).getName();
    }
}
