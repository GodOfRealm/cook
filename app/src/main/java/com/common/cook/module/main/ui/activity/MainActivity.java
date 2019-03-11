package com.common.cook.module.main.ui.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.common.cook.R;
import com.common.cook.app.BaseActivity;
import com.common.cook.module.main.contract.MainContract;
import com.common.cook.module.main.di.component.DaggerMainComponent;
import com.common.cook.module.main.di.module.MainModule;
import com.common.cook.module.main.presenter.MainPresenter;
import com.common.cook.util.DaoSharedPreferences;
import com.common.cook.util.UrlUtils;
import com.jess.arms.di.component.AppComponent;

import butterknife.BindView;

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {

    @BindView(R.id.main_wb_url)
    WebView mbrowserWebview;//浏览webview
    @BindView(R.id.main_wb_play)
    WebView mPlayWebview;//播放webview
    @BindView(R.id.main_dl)
    DrawerLayout mDl;
    @BindView(R.id.main_nv)
    NavigationView mNv;
    private String QQ_URL = "https://v.qq.com/";
    private String AIQIYI_URL = "https://www.iqiyi.com/";
    private String YOUKU_URL = "http://www.youku.com/";
    public static String VIP_URL = "http://api.xiuyao.me/jx/?url=";
    private String pcAgent = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.134 Safari/537.36";//电脑UA,模拟谷歌浏览器。
    private String phoneAgent = "Mozilla/5.0 (Linux; Android 5.1; MZ-m1 metal Build/LMY47I) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0";//我的手机的浏览器的UA是

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMainComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .mainModule(new MainModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.main_activity_main; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initWebView();
        initListner();
        initDrawerLayout();
        showLoading();
        mbrowserWebview.loadUrl(QQ_URL);
    }

    private void initDrawerLayout() {
        boolean firstLogin = DaoSharedPreferences.getInstance().isFirstLogin();
        if (firstLogin) {
            mDl.openDrawer(Gravity.LEFT);
            DaoSharedPreferences.getInstance().setFirstLogin();
        }
    }

    private void initListner() {
        mNv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                mDl.closeDrawers();
                showLoading();
                switch (menuItem.getItemId()) {
                    case R.id.main_navigation_aqiyi:
                        mbrowserWebview.loadUrl(AIQIYI_URL);
                        break;
                    case R.id.main_navigation_youku:
                        mbrowserWebview.loadUrl(YOUKU_URL);
                        break;
                    case R.id.main_navigation_qq:
                        mbrowserWebview.loadUrl(QQ_URL);
                        break;
                }
                return false;
            }
        });
    }

    /**
     * 展示网页界面
     **/
    public void initWebView() {
        mbrowserWebview.setHorizontalScrollBarEnabled(false);//水平不显示
        mbrowserWebview.setVerticalScrollBarEnabled(false); //垂直不显示
        WebSettings webSettings = mbrowserWebview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true); // 关键点
        webSettings.setAllowFileAccess(true); // 允许访问文件
        webSettings.setSupportZoom(true); // 支持缩放
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE); // 不加载缓存内容
        webSettings.setUserAgentString(pcAgent);


        WebSettings playWebviewSettings = mPlayWebview.getSettings();
        playWebviewSettings.setJavaScriptEnabled(true);
        playWebviewSettings.setUseWideViewPort(true); // 关键点
        playWebviewSettings.setAllowFileAccess(true); // 允许访问文件
        playWebviewSettings.setSupportZoom(true); // 支持缩放
        playWebviewSettings.setLoadWithOverviewMode(true);
        playWebviewSettings.setCacheMode(WebSettings.LOAD_NO_CACHE); // 不加载缓存内容


        WebViewClient wvc = new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                mbrowserWebview.loadUrl(url);
                return true;
            }


            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {

                if (UrlUtils.isVideo(url)) {
                    view.stopLoading();
                    MainActivity.this.showLoading();
                    mPlayWebview.setVisibility(View.VISIBLE);
                    mPlayWebview.onResume();
                    mPlayWebview.resumeTimers();
                    mPlayWebview.loadUrl(VIP_URL + url);

                }
            }
        };
        mbrowserWebview.setWebViewClient(wvc);
        mbrowserWebview.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress >= 30){
                    hideLoading();
                }
            }
        });


        mPlayWebview.setWebViewClient(
                new WebViewClient() {
                    @Override
                    public void onPageStarted(WebView view, String url, Bitmap favicon) {
                        super.onPageStarted(view, url, favicon);
                        MainActivity.this.hideLoading();
                    }
                }
        );
    }


    @Override
    public void onBackPressed() {
        if (mPlayWebview.getVisibility() == View.VISIBLE) {
            mPlayWebview.setVisibility(View.GONE);
            mPlayWebview.onPause();
            mPlayWebview.pauseTimers();

        } else {
            if (!mbrowserWebview.canGoBack()) {
                super.onBackPressed();
            } else {
                mbrowserWebview.goBack();
            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPlayWebview != null) {
            mPlayWebview.stopLoading();
            mPlayWebview.getSettings().setJavaScriptEnabled(false);
            mPlayWebview.clearHistory();
            mPlayWebview.removeAllViews();
            mPlayWebview.destroy();
            mPlayWebview = null;
        }
        if (mbrowserWebview != null) {
            mbrowserWebview.stopLoading();
            mbrowserWebview.getSettings().setJavaScriptEnabled(false);
            mbrowserWebview.clearHistory();
            mbrowserWebview.removeAllViews();
            mbrowserWebview.destroy();
            mbrowserWebview = null;
        }
    }
}
