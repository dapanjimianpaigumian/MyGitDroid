package com.yulu.zhaoxinpeng.mygitdroid.content.repositories.subrepoInfo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hannesdorfmann.mosby.mvp.MvpActivity;
import com.squareup.picasso.Picasso;
import com.yulu.zhaoxinpeng.mygitdroid.R;
import com.yulu.zhaoxinpeng.mygitdroid.commons.ActivityUtils;
import com.yulu.zhaoxinpeng.mygitdroid.content.repositories.model.Repo;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/5/9.
 * 仓库详情界面
 */
public class SubRepoInfoActivity extends MvpActivity<SubRepoInfoView,SubRepoInfoPresenter> implements SubRepoInfoView {

    private static final String KEY_REPO = "key_repo";
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.ivIcon)
    ImageView mIvIcon;
    @BindView(R.id.tvRepoName)
    TextView mTvRepoName;
    @BindView(R.id.tvRepoStars)
    TextView mTvRepoStars;
    @BindView(R.id.tvRepoInfo)
    TextView mTvRepoInfo;
    @BindView(R.id.webView)
    WebView mWebView;
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;
    private Unbinder bind;
    private Repo mRepo;
    private ActivityUtils mActivityUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 触发onContentChanged方法
        setContentView(R.layout.activity_repo_info);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        bind = ButterKnife.bind(this);
        mActivityUtils = new ActivityUtils(this);

        //获取传递的数据
        mRepo = (Repo) getIntent().getSerializableExtra(KEY_REPO);

        setSupportActionBar(mToolbar);
        // 设置返回箭头：Android 默认已经提供了id=home，选项菜单来处理
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(mRepo.getName());

        //设置仓库详情界面的信息
        mTvRepoName.setText(mRepo.getFullName());//仓库名称
        mTvRepoInfo.setText(mRepo.getDescription());
        mTvRepoStars.setText(String.format("Star：%d  Fork：%d", mRepo.getStarCount(), mRepo.getForksCount()));
        Picasso.with(this).load(mRepo.getOwner().getAvatar()).into(mIvIcon);//设置头像

        // 获取及展示ReadMe：网络请求
        presenter.getReadMe(mRepo);
    }

    @NonNull
    @Override
    public SubRepoInfoPresenter createPresenter() {
        return new SubRepoInfoPresenter();
    }

    //设置返回箭头
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }

    /**
     * 对外提供一个跳转的方法：
     * 方便别人跳转的时候调用
     * 规范数据的传递：调用getSubRepoInstance方法，就必须传入open方法里面需要的参数
     */
    public static void getSubRepoInstance(Context context, @NonNull Repo repo) {
        Intent intent = new Intent(context, SubRepoInfoActivity.class);
        intent.putExtra(KEY_REPO, repo);
        context.startActivity(intent);
    }

    //--------------------------------------视图接口的具体实现------------------------------------------------
    @Override
    public void showProgressbar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressbar() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showToast(String s) {
        mActivityUtils.showToast(s);
    }

    // 设置数据：HTML的纯文本内容
    @Override
    public void setHtmlData(String htmlData) {
        // 使用WebView加载Html的字符串
        mWebView.loadData(htmlData,"text/html","UTF-8");
    }
}
