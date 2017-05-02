package com.yulu.zhaoxinpeng.mygitdroid.splash.pager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.yulu.zhaoxinpeng.mygitdroid.R;

/**
 * Created by Administrator on 2017/5/2.
 */

public class Pager2 extends FrameLayout {
    public Pager2(Context context) {
        this(context,null);
    }

    public Pager2(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public Pager2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.content_pager_2,this,true);
    }
}
