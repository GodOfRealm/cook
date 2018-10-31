package com.miguan.youmi.module.common.ui.activity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.just.agentweb.IWebLayout;
import com.miguan.youmi.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.sensorsdata.analytics.android.sdk.SensorsDataAPI;

public class WebLayout implements IWebLayout {

    private SmartRefreshLayout mSmartRefreshLayout;
    private WebView mWebView;

    public WebLayout(Context context) {
        mSmartRefreshLayout = (SmartRefreshLayout) View.inflate(context, R.layout.common_layout_web, null);
        mWebView = mSmartRefreshLayout.findViewById(R.id.common_web_view);

        SensorsDataAPI.sharedInstance().showUpWebView(mWebView, false, true);
    }

    @NonNull
    @Override
    public ViewGroup getLayout() {
        return mSmartRefreshLayout;
    }

    @Nullable
    @Override
    public WebView getWebView() {
        return mWebView;
    }

}
