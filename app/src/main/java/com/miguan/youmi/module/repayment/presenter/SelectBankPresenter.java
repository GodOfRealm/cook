package com.miguan.youmi.module.repayment.presenter;

import android.app.Application;

import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;

import com.jess.arms.http.imageloader.ImageLoader;

import javax.inject.Inject;

import com.miguan.youmi.app.base.BaseListPresenter;
import com.miguan.youmi.bean.repayment.BankItemBean;
import com.miguan.youmi.module.repayment.contract.SelectBankContract;

import java.util.List;

import io.reactivex.Observable;

@ActivityScope
public class SelectBankPresenter extends BaseListPresenter<BankItemBean, SelectBankContract.Model, SelectBankContract.View<BankItemBean>> {
    @Inject
    Application mApplication;
    @Inject
    AppManager mAppManager;

    @Inject
    public SelectBankPresenter(SelectBankContract.Model model, SelectBankContract.View<BankItemBean> rootView) {
        super(model, rootView);
    }

    @Override
    protected Observable<List<BankItemBean>> provideRequestObservable(int page) {
        return null; // mModel.getUser(page);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mAppManager = null;
        this.mApplication = null;
    }
}
