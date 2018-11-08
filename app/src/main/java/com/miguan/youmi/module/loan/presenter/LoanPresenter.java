package com.miguan.youmi.module.loan.presenter;

import android.app.Application;

import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.FragmentScope;

import com.jess.arms.http.imageloader.ImageLoader;

import javax.inject.Inject;

import com.miguan.youmi.app.base.BaseListPresenter;
import com.miguan.youmi.module.loan.contract.LoanContract;

import java.util.List;

import io.reactivex.Observable;

@FragmentScope
public class LoanPresenter extends BaseListPresenter<Object, LoanContract.Model, LoanContract.View<Object>> {
    @Inject
    Application mApplication;
    @Inject
    AppManager mAppManager;

    @Inject
    public LoanPresenter(LoanContract.Model model, LoanContract.View<Object> rootView) {
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
