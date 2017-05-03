package com.yulu.zhaoxinpeng.mygitdroid;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.yulu.zhaoxinpeng.mygitdroid.commons.ActivityUtils;
import com.yulu.zhaoxinpeng.mygitdroid.content.repositories.RepositoryFragment;
import com.yulu.zhaoxinpeng.mygitdroid.content.user.UserFragment;
import com.yulu.zhaoxinpeng.mygitdroid.login.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.navigationView)
    NavigationView mNavigationView;
    @BindView(R.id.activity_main)
    DrawerLayout mDrawerLayout;
    private Unbinder bind;
    private ActivityUtils mActivityUtils;
    private RepositoryFragment mRepositoryFragment;
    private UserFragment mUserFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 设置布局：触发onContentChanged方法
        setContentView(R.layout.activity_main);

    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        bind = ButterKnife.bind(this);
        mActivityUtils = new ActivityUtils(this);

        setSupportActionBar(mToolbar);

        // 按钮监听的创建
        ActionBarDrawerToggle mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mToggle.syncState();// 同步状态

        // 设置侧滑监听
        mDrawerLayout.addDrawerListener(mToggle);

        // 设置侧滑菜单的item选中事件
        mNavigationView.setNavigationItemSelectedListener(this);

        //找到侧滑的头布局的控件
        Button mBtnLogin = ButterKnife.findById(mNavigationView.getHeaderView(0), R.id.btnLogin);
        ImageView mIvIcon = ButterKnife.findById(mNavigationView.getHeaderView(0), R.id.ivIcon);

        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivityUtils.startActivity(LoginActivity.class);
            }
        });

        //默认展示热门仓库
        mNavigationView.setCheckedItem(R.id.github_hot_repo);
        if(mRepositoryFragment==null) mRepositoryFragment = new RepositoryFragment();
        replaceFragment(mRepositoryFragment);
    }

    //更换 Fragment 方法
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container,fragment);
        fragmentTransaction.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }

    //处理侧滑菜单中item的选择事件
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        if (item.isChecked()) {
            item.setChecked(false);
        }

        switch (item.getItemId()) {
            case R.id.github_hot_repo:
                if (!mRepositoryFragment.isAdded()) {
                    replaceFragment(mRepositoryFragment);
                }
                break;
            case R.id.github_hot_coder:
                if (mUserFragment ==null) mUserFragment = new UserFragment();
                if (!mUserFragment.isAdded()) {
                    replaceFragment(mUserFragment);
                }
                break;
            case R.id.arsenal_my_repo:
                mActivityUtils.showToast("我的收藏");
                break;
            case R.id.tips_daily:
                mActivityUtils.showToast("每日干货");
                break;
        }

        //选中任意一项后，都会关闭侧滑
        mDrawerLayout.closeDrawer(GravityCompat.START);

        // 返回true，表示该项被选择
        return true;
    }

    //处理返回键
    @Override
    public void onBackPressed() {
        //如果侧滑呈打开状态，那么先关闭侧滑，否则直接退出
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }
}
