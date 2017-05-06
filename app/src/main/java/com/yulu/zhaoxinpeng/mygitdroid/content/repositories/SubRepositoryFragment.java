package com.yulu.zhaoxinpeng.mygitdroid.content.repositories;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.yulu.zhaoxinpeng.mygitdroid.R;
import com.yulu.zhaoxinpeng.mygitdroid.content.repositories.model.Language;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.header.StoreHouseHeader;


/**
 * Created by Administrator on 2017/5/6.
 * 展示每个热门仓库的Fragment,比如java,javaScript,C...
 */

public class SubRepositoryFragment extends Fragment {


    @BindView(R.id.lvRepos)
    ListView mLvRepos;
    @BindView(R.id.ptrClassicFrameLayout)
    PtrClassicFrameLayout mPtrClassicFrameLayout;
    @BindView(R.id.emptyView)
    TextView mEmptyView;
    @BindView(R.id.errorView)
    TextView mErrorView;
    Unbinder unbinder;
    public static final String KEY_LANGUAGE="key_language";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_repo_list, container, false);
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

        mEmptyView.setText(getLanguage().getName());
        initRefresh();
    }

    public static SubRepositoryFragment getInstance(Language language){
        //创建一个SubRepositoryFragment
        SubRepositoryFragment mSubRepositoryFragment = new SubRepositoryFragment();
        //数据
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_LANGUAGE,language);
        //设置数据
        mSubRepositoryFragment.setArguments(bundle);
        return mSubRepositoryFragment;
    }

    //拿到传递的数据
    private Language getLanguage(){
        return (Language) getArguments().getSerializable(KEY_LANGUAGE);
    }

    private void initRefresh() {
        //刷新间隔比较短，不触发刷新
        mPtrClassicFrameLayout.setLastUpdateTimeRelateObject(this);

        //关闭刷新视图的时间
        mPtrClassicFrameLayout.setDurationToClose(1000);

        //设置头布局的样式
        StoreHouseHeader mStoreHouseHeader = new StoreHouseHeader(getContext());
        mStoreHouseHeader.initWithString("I LOVE ANDROID");
        mStoreHouseHeader.setPadding(0,30,0,30);
        mPtrClassicFrameLayout.setHeaderView(mStoreHouseHeader);//设置刷新的头布局
        mPtrClassicFrameLayout.addPtrUIHandler(mStoreHouseHeader);//处理UI

        //设置头布局的背景
        mPtrClassicFrameLayout.setBackgroundResource(R.color.colorRefresh);

        mPtrClassicFrameLayout.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                // TODO: 2017/5/6 上拉加载更多的网络请求
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                // TODO: 2017/5/6 下拉刷新的网络请求
            }
        });
    }
}
