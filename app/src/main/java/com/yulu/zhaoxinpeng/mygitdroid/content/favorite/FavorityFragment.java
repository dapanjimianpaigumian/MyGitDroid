package com.yulu.zhaoxinpeng.mygitdroid.content.favorite;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.yulu.zhaoxinpeng.mygitdroid.R;
import com.yulu.zhaoxinpeng.mygitdroid.content.favorite.dao.DBHelper;
import com.yulu.zhaoxinpeng.mygitdroid.content.favorite.dao.LocalRepoDao;
import com.yulu.zhaoxinpeng.mygitdroid.content.favorite.dao.RepoGroupDao;
import com.yulu.zhaoxinpeng.mygitdroid.content.favorite.model.LocalRepo;
import com.yulu.zhaoxinpeng.mygitdroid.content.favorite.model.RepoGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/5/11.
 * 我的收藏
 */

public class FavorityFragment extends Fragment implements PopupMenu.OnMenuItemClickListener{

    @BindView(R.id.tvGroupType)
    TextView mTvGroupType;
    @BindView(R.id.btnFilter)
    ImageButton mBtnFilter;
    @BindView(R.id.listView)
    ListView mListView;
    Unbinder unbinder;
    private RepoGroupDao mRepoGroupDao;
    private LocalRepoDao mLocalRepoDao;
    private FavoriteAdapter mFavoriteAdapter;
    private int mCurrentRepoGroupId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 分别创建用于操作数据库表的操作类
        mRepoGroupDao = new RepoGroupDao(DBHelper.getInstance(getContext()));
        mLocalRepoDao = new LocalRepoDao(DBHelper.getInstance(getContext()));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //设置ListView适配器
        mFavoriteAdapter = new FavoriteAdapter();
        mListView.setAdapter(mFavoriteAdapter);

        // 默认显示的数据(全部)，根据不同的分组(以菜单的item的id)来显示不同的数据，单独写一个方法
        setFavoriteData(R.id.repo_group_all);

        /**
         * 1. 注册上下文菜单：表明显示的菜单作用到谁的身上
         * 2. 创建上下文菜单：onCreateContextMenu，直接重写
         *      菜单的填充、子菜单等
         * 3. 需要处理点击了哪一项
         */
        // 注册上下文菜单：表明作用到ListView上
        registerForContextMenu(mListView);
    }

    //设置数据：根据不同的分组Id设置数据
    private void setFavoriteData(int groupId) {
        switch (groupId) {
            //全部类型
            case R.id.repo_group_all:
                //设置本地仓库里面的所有类别为数据源
                mFavoriteAdapter.setDatas(mLocalRepoDao.queryAll());
                break;
            //未分类
            case R.id.repo_group_no:
                // 查询出本地仓库表里面所有的未分类的仓库
                mFavoriteAdapter.setDatas(mLocalRepoDao.queryNoGroup());
                break;
            //其他所有分组
            default:
                // 根据当前的GroupId去查询本地仓库的数据
                mFavoriteAdapter.setDatas(mLocalRepoDao.queryGroupId(mCurrentRepoGroupId));
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    //点击弹出PopMenu
    @OnClick(R.id.btnFilter)
    public void onViewClicked(View view) {
        /**
         * 设置弹出菜单：
         * 1.创建Popmenu
         * 2.菜单的填充
         */

        //PopMenu的创建
        PopupMenu mPopMenu = new PopupMenu(getContext(), view);

        // 填充菜单：本地有一个Menu，还有一部分是从数据库表里面读取的
        mPopMenu.inflate(R.menu.menu_popup_repo_groups);
        // 另一部分在类别表里
        /**
         * 1. 拿到Menu：要填充到这上面
         * 2. 读取数据库的数据
         * 3. 添加数据到Menu上
         */
        Menu menu = mPopMenu.getMenu();
        // 查询所有的仓库类别
        List<RepoGroup> repoGroups = mRepoGroupDao.queryAll();
        for (RepoGroup repoGroup :
                repoGroups) {
            // 填充菜单数据
            menu.add(Menu.NONE, repoGroup.getId(), Menu.NONE, repoGroup.getName());
        }

        //设置弹出菜单的点击事件
        mPopMenu.setOnMenuItemClickListener(this);
        //展示弹出菜单
        mPopMenu.show();
    }

    //监听PopMenu菜单的点击事件
    @Override
    public boolean onMenuItemClick(MenuItem item) {

        /**
         * 1. 当前的类别标题变化
         * 2. 展示的数据变化
         */
        //改变左上角的类型标题
        mTvGroupType.setText(item.getTitle().toString());

        //根据选择的不同的类别展示数据
        mCurrentRepoGroupId = item.getItemId();
        setFavoriteData(mCurrentRepoGroupId);
        return true;
    }

    //创建上下文菜单
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        // menu 上下文菜单  v 作用到的是v上  menuInfo 创建上下文的一些信息

    }
}
