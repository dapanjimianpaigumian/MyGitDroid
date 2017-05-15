package com.yulu.zhaoxinpeng.mygitdroid.content.gank;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.hannesdorfmann.mosby.mvp.MvpFragment;
import com.yulu.zhaoxinpeng.mygitdroid.R;
import com.yulu.zhaoxinpeng.mygitdroid.commons.ActivityUtils;
import com.yulu.zhaoxinpeng.mygitdroid.content.gank.model.GankItem;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/5/15.
 * 每日干货界面
 */

public class GankFragment extends MvpFragment<GankView,GankPresenter> implements GankView{

    @BindView(R.id.tvDate)
    TextView mTvDate;
    @BindView(R.id.btnFilter)
    ImageButton mBtnFilter;
    @BindView(R.id.content)
    ListView mLvcontent;
    @BindView(R.id.emptyView)
    FrameLayout mEmptyView;
    Unbinder unbinder;
    private Date mDate;
    private GankAdapter mGankAdapter;
    private Calendar mCalendar;
    private SimpleDateFormat simpleDateFormat;
    private ActivityUtils mActivityUtils;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //获取当前日期
        mCalendar = Calendar.getInstance(Locale.CHINA);
        mDate = new Date(System.currentTimeMillis());
        mActivityUtils = new ActivityUtils(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gank, container, false);
        return view;
    }

    @Override
    public GankPresenter createPresenter() {
        return new GankPresenter();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        // 规范一下日期的展示
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        //设置日期的展示
        mTvDate.setText(simpleDateFormat.format(mDate));
        mGankAdapter = new GankAdapter();
        mLvcontent.setAdapter(mGankAdapter);
        mLvcontent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mActivityUtils.startBrowser(mGankAdapter.getItem(position).getUrl());
            }
        });

        //当日数据的获取
        presenter.getGank(mDate);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    //点击的时候显示一个日期选择器
    @OnClick(R.id.btnFilter)
    public void onViewClicked() {
        /** 显示日期选择器
         * 1. 创建DatePickerDialog
         * 2. 当前的日期
         * 3. 监听：日期选中后
         */

        int year = mCalendar.get(Calendar.YEAR);
        int month = mCalendar.get(Calendar.MONTH);
        int day = mCalendar.get(Calendar.DAY_OF_MONTH);

        //创建日期选择器
        DatePickerDialog mDatePickerDialog = new DatePickerDialog(
                getContext(),
                dateListener,
                year,
                month,
                day);

        mDatePickerDialog.show();
    }

    // 日期设置的监听
    private DatePickerDialog.OnDateSetListener dateListener=new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            mCalendar.set(year,month,dayOfMonth);
            mDate=mCalendar.getTime();

            //设置日期
            mTvDate.setText(simpleDateFormat.format(mDate));

            // 如果重新选择了日期，重新根据选择的日期进行数据获取
            presenter.getGank(mDate);
        }
    };

    //-----------------------------------------视图方法的具体实现-----------------------------------------

    //设置数据
    @Override
    public void setData(List<GankItem> list) {
        mGankAdapter.setDatas(list);
        mGankAdapter.notifyDataSetChanged();
    }

    //显示空视图
    @Override
    public void showEmptyView() {
        mEmptyView.setVisibility(View.VISIBLE);
        mLvcontent.setVisibility(View.GONE);
    }

    //隐藏空视图
    @Override
    public void hideEmptyView() {
        mLvcontent.setVisibility(View.VISIBLE);
        mEmptyView.setVisibility(View.GONE);
    }

    @Override
    public void showToast(String s) {
        mActivityUtils.showToast(s);
    }
}
