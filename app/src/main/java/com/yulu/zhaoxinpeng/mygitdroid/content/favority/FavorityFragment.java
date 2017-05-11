package com.yulu.zhaoxinpeng.mygitdroid.content.favority;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.yulu.zhaoxinpeng.mygitdroid.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/5/11.
 */

public class FavorityFragment extends Fragment {

    @BindView(R.id.tvGroupType)
    TextView mTvGroupType;
    @BindView(R.id.btnFilter)
    ImageButton mBtnFilter;
    @BindView(R.id.listView)
    ListView mListView;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btnFilter)
    public void onViewClicked() {
    }
}
