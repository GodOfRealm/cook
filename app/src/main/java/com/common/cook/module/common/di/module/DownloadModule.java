package com.common.cook.module.common.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.common.cook.module.common.contract.DownloadContract;
import com.common.cook.module.common.model.DownloadModel;

import dagger.Module;
import dagger.Provides;


@Module
public class DownloadModule {
    private DownloadContract.View view;

    /**
     * 构建DownloadModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public DownloadModule(DownloadContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    DownloadContract.View provideDownloadView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    DownloadContract.Model provideDownloadModel(DownloadModel model) {
        return model;
    }
}