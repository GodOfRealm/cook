package com.common.cook.module.vip.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.common.cook.module.vip.contract.vipContract;
import com.common.cook.module.vip.model.vipModel;


@Module
public class vipModule {
    private vipContract.View view;

    /**
     * 构建vipModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public vipModule(vipContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    vipContract.View providevipView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    vipContract.Model providevipModel(vipModel model) {
        return model;
    }
}