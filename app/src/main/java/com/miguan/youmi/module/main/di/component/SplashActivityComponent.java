package com.miguan.youmi.module.main.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.miguan.youmi.module.main.di.module.SplashActivityModule;

import com.jess.arms.di.scope.ActivityScope;
import com.miguan.youmi.module.main.ui.activity.SplashActivity;

@ActivityScope
@Component(modules = SplashActivityModule.class, dependencies = AppComponent.class)
public interface SplashActivityComponent {
    void inject(SplashActivity activity);
}