package com.yulu.zhaoxinpeng.mygitdroid.splash.pager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.yulu.zhaoxinpeng.mygitdroid.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/5/2.
 */

public class Pager2 extends FrameLayout {
    @BindView(R.id.ivBubble1)
    ImageView mIvBubble1;
    @BindView(R.id.ivBubble2)
    ImageView mIvBubble2;
    @BindView(R.id.ivBubble3)
    ImageView mIvBubble3;

    public Pager2(Context context) {
        this(context, null);
    }

    public Pager2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Pager2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.content_pager_2, this, true);
        ButterKnife.bind(this);
        mIvBubble1.setVisibility(GONE);
        mIvBubble2.setVisibility(GONE);
        mIvBubble3.setVisibility(GONE);

    }

    // 做动画的效果：三个图片依次出现
    public void setAnimation(){
        if (mIvBubble1.getVisibility()!=VISIBLE) {
            // 做一个延时：更清楚的展示三个图片的渐出效果
            //第一个图片
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    mIvBubble1.setVisibility(VISIBLE);
                    YoYo.with(Techniques.FadeInLeft)//从左侧淡淡进入
                            .duration(300)//持续时间
                            .playOn(mIvBubble1);
                }
            },300);
            //第二个图片
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    mIvBubble2.setVisibility(VISIBLE);
                    YoYo.with(Techniques.FadeInLeft)
                            .duration(300)
                            .playOn(mIvBubble2);
                }
            },700);
            //第三个图片
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    mIvBubble3.setVisibility(VISIBLE);
                    YoYo.with(Techniques.FadeInLeft)
                            .duration(300)
                            .playOn(mIvBubble3);
                }
            },1100);
        }
    }
}
