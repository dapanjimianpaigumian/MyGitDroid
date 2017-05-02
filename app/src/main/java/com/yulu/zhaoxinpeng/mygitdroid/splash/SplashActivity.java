package com.yulu.zhaoxinpeng.mygitdroid.splash;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.yulu.zhaoxinpeng.mygitdroid.MainActivity;
import com.yulu.zhaoxinpeng.mygitdroid.R;
import com.yulu.zhaoxinpeng.mygitdroid.commons.ActivityUtils;
import com.yulu.zhaoxinpeng.mygitdroid.login.LoginActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 欢迎页界面
 */
public class SplashActivity extends AppCompatActivity {

    private Unbinder bind;
    private ActivityUtils mActivityUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        bind = ButterKnife.bind(this);
        mActivityUtils = new ActivityUtils(this);
    }


    @OnClick({R.id.btnLogin, R.id.btnEnter})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                mActivityUtils.startActivity(LoginActivity.class);
                finish();
                break;
            case R.id.btnEnter:
                mActivityUtils.startActivity(MainActivity.class);
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }
}
