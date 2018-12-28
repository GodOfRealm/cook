package com.common.cook.module.common.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.common.cook.module.common.di.module.DownloadModule;
import com.common.cook.module.common.di.module.PreviewImageModule;
import com.common.cook.module.common.ui.activity.PreviewImageActivity;

import dagger.Component;

@ActivityScope
@Component(modules = {PreviewImageModule.class, DownloadModule.class}, dependencies = AppComponent.class)
public interface PreviewImageComponent {
    void inject(PreviewImageActivity activity);
}