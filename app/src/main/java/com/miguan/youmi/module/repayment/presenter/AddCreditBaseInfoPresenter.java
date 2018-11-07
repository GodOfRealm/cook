package com.miguan.youmi.module.repayment.presenter;

import android.app.Application;

import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import com.miguan.youmi.module.repayment.contract.AddCreditBaseInfoContract;


@ActivityScope
public class AddCreditBaseInfoPresenter extends BasePresenter<AddCreditBaseInfoContract.Model, AddCreditBaseInfoContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    AppManager mAppManager;

    @Inject
    public AddCreditBaseInfoPresenter(AddCreditBaseInfoContract.Model model, AddCreditBaseInfoContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mApplication = null;
    }
}
