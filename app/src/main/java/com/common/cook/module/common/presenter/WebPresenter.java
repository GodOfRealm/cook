package com.common.cook.module.common.presenter;

import android.app.Application;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.just.agentweb.AbsAgentWebSettings;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.DefaultWebClient;
import com.just.agentweb.IAgentWebSettings;
import com.common.cook.R;
import com.common.cook.app.ServicesObserver;
import com.common.cook.app.constant.Constant;
import com.common.cook.app.constant.EventBusTags;
import com.common.cook.bean.Download;
import com.common.cook.bean.WebShare;
import com.common.cook.core.util.GsonUtil;
import com.common.cook.module.common.contract.WebContract;
import com.common.cook.module.common.model.ShareModel;
import com.common.cook.util.CommonUtils;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.simple.eventbus.Subscriber;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;

/**
 * Desc:
 * <p>
 * Author: SonnyJack
 * Date: 2018-09-10
 * Copyright: Copyright (c) 2013-2018
 * Company: @米冠网络
 * Update Comments:
 */
@ActivityScope
public class WebPresenter extends BasePresenter<WebContract.Model, WebContract.View> {

    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;
    @Inject
    DownloadPresenter mDownloadPresenter;
    @Inject
    ShareModel mShareModel;

    protected AgentWeb mAgentWeb;

    /**
     * Desc:
     * <p>
     * Author: SonnyJack
     * Date: 2018-09-10
     *
     * @param model
     * @param rootView
     */
    @Inject
    public WebPresenter(WebContract.Model model, WebContract.View rootView) {
        super(model, rootView);
    }

    /**
     * Desc:
     * <p>
     * Author: SonnyJack
     * Date: 2018-09-10
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate() {
        loadUrl();
    }

    private void loadUrl() {
        mAgentWeb = AgentWeb.with(mRootView.getActivity())
                .setAgentWebParent(mRootView.getLinearLayout(), new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator(mRootView.getActivity().getResources().getColor(R.color.colorPrimary))
                .setWebChromeClient(mRootView.getWebChromeClient())
                .setWebViewClient(mRootView.getWebViewClient())
                .setAgentWebWebSettings(getSetting())
                .setMainFrameErrorView(R.layout.agentweb_error_page, -1)
                .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
                .setWebLayout(mRootView.getWebLayout())
                .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)//打开其他应用时，弹窗咨询用户是否前往其他应用
                .interceptUnkownUrl() //拦截找不到相关页面的Scheme
                .createAgentWeb()
                .ready()
                .go(mRootView.getUrl());
        mAgentWeb.getJsInterfaceHolder().addJavaObject("android", new AndroidInterface(mRootView.getActivity()));
    }

    /**
     * Desc:
     * <p>
     * Author: SonnyJack
     * Date: 2018-09-10
     *
     * @param bundle
     */
    @Subscriber(tag = EventBusTags.ON_WEB_RELOAD)
    public void onReload(Bundle bundle) {
        reload();
    }

    /**
     * Desc:
     * <p>
     * Author: SonnyJack
     * Date: 2018-09-10
     */
    public void reload() {
        mAgentWeb.getUrlLoader().reload();
    }

    /**
     * Desc:
     * <p>
     * Author: SonnyJack
     * Date: 2018-09-10
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    void onPause() {
        mAgentWeb.getWebLifeCycle().onPause();
    }

    /**
     * Desc:
     * <p>
     * Author: SonnyJack
     * Date: 2018-09-10
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    void onResume() {
        mAgentWeb.getWebLifeCycle().onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
        mAgentWeb.getWebLifeCycle().onDestroy();
    }

    /**
     * Desc:
     * <p>
     * Author: SonnyJack
     * Date: 2018-09-10
     *
     * @return agent web
     */
    public AgentWeb getAgentWeb() {
        return mAgentWeb;
    }

    private AbsAgentWebSettings getSetting() {
        return new AbsAgentWebSettings() {
            @Override
            protected void bindAgentWebSupport(AgentWeb agentWeb) {

            }

            @Override
            public IAgentWebSettings toSetting(WebView webView) {
                super.toSetting(webView);
                getWebSettings().setMinimumFontSize(1);
                return this;
            }
        };
    }

    /**
     * Desc:
     * <p>
     * Author: SonnyJack
     * Date: 2018-09-10
     *
     * @param shareParams
     */
    public void startShare(String shareParams) {
        WebShare webShare = GsonUtil.parse(shareParams, WebShare.class);
        if (null == webShare) {
            return;
        }
        //分享的是图片
        if (webShare.getType() == WebShare.SHARE_TYPE_IMAGE && CommonUtils.isHttpUrl(webShare.getShareImageURL())) {
            Download download = new Download(EventBusTags.DOWNLOAD_WEB_IMAGE_SHARE);
            download.setUrl(webShare.getShareImageURL());
            download.setFileDir(Constant.IMAGE_PATH);
            download.setObject(webShare);
            mDownloadPresenter.download(download, true);
        } else {
            startShare(webShare);
        }
    }

    /**
     * Desc:
     * <p>
     * Author: SonnyJack
     * Date: 2018-09-10
     *
     * @param download
     */
    @Subscriber(tag = EventBusTags.DOWNLOAD_WEB_IMAGE_SHARE)
    public void downloadImageComplete(Download download) {
        if (!download.isSuccess()) {
            return;
        }
        WebShare webShare = (WebShare) download.getObject();
        webShare.setShareImageURL(download.getLocalUrl());
        startShare(webShare);
    }

    /**
     * Desc: 分享
     * <p>
     * Author: SonnyJack
     * Date: 2018-09-06
     *
     * @param webShare
     */
    private void startShare(WebShare webShare) {

    }


    /**
     * Desc:开始分享后回调
     * <p>
     * Author: SonnyJack
     * Date: 2018-09-10
     *
     * @param webShare
     */
    public void startShareCallBack(WebShare webShare) {
        if (null == webShare) {
            return;
        }
        if (mRootView.isAnalysis()) {
//            AnalysisManager.getInstance().onVoiceShared(getShareTypeText(webShare.getShareMedia()));
        }
        if (!TextUtils.isEmpty(webShare.getShareCode())) {
            mShareModel.shareCallBack(webShare.getShareCode())
                    .subscribe(new ServicesObserver<String>() {
                        @Override
                        public void onNext(String s) {

                        }

                        @Override
                        public void onError(int code, String msg) {

                        }
                    });
        }
    }

    private String getShareTypeText(SHARE_MEDIA shareMedia) {
        // 微信好友 / 朋友圈 / QQ好友 / QQ空间
        switch (shareMedia) {
            case QQ:
                return "QQ好友";
            case QZONE:
                return "QQ空间";
            case WEIXIN:
                return "微信好友";
            case WEIXIN_CIRCLE:
            default:
                return "朋友圈";
        }
    }

}
