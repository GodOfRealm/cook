package com.miguan.youmi.module.loan.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.miguan.youmi.module.loan.di.module.LoanModule;

import com.jess.arms.di.scope.FragmentScope;
import com.miguan.youmi.module.loan.ui.fragment.LoanFragment;

@FragmentScope
@Component(modules = LoanModule.class, dependencies = AppComponent.class)
public interface LoanComponent {
    void inject(LoanFragment fragment);
}