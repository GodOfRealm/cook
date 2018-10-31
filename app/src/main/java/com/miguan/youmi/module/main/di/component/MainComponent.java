package com.miguan.youmi.module.main.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.miguan.youmi.module.main.di.module.MainModule;

import com.jess.arms.di.scope.ActivityScope;
import com.miguan.youmi.module.main.ui.activity.MainActivity;

@ActivityScope
@Component(modules = MainModule.class, dependencies = AppComponent.class)
public interface MainComponent {
    void inject(MainActivity activity);
}