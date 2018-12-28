package com.common.cook.module.common.presenter;

import com.jess.arms.mvp.BasePresenter;
import com.common.cook.module.common.contract.ShareContract;

import javax.inject.Inject;


public class SharePresenter extends BasePresenter<ShareContract.Model, ShareContract.View> {

    @Inject
    public SharePresenter(ShareContract.Model model, ShareContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
