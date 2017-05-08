package com.yulu.zhaoxinpeng.mygitdroid.content.repositories;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yulu.zhaoxinpeng.mygitdroid.R;
import com.yulu.zhaoxinpeng.mygitdroid.content.repositories.model.Repo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/5/8.
 * 仓库列表的适配器
 */
public class SubRepositoryAdapter extends BaseAdapter {

    private List<Repo> repoList;

    //添加数据
    public void addAll(Collection<Repo> collections) {
        repoList.addAll(collections);
        notifyDataSetChanged();
    }

    //清空数据
    public void clear() {
        repoList.clear();
    }

    public SubRepositoryAdapter() {
        repoList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return repoList == null ? 0 : repoList.size();
    }

    @Override
    public Repo getItem(int position) {
        return repoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;

        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_repo, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        viewHolder = (ViewHolder) convertView.getTag();
        Repo repo = repoList.get(position);
        viewHolder.mTvRepoInfo.setText(repo.getDescription());
        viewHolder.mTvRepoName.setText(repo.getName());
        viewHolder.mTRepoStars.setText("Star : " + repo.getStarCount());
        viewHolder.mTRepoForks.setText("Fork : "+repo.getForksCount());
        //加载仓库所有者的头像
        Picasso.with(parent.getContext()).load(repo.getOwner().getAvatar()).into(viewHolder.mIvIcon);

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
        TextView mTRepoStars;
        @BindView(R.id.tvRepoForkcount)
        TextView mTRepoForks;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
