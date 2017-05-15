package com.yulu.zhaoxinpeng.mygitdroid.content.user;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yulu.zhaoxinpeng.mygitdroid.R;
import com.yulu.zhaoxinpeng.mygitdroid.login.model.User;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/5/15.
 */
public class UserAdapter extends BaseAdapter {

    private List<User> userList;

    public UserAdapter() {
        this.userList = new ArrayList<>();
    }

    //增加数据
    public void addAll(List<User> users) {
        userList.addAll(users);
        notifyDataSetChanged();
    }

    //清除数据
    public void clear() {
        userList.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return userList == null ? 0 : userList.size();
    }

    @Override
    public User getItem(int position) {
        return userList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_user, parent, false);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        viewHolder = (ViewHolder) convertView.getTag();
        User user = userList.get(position);
        viewHolder.mTvName.setText(user.getLogin());
        Picasso.with(parent.getContext()).load(user.getAvatar()).into(viewHolder.mIvIcon);

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.ivIcon)
        ImageView mIvIcon;
        @BindView(R.id.tvLoginName)
        TextView mTvName;
        @BindView(R.id.imageView)
        ImageView mLineView;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
