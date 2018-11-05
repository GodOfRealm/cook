package com.miguan.youmi.module.repayment.di.module;

import com.jess.arms.di.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;

import com.miguan.youmi.module.repayment.contract.RepaymentContract;
import com.miguan.youmi.module.repayment.model.RepaymentModel;



@Module
public class RepaymentModule {
    private RepaymentContract.View<Object> view;

    /**
     * 构建RepaymentModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public RepaymentModule(RepaymentContract.View<Object> view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    RepaymentContract.View<Object> provideRepaymentView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    RepaymentContract.Model provideRepaymentModel(RepaymentModel model) {
        return model;
    }
}