package com.common.cook.module.common.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.common.cook.module.common.di.module.DownloadModule;
import com.common.cook.module.common.di.module.WebModule;
import com.common.cook.module.common.ui.activity.WebActivity;

import dagger.Component;

@ActivityScope
@Component(modules = {WebModule.class, DownloadModule.class}, dependencies = AppComponent.class)
public interface WebComponent {
    void inject(WebActivity activity);
}