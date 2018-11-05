package com.miguan.youmi.module.repayment.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.miguan.youmi.bean.repayment.BankItemBean;
import com.miguan.youmi.module.repayment.contract.SelectBankContract;
import com.miguan.youmi.module.repayment.model.SelectBankModel;


@Module
public class SelectBankModule {
    private SelectBankContract.View<BankItemBean> view;

    /**
     * 构建SelectBankModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public SelectBankModule(SelectBankContract.View<BankItemBean> view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    SelectBankContract.View<BankItemBean> provideSelectBankView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    SelectBankContract.Model provideSelectBankModel(SelectBankModel model) {
        return model;
    }
}