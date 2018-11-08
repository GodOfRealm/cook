package com.miguan.youmi.module.repayment.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.miguan.youmi.module.repayment.contract.AddCreditDetailInfoContract;
import com.miguan.youmi.module.repayment.model.AddCreditDetailInfoModel;


@Module
public class AddCreditDetailInfoModule {
    private AddCreditDetailInfoContract.View view;

    /**
     * 构建AddCreditDetailInfoModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public AddCreditDetailInfoModule(AddCreditDetailInfoContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    AddCreditDetailInfoContract.View provideAddCreditDetailInfoView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    AddCreditDetailInfoContract.Model provideAddCreditDetailInfoModel(AddCreditDetailInfoModel model) {
        return model;
    }
}