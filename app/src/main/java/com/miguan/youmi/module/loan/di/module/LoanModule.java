package com.miguan.youmi.module.loan.di.module;

import com.jess.arms.di.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;

import com.miguan.youmi.module.loan.contract.LoanContract;
import com.miguan.youmi.module.loan.model.LoanModel;


@Module
public class LoanModule {
    private LoanContract.View<Object> view;

    /**
     * 构建LoanModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public LoanModule(LoanContract.View<Object> view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    LoanContract.View<Object> provideLoanView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    LoanContract.Model provideLoanModel(LoanModel model) {
        return model;
    }
}