package com.common.cook.module.common.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.common.cook.module.common.di.module.ShareModule;

import dagger.Component;

@ActivityScope
@Component(modules = ShareModule.class, dependencies = AppComponent.class)
public interface ShareComponent {
}