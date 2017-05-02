package com.yulu.zhaoxinpeng.mygitdroid.splash.pager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.yulu.zhaoxinpeng.mygitdroid.R;

/**
 * Created by Administrator on 2017/5/2.
 */

public class Pager0 extends FrameLayout {

    //在代码中使用
    public Pager0(Context context) {
        this(context,null);
    }

    //在代码和布局中使用
    public Pager0(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    //在代码和布局中使用，并且设置了仰视
    public Pager0(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    //初始化视图
    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.content_pager_0,this,true);
    }
}
