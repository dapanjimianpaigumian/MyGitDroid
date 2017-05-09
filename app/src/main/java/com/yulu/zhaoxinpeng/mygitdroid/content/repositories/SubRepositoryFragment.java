package com.yulu.zhaoxinpeng.mygitdroid.content.repositories;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.hannesdorfmann.mosby.mvp.MvpFragment;
import com.yulu.zhaoxinpeng.mygitdroid.R;
import com.yulu.zhaoxinpeng.mygitdroid.commons.ActivityUtils;
import com.yulu.zhaoxinpeng.mygitdroid.content.repositories.model.Language;
import com.yulu.zhaoxinpeng.mygitdroid.content.repositories.model.Repo;
import com.yulu.zhaoxinpeng.mygitdroid.content.repositories.subrepoInfo.SubRepoInfoActivity;

import java.util.List;

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

public class SubRepositoryFragment extends MvpFragment<SubRepositoryView, SubRepositoryPresenter> implements SubRepositoryView {

    @BindView(R.id.lvRepos)
    ListView mLvRepos;
    @BindView(R.id.ptrClassicFrameLayout)
    PtrClassicFrameLayout mPtrClassicFrameLayout;
    @BindView(R.id.emptyView)
    TextView mEmptyView;
    @BindView(R.id.errorView)
    TextView mErrorView;
    Unbinder unbinder;
    public static final String KEY_LANGUAGE = "key_language";
    private ActivityUtils mActivityUtils;
    private SubRepositoryAdapter mSubRepositoryAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityUtils = new ActivityUtils(this);
        mSubRepositoryAdapter = new SubRepositoryAdapter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_repo_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        //自动刷新数据
        if (mSubRepositoryAdapter.getCount() == 0) {
            mPtrClassicFrameLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mPtrClassicFrameLayout.autoRefresh();
                }
            },200);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public SubRepositoryPresenter createPresenter() {
        return new SubRepositoryPresenter(getLanguage());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLvRepos.setAdapter(mSubRepositoryAdapter);
        mLvRepos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Repo repo = mSubRepositoryAdapter.getItem(position);
                SubRepoInfoActivity.getSubRepoInstance(getContext(),repo);
            }
        });

        mLvRepos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO: 2017/5/9 长按收藏 待实现
                return true;
            }
        });
        initRefresh();
    }

    public static SubRepositoryFragment getInstance(Language language) {
        //创建一个SubRepositoryFragment
        SubRepositoryFragment mSubRepositoryFragment = new SubRepositoryFragment();
        //数据
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_LANGUAGE, language);
        //设置数据
        mSubRepositoryFragment.setArguments(bundle);
        return mSubRepositoryFragment;
    }

    //拿到传递的数据
    private Language getLanguage() {
        return (Language) getArguments().getSerializable(KEY_LANGUAGE);
    }

    //初始化RefreshLayout
    private void initRefresh() {
        //刷新间隔比较短，不触发刷新
        mPtrClassicFrameLayout.setLastUpdateTimeRelateObject(this);

        //关闭刷新视图的时间
        mPtrClassicFrameLayout.setDurationToClose(1000);

        //设置头布局的样式
        StoreHouseHeader mStoreHouseHeader = new StoreHouseHeader(getContext());
        mStoreHouseHeader.initWithString("I LOVE ANDROID");
        mStoreHouseHeader.setPadding(0, 30, 0, 30);
        mPtrClassicFrameLayout.setHeaderView(mStoreHouseHeader);//设置刷新的头布局
        mPtrClassicFrameLayout.addPtrUIHandler(mStoreHouseHeader);//处理UI

        //设置头布局的背景
        mPtrClassicFrameLayout.setBackgroundResource(R.color.colorRefresh);

        mPtrClassicFrameLayout.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                presenter.load();
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                presenter.refresh();
            }
        });
    }

    //-----------------------------------------视图方法的具体实现---------------------------------------------
    //停止刷新
    @Override
    public void stopRefresh() {
        mPtrClassicFrameLayout.refreshComplete();
    }

    //停止加载
    @Override
    public void stopLoad() {
        mPtrClassicFrameLayout.refreshComplete();
    }

    //显示空视图
    @Override
    public void showEmptyView(String s) {
        mPtrClassicFrameLayout.setVisibility(View.GONE);
        mEmptyView.setVisibility(View.VISIBLE);
        mErrorView.setVisibility(View.GONE);
        mEmptyView.setText(s);
    }

    //显示错误视图
    @Override
    public void showErrorView() {
        mPtrClassicFrameLayout.setVisibility(View.GONE);
        mEmptyView.setVisibility(View.GONE);
        mErrorView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showToast(String s) {
        mActivityUtils.showToast(s);
    }

    //设置刷新数据
    @Override
    public void addRefreshData(List<Repo> repos) {
        //刷新数据时，先清空再添加
        mSubRepositoryAdapter.clear();
        mSubRepositoryAdapter.addAll(repos);
        mSubRepositoryAdapter.notifyDataSetChanged();
    }

    //设置加载数据
    @Override
    public void addLoadData(List<Repo> repos) {
        //加载数据时，直接添加
        mSubRepositoryAdapter.addAll(repos);
        mSubRepositoryAdapter.notifyDataSetChanged();
    }
}
