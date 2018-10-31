package com.miguan.youmi.module.main.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.miguan.youmi.module.main.contract.SplashActivityContract;
import com.miguan.youmi.module.main.model.SplashActivityModel;


@Module
public class SplashActivityModule {
    private SplashActivityContract.View view;

    /**
     * 构建SplashActivityModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public SplashActivityModule(SplashActivityContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    SplashActivityContract.View provideSplashActivityView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    SplashActivityContract.Model provideSplashActivityModel(SplashActivityModel model) {
        return model;
    }
}