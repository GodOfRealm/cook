package com.miguan.youmi.module.repayment.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.miguan.youmi.module.repayment.contract.ThisCardBalanceContract;
import com.miguan.youmi.module.repayment.model.ThisCardBalanceModel;


@Module
public class ThisCardBalanceModule {
    private ThisCardBalanceContract.View view;

    /**
     * 构建ThisCardBalanceModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public ThisCardBalanceModule(ThisCardBalanceContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    ThisCardBalanceContract.View provideThisCardBalanceView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    ThisCardBalanceContract.Model provideThisCardBalanceModel(ThisCardBalanceModel model) {
        return model;
    }
}