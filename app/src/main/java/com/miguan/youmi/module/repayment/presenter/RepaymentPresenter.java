package com.miguan.youmi.module.repayment.presenter;

import android.app.Application;

import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.FragmentScope;


import javax.inject.Inject;

import com.miguan.youmi.app.base.BaseListPresenter;
import com.miguan.youmi.module.repayment.contract.RepaymentContract;

import java.util.List;

import io.reactivex.Observable;

@FragmentScope
public class RepaymentPresenter extends BaseListPresenter<Object, RepaymentContract.Model, RepaymentContract.View<Object>> {
    @Inject
    Application mApplication;
    @Inject
    AppManager mAppManager;

    @Inject
    public RepaymentPresenter(RepaymentContract.Model model, RepaymentContract.View<Object> rootView) {
        super(model, rootView);
    }

    @Override
    protected Observable<List<Object>> provideRequestObservable(int page) {
        return null; // mModel.getUser(page);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mAppManager = null;
        this.mApplication = null;
    }
}
