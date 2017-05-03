package com.yulu.zhaoxinpeng.mygitdroid.splash;


import android.animation.ArgbEvaluator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.yulu.zhaoxinpeng.mygitdroid.R;
import com.yulu.zhaoxinpeng.mygitdroid.splash.pager.Pager2;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.relex.circleindicator.CircleIndicator;

import static com.yulu.zhaoxinpeng.mygitdroid.R.color.colorGreen;
import static com.yulu.zhaoxinpeng.mygitdroid.R.color.colorRed;
import static com.yulu.zhaoxinpeng.mygitdroid.R.color.colorYellow;

/**
 * 欢迎页上的 Fragment
 */
public class SplashPagerFragment extends Fragment {

    // 动画：AndroidViewAnimation 开源库，github上搜一下

    @BindView(R.id.ivPhoneBlank)
    ImageView mIvPhoneBlank;
    @BindView(R.id.ivPhoneFont)
    ImageView mIvPhoneFont;
    @BindView(R.id.layoutPhoneInner)
    FrameLayout mLayoutPhoneInner;
    @BindView(R.id.layoutPhone)
    FrameLayout mLayoutPhone;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @BindView(R.id.content)
    FrameLayout mContent;
    @BindView(R.id.indicator)
    CircleIndicator mCircleIndicator;
    private Unbinder bind;
    private SplashPagerAdapter mSplashPagerAdapter;
    @BindColor(R.color.colorGreen) int colorGreen;
    @BindColor(R.color.colorRed) int colorRed;
    @BindColor(R.color.colorYellow) int colorYellow;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_splash_pager, container, false);
        bind = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //视图的初始化
        mSplashPagerAdapter = new SplashPagerAdapter(getContext());
        mViewPager.setAdapter(mSplashPagerAdapter);
        //为Viewpager设置圆点指示器
        mCircleIndicator.setViewPager(mViewPager);

        //根据ViewPager的切换改变动画效果
        mViewPager.addOnPageChangeListener(mBackgroundColorListener);
        mViewPager.addOnPageChangeListener(mPhoneViewListener);
    }

    //监听 Viewpager的背景颜色的变化
    private ViewPager.OnPageChangeListener mBackgroundColorListener=new ViewPager.OnPageChangeListener() {
        // ArgbEvaluator：颜色生成器：根据差值、两个颜色，计算出两种颜色和差值得出一个新的颜色
        ArgbEvaluator mArgbEvaluator=new ArgbEvaluator();

        /**
         * @param position 当前页面的 Position
         * @param positionOffset  偏移量百分比[0,1)
         * @param positionOffsetPixels  偏移量的像素值
         */
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            //从第一个页面往后切换的时候
            if (position==0) {
                // 根据偏移量计算出的新的颜色
                int color= (int) mArgbEvaluator.evaluate(positionOffset,colorGreen,colorRed);
                mContent.setBackgroundColor(color);
                return;
            }

            // 从第二个页面向后切换的时候
            if (position==1) {
                int color= (int) mArgbEvaluator.evaluate(positionOffset,colorRed,colorYellow);
                mContent.setBackgroundColor(color);
                return;
            }
        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    //实现 Fragment 中“手机”的动画(缩放、移动、透明度)
    public ViewPager.OnPageChangeListener mPhoneViewListener=new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            /**
             * 1. 手机缩放
             * 2. 手机移动
             * 3. 手机上文字图片的透明度
             * 4. 切换到第三个页面的时候，展示自己的动画效果
             */

            if (position==0) {
                //缩放
                float scale=0.5f+positionOffset*0.5f;
                mLayoutPhone.setScaleX(scale);
                mLayoutPhone.setScaleY(scale);

                //移动
                int scroll= (int) ((positionOffset-1)*360);
                mLayoutPhone.setTranslationX(scroll);

                //透明度变化
                mIvPhoneFont.setAlpha(positionOffset);
            }

            // 手机在第二个页面的切换动画
            if (position==1){
                mLayoutPhone.setTranslationX(-positionOffsetPixels);
            }
        }

        @Override
        public void onPageSelected(int position) {
            // 切换到第三页的时候展示当前页面自己的效果
            if (position==2){
                Pager2 pager2 = (Pager2) mSplashPagerAdapter.getView(position);
                pager2.setAnimation();
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }

}
