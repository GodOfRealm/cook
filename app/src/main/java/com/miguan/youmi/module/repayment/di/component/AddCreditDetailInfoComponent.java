package com.miguan.youmi.module.repayment.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.miguan.youmi.module.repayment.di.module.AddCreditDetailInfoModule;

import com.jess.arms.di.scope.ActivityScope;
import com.miguan.youmi.module.repayment.ui.activity.AddCreditDetailInfoActivity;

@ActivityScope
@Component(modules = AddCreditDetailInfoModule.class, dependencies = AppComponent.class)
public interface AddCreditDetailInfoComponent {
    void inject(AddCreditDetailInfoActivity activity);
}