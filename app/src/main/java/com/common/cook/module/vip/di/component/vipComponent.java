package com.common.cook.module.vip.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.common.cook.module.vip.di.module.vipModule;

import com.jess.arms.di.scope.ActivityScope;
import com.common.cook.module.vip.ui.activity.VipActivity;

@ActivityScope
@Component(modules = vipModule.class, dependencies = AppComponent.class)
public interface vipComponent {
    void inject(VipActivity activity);
}