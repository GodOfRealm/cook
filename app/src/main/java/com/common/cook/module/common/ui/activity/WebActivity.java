package com.common.cook.module.common.ui.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.jess.arms.di.component.AppComponent;
import com.just.agentweb.IWebLayout;
import com.common.cook.R;
import com.common.cook.app.BaseActivity;
import com.common.cook.app.ViewConfig;
import com.common.cook.app.constant.ARouterPaths;
import com.common.cook.app.constant.ExtraConstant;
import com.common.cook.bean.WebShare;
import com.common.cook.core.util.PickToast;
import com.common.cook.module.common.contract.DownloadContract;
import com.common.cook.module.common.contract.WebContract;
import com.common.cook.module.common.di.component.DaggerWebComponent;
import com.common.cook.module.common.di.module.DownloadModule;
import com.common.cook.module.common.di.module.WebModule;
import com.common.cook.module.common.model.constant.CustomType;
import com.common.cook.module.common.presenter.WebPresenter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Desc: 加载网页
 * <p>
 * Author: 廖培坤
 * Date: 2018-07-24
 * Copyright: Copyright (c) 2013-2018
 * Company: @米冠网络
 * Update Comments:
 */
@Route(path = ARouterPaths.COMMON_WEB)
public class WebActivity extends BaseActivity<WebPresenter> implements WebContract.View, DownloadContract.View {

    /**
     *
     */
    @BindView(R.id.common_ll_web_root)
    LinearLayout mLlWebRoot;
    /**
     *
     */
    @BindView(R.id.toolbar)
    LinearLayout mLlWebtoolbar;
    /**
     *
     */
    @BindView(R.id.base_toolbar_close)
    ImageView mIcClose;

    @Autowired(name = ExtraConstant.RIGHT_CUSTOM_TYPE)
    @CustomType.Type
    int mCustomType;

    @Autowired(name = ExtraConstant.WEB_IS_ANALYSIS)
    boolean mIsAnalysis;

    private SmartRefreshLayout mSmartRefreshLayout;

    private WebChromeClient mWebChromeClient = new WebChromeClient() {

        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            PickToast.show(message);
            return super.onJsAlert(view, url, message, result);
        }

        @Override
        public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
            return super.onConsoleMessage(consoleMessage);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            if (!TextUtils.isEmpty(title)) {
                TextView tvTitle = findViewById(R.id.base_toolbar_title);
                if (tvTitle != null) tvTitle.setText(title);
            }
        }

    };

    private WebViewClient mWebViewClient = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return super.shouldOverrideUrlLoading(view, request);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            mSmartRefreshLayout.finishRefresh();
            if (mPresenter.getAgentWeb().getWebCreator().getWebView().canGoBack()) {
                mIcClose.setVisibility(View.VISIBLE);
            } else {
                mIcClose.setVisibility(View.GONE);
            }
        }
    };

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerWebComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .webModule(new WebModule(this))
                .downloadModule(new DownloadModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.common_activity_web; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
    }

    @Override
    public ViewConfig getViewConfig() {
        return super.getViewConfig().setRightCustomType(mCustomType);
    }

    @Override
    public String getUrl() {
        return getIntent().getStringExtra(ExtraConstant.WEB_URL);
    }

    @Override
    public IWebLayout getWebLayout() {
        WebLayout webLayout = new WebLayout(this);
        mSmartRefreshLayout = (SmartRefreshLayout) webLayout.getLayout();
        mSmartRefreshLayout.setOnRefreshListener(refreshLayout -> {
            mPresenter.reload();
        });
        mSmartRefreshLayout.setEnableLoadMore(false);

        return webLayout;
    }

    @Override
    public LinearLayout getLinearLayout() {
        return mLlWebRoot;
    }

    @Override
    public WebChromeClient getWebChromeClient() {
        return mWebChromeClient;
    }

    @Override
    public WebViewClient getWebViewClient() {
        return mWebViewClient;
    }

    /**
     * Desc:
     * <p>
     * Author: SonnyJack
     * Date: 2018-09-06
     *
     * @param view
     */
    @OnClick(R.id.base_toolbar_close)
    public void close(View view) {
        killMyself();
    }

    /**
     * Desc:
     * <p>
     * Author: SonnyJack
     * Date: 2018-09-06
     *
     * @param view
     */
    @OnClick(R.id.base_toolbar_back)
    public void back(View view) {
        onBackPressed();
    }

    /**
     * 隐藏状态栏以及置顶布局
     */
    public void hideToolbar() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mLlWebtoolbar.setVisibility(View.GONE);
            }
        });

    }

    /**
     * h5 callback 回调
     */
    public void goBack() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (!mPresenter.getAgentWeb().getWebCreator().getWebView().canGoBack()) {
            super.onBackPressed();
        } else {
            mPresenter.getAgentWeb().back();
        }
    }


    /**
     * Desc: 显示右上角分享按钮
     * <p>
     * Author: SonnyJack
     * Date: 2018-09-06
     *
     * @param shareParams
     */
    public void createNavShareItem(String shareParams) {
        if (TextUtils.isEmpty(shareParams)) {
            return;
        }
        runOnUiThread(() -> {
            ImageView right = findViewById(R.id.base_toolbar_right);
            if (null != right) {
                right.setVisibility(View.VISIBLE);
                right.setImageResource(R.mipmap.profile_ic_share_black);
                right.setOnClickListener((v) -> mPresenter.startShare(shareParams));
            }
        });
    }


    /**
     * Desc:开始分享回调
     * <p>
     * Author: SonnyJack
     * Date: 2018-09-10
     *
     * @param webShare
     */
    public void startShareCallBack(WebShare webShare) {
        mPresenter.startShareCallBack(webShare);
    }

    @Override
    public boolean isAnalysis() {
        return mIsAnalysis;
    }

}
