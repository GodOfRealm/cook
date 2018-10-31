package com.miguan.youmi.module.common.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.miguan.youmi.module.common.contract.PreviewImageContract;
import com.miguan.youmi.module.common.model.PreviewImageModel;

import dagger.Module;
import dagger.Provides;


@Module
public class PreviewImageModule {
    private PreviewImageContract.View view;

    /**
     * 构建PreviewImageModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public PreviewImageModule(PreviewImageContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    PreviewImageContract.View providePreviewImageView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    PreviewImageContract.Model providePreviewImageModel(PreviewImageModel model) {
        return model;
    }
}