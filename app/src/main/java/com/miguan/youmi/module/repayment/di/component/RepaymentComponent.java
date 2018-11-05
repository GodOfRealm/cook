package com.miguan.youmi.module.repayment.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.miguan.youmi.module.repayment.di.module.RepaymentModule;

import com.jess.arms.di.scope.FragmentScope;
import com.miguan.youmi.module.repayment.ui.fragment.RepaymentFragment;

@FragmentScope
@Component(modules = RepaymentModule.class, dependencies = AppComponent.class)
public interface RepaymentComponent {
    void inject(RepaymentFragment fragment);
}