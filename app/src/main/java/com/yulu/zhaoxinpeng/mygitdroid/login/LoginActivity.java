package com.yulu.zhaoxinpeng.mygitdroid.login;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.hannesdorfmann.mosby.mvp.MvpActivity;
import com.yulu.zhaoxinpeng.mygitdroid.MainActivity;
import com.yulu.zhaoxinpeng.mygitdroid.R;
import com.yulu.zhaoxinpeng.mygitdroid.commons.ActivityUtils;
import com.yulu.zhaoxinpeng.mygitdroid.network.NetApi;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import pl.droidsonroids.gif.GifImageView;

public class LoginActivity extends MvpActivity<LoginView, LoginPresenter> implements LoginView {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.webView)
    WebView mWebView;
    @BindView(R.id.gifImageView)
    GifImageView mGifImageView;
    private Unbinder bind;
    private ActivityUtils mActivityUtils;

    /**
     * 视图：
     * Toolbar标题，WebView是用于展示登录的页面，gif动画是用于WebView加载之前或进行网络请求展示的
     * 授权功能：
     * 1. WebView加载登录页面
     * 2. 如果没有授权过，提示授权，根据创建应用的时候填写的callback的url，拿到临时的授权码
     * 3. code获取token：进行网络请求来获取
     * 4. 根据token值去获取用户信息
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        bind = ButterKnife.bind(this);
        mActivityUtils = new ActivityUtils(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initWebView();
    }

    @NonNull
    @Override
    public LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    private void initWebView() {
        //删除Cookie
        CookieManager mCookieManager = CookieManager.getInstance();
        mCookieManager.removeAllCookie();

        //加载登录的网页
        mWebView.loadUrl(NetApi.AUTH_URL);
        // 让WebView获取焦点和触摸焦点
        mWebView.setFocusable(true);
        mWebView.setFocusableInTouchMode(true);

        // 设置WebView加载的进度的监听：以便设置动画的显示和隐藏
        mWebView.setWebChromeClient(mWebChromeClient);

        // 设置WebView刷新(重定向)
        mWebView.setWebViewClient(mWebViewClient);
    }

    private WebViewClient mWebViewClient = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // 判断是不是重定向到了同意授权的页面
            // 将url转换为Uri，通过uri的getScheme()来判断是不是授权的url
            Uri uri = Uri.parse(url);

            // 判断当前WebView的页面url是不是授权登录的callback的url
            if (NetApi.AUTH_CALLBACK.equals(uri.getScheme())) {
                // 如果是的话，授权了，github会给一个临时的授权码，通过参数code来拿到
                String code = uri.getQueryParameter("code");//拿到的临时授权码

                // 可以根据这个临时授权码获取token了
                presenter.login(code);

                return true;
            }
            return super.shouldOverrideUrlLoading(view, url);
        }
    };

    private WebChromeClient mWebChromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            //如果网页加载完成，动画随即隐藏
            if (newProgress >= 100) {
                mGifImageView.setVisibility(View.GONE);
            }
        }
    };

    //设置返回箭头
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        bind.unbind();
        super.onDestroy();
    }

    //------------------------------------------视图接口的实现-------------------------------------------------
    @Override
    public void showToast(String s) {
        mActivityUtils.showToast(s);
    }

    @Override
    public void showProgressbar() {
        mGifImageView.setVisibility(View.VISIBLE);
    }

    // 重新加载登录页面，重新登录和请求
    @Override
    public void resetWeb() {
        initWebView();
    }

    // 跳转主页面
    @Override
    public void navigateToMain() {
        mActivityUtils.startActivity(MainActivity.class);
        finish();
    }
}
