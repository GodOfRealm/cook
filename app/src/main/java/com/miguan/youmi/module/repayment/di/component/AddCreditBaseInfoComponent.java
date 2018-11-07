package com.miguan.youmi.module.repayment.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.miguan.youmi.module.repayment.di.module.AddCreditBaseInfoModule;

import com.jess.arms.di.scope.ActivityScope;
import com.miguan.youmi.module.repayment.ui.activity.AddCreditBaseInfoActivity;

@ActivityScope
@Component(modules = AddCreditBaseInfoModule.class, dependencies = AppComponent.class)
public interface AddCreditBaseInfoComponent {
    void inject(AddCreditBaseInfoActivity activity);
}