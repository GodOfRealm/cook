package com.miguan.youmi.app.overlay;

import android.app.Activity;

/**
 * Desc: Activity对自身功能的拓展委托给该类实现
 * 可通过重写Activity的createViewExpansionDelegate提供扩展类
 * <p>
 * Author: 廖培坤
 * Date: 2018-07-06
 * Copyright: Copyright (c) 2013-2018
 * Company: @米冠网络
 * Update Comments:
 */
public abstract class ViewExpansionDelegate {

    private Activity mActivity;

    /**
     * Desc:
     * <p>
     * Author: 廖培坤
     * Date: 2018-07-06
     *
     * @param activity
     */
    public ViewExpansionDelegate(Activity activity) {
        mActivity = activity;
    }

    /**
     * Desc:
     * <p>
     * Author: 廖培坤
     * Date: 2018-07-06
     *
     * @return base activity
     */
    public Activity getActivity() {
        return mActivity;
    }

    /**
     * Desc:
     * <p>
     * Author: 廖培坤
     * Date: 2018-07-06
     */
    public void showProgressBar() {
    }

    /**
     * Desc:
     * <p>
     * Author: 廖培坤
     * Date: 2018-07-06
     *
     * @param msg
     */
    public void showProgressBar(String msg) {
    }

    /**
     * Desc:
     * <p>
     * Author: 廖培坤
     * Date: 2018-07-06
     *
     * @param resId
     */
    public void showLoading(int resId) {
    }

    /**
     * Desc:
     * <p>
     * Author: 廖培坤
     * Date: 2018-07-06
     */
    public void showLoading() {
    }

    /**
     * Desc:
     * <p>
     * Author: 廖培坤
     * Date: 2018-07-06
     */
    public void hideLoading() {
    }

    /**
     * Desc:
     * <p>
     * Author: 廖培坤
     * Date: 2018-07-06
     */
    public void hideProgressBar() {
    }

    /**
     * Desc:
     * <p>
     * Author: 廖培坤
     * Date: 2018-07-06
     */
    public void showErrorPage() {
    }

    /**
     * Desc:
     * <p>
     * Author: 廖培坤
     * Date: 2018-07-06
     *
     * @param text
     */
    public void showErrorToast(String text) {
    }

    /**
     * Desc:
     * <p>
     * Author: 廖培坤
     * Date: 2018-07-06
     *
     * @param throwable
     */
    public void doError(Throwable throwable) {
    }

    /**
     * Desc:
     * <p>
     * Author: 廖培坤
     * Date: 2018-07-06
     */
    public void hideErrorPage() {
    }

    /**
     * Desc:
     * <p>
     * Author: 廖培坤
     * Date: 2018-07-06
     */
    public void showEmptyPage() {
    }

}
