package com.miguan.youmi.app.overlay;

import android.app.Activity;
import android.app.ProgressDialog;

/**
 * Desc: 默认Activity功能扩展类
 * <p>
 * Author: 廖培坤
 * Date: 2018-07-06
 * Copyright: Copyright (c) 2013-2018
 * Company: @米冠网络
 * Update Comments:
 */
public class DefaultViewExpansionDelegate extends ViewExpansionDelegate {

    private ProgressDialog mProgressDialog;

    /**
     * Desc:
     * <p>
     * Author: 廖培坤
     * Date: 2018-07-06
     *
     * @param activity
     */
    public DefaultViewExpansionDelegate(Activity activity) {
        super(activity);
    }

    @Override
    public void showProgressBar() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setMessage("请稍等");
        mProgressDialog.setCancelable(true);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();
    }

    @Override
    public void hideProgressBar() {
        if (mProgressDialog != null) mProgressDialog.dismiss();
    }

}
