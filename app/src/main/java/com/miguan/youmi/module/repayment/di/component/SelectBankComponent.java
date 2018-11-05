package com.miguan.youmi.module.repayment.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.miguan.youmi.module.repayment.di.module.SelectBankModule;

import com.jess.arms.di.scope.ActivityScope;
import com.miguan.youmi.module.repayment.ui.activity.SelectBankActivity;

@ActivityScope
@Component(modules = SelectBankModule.class, dependencies = AppComponent.class)
public interface SelectBankComponent {
    void inject(SelectBankActivity activity);
}