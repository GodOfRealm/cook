package com.miguan.youmi.module.repayment.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.miguan.youmi.module.repayment.contract.AddCreditBaseInfoContract;
import com.miguan.youmi.module.repayment.model.AddCreditBaseInfoModel;


@Module
public class AddCreditBaseInfoModule {
    private AddCreditBaseInfoContract.View view;

    /**
     * 构建AddCreditBaseInfoModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public AddCreditBaseInfoModule(AddCreditBaseInfoContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    AddCreditBaseInfoContract.View provideAddCreditBaseInfoView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    AddCreditBaseInfoContract.Model provideAddCreditBaseInfoModel(AddCreditBaseInfoModel model) {
        return model;
    }
}