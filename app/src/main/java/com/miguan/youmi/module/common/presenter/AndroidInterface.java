package com.miguan.youmi.module.common.presenter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.webkit.JavascriptInterface;

import com.blankj.utilcode.util.PhoneUtils;
import com.miguan.youmi.app.Navigator;
import com.miguan.youmi.core.util.PickToast;
import com.miguan.youmi.module.common.ui.activity.WebActivity;

import com.miguan.youmi.util.CommonUtils;

/**
 * Desc: 暴露给JS调用的方法
 * <p>
 * Author: 廖培坤
 * Date: 2018-07-24
 * Copyright: Copyright (c) 2013-2018
 * Company: @米冠网络
 * Update Comments:
 */
public class AndroidInterface {

    private Activity mActivity;

    /**
     * Desc:
     * <p>
     * Author: 张文顺
     * Date: 2018-08-10
     *
     * @param activity
     */
    public AndroidInterface(Activity activity) {
        mActivity = activity;
    }

    /**
     * Desc: Toast
     * <p>
     * Author: 廖培坤
     * Date: 2018-07-24
     *
     * @param msg
     */
    @JavascriptInterface
    public void toast(String msg) {
        PickToast.show(msg);
    }

    /**
     * Desc: 关闭当前页面
     * <p>
     * Author: SonnyJack
     * Date: 2018-09-06
     *
     * @param params
     */
    public void close(String params) {
        if (null == mActivity) {
            return;
        }
        mActivity.runOnUiThread(() -> mActivity.finish());
    }

    /**
     * Desc: 获取登录Token
     * <p>
     * Author: 廖培坤
     * Date: 2018-07-24
     *
     * @return string
     */
    @JavascriptInterface
    public String getUserToken() {
        return CommonUtils.getToken();
    }

    /**
     * Desc: 获取id
     * <p>
     * Author: 张文顺
     * Date: 2018-07-24
     *
     * @return string
     */
    @JavascriptInterface
    public String getUserId() {
        return CommonUtils.getUserId();
    }

    /**
     * Desc:
     * <p>
     * Author: 廖培坤
     * Date: 2018-08-09
     *
     * @param platformType 1-微信好友 2-朋友圈 3-QQ好友 4-QQ空间 5-新浪微博
     * @param url
     * @param title
     * @param content
     * @param imageUrl
     */
    @JavascriptInterface
    public void share(int platformType, String url, String title, String content, String imageUrl) {

    }

    /**
     * Desc:获取设备号
     * <p>
     * Author: 张文顺
     * Date: 2018-08-13
     *
     * @return string
     */
    @JavascriptInterface
    public String getDeviceNumber() {
        return getImei();
    }

    /**
     * Desc:
     * <p>
     * Author: SonnyJack
     * Date: 2018-09-10
     *
     * @return string
     */
    @SuppressLint("MissingPermission")
    public String getImei() {
        String imei = "";
        try {
            imei = PhoneUtils.getIMEI();
        } catch (Exception ignore) {

        }
        return imei;
    }


    /**
     * Desc: 打开微信
     * <p>
     * Author: 廖培坤
     * Date: 2018-08-08
     */
    @JavascriptInterface
    public void openWechat() {
        Navigator.getInstance().getCommonNavigator().openWechat(mActivity);
    }

    /**
     * Desc:打开qq
     * <p>
     * Author: 张文顺
     * Date: 2018-08-10
     */
    @JavascriptInterface
    public void openQQ() {
        Navigator.getInstance().getCommonNavigator().openQQ(mActivity);
    }



    /**
     * Desc:
     * <p>
     * Author: 张文顺
     * Date: 2018-08-10
     */
    @JavascriptInterface
    public void navHidden() {
        ((WebActivity) mActivity).hideToolbar();
    }

    /**
     * Desc:
     * <p>
     * Author: 张文顺
     * Date: 2018-08-10
     */
    @JavascriptInterface
    public void navGoBack() {
        ((WebActivity) mActivity).goBack();
    }

    /**
     * Desc: 显示右上角分享按钮
     * <p>
     * Author: SonnyJack
     * Date: 2018-09-06
     */
    @JavascriptInterface
    public void createNavShareItem(String params) {
        if (null == mActivity || !(mActivity instanceof WebActivity)) {
            return;
        }
        ((WebActivity) mActivity).createNavShareItem(params);
    }


}
