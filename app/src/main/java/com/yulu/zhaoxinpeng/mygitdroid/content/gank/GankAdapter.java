package com.yulu.zhaoxinpeng.mygitdroid.content.gank;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yulu.zhaoxinpeng.mygitdroid.R;
import com.yulu.zhaoxinpeng.mygitdroid.content.gank.model.GankItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/5/15.
 * 每日干货的适配器
 */
public class GankAdapter extends BaseAdapter {

    private List<GankItem> mList;

    public GankAdapter() {
        this.mList = new ArrayList<>();
    }

    //对外提供设置数据的方法
    public void setDatas(List<GankItem> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public GankItem getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder=null;

        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_gank, parent, false);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        viewHolder= (ViewHolder) convertView.getTag();
        GankItem gankItem = getItem(position);
        viewHolder.mGankItem.setText(gankItem.getDesc());

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.gank_item)
        TextView mGankItem;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
