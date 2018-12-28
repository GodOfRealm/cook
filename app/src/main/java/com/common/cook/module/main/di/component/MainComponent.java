package com.common.cook.module.main.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.common.cook.module.main.di.module.MainModule;

import com.jess.arms.di.scope.ActivityScope;
import com.common.cook.module.main.ui.activity.MainActivity;

@ActivityScope
@Component(modules = MainModule.class, dependencies = AppComponent.class)
public interface MainComponent {
    void inject(MainActivity activity);
}