package com.miguan.youmi.module.repayment.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.miguan.youmi.module.repayment.di.module.ThisCardBalanceModule;

import com.jess.arms.di.scope.ActivityScope;
import com.miguan.youmi.module.repayment.ui.activity.RepaymentActivity;

@ActivityScope
@Component(modules = ThisCardBalanceModule.class, dependencies = AppComponent.class)
public interface ThisCardBalanceComponent {
    void inject(RepaymentActivity activity);
}