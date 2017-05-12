package com.yulu.zhaoxinpeng.mygitdroid.content.favorite;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yulu.zhaoxinpeng.mygitdroid.R;
import com.yulu.zhaoxinpeng.mygitdroid.content.favorite.model.LocalRepo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/5/12.
 * 我的收藏的适配器
 */
public class FavoriteAdapter extends BaseAdapter {

    //数据
    private List<LocalRepo> mLocalRepoList;

    //构造方法中初始化 List
    public FavoriteAdapter() {
        mLocalRepoList = new ArrayList<>();
    }

    //设置数据的方法
    public void setDatas(List<LocalRepo> list){
        mLocalRepoList.clear();
        mLocalRepoList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mLocalRepoList == null ? 0 : mLocalRepoList.size();
    }

    @Override
    public LocalRepo getItem(int position) {
        return mLocalRepoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder=null;

        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_repo, parent, false);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        viewHolder = (ViewHolder) convertView.getTag();

        //对item视图进行数据填充
        LocalRepo localRepo = getItem(position);
        viewHolder.mTvRepoName.setText(localRepo.getFullName());
        viewHolder.mTvRepoInfo.setText(localRepo.getDescription());
        viewHolder.mTvRepoStars.setText("Star："+localRepo.getStargazersCount());
        viewHolder.mTvRepoForkcount.setText("Fork: "+localRepo.getForksCount());
        Picasso.with(parent.getContext()).load(localRepo.getAvatarUrl()).into(viewHolder.mIvIcon);

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.ivIcon)
        ImageView mIvIcon;
        @BindView(R.id.tvRepoName)
        TextView mTvRepoName;
        @BindView(R.id.tvRepoInfo)
        TextView mTvRepoInfo;
        @BindView(R.id.tvRepoStars)
        TextView mTvRepoStars;
        @BindView(R.id.tvRepoForkcount)
        TextView mTvRepoForkcount;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
