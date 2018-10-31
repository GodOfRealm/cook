package com.miguan.youmi.module.home.di.module;

import com.jess.arms.di.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;

import com.miguan.youmi.module.home.contract.HomeContract;
import com.miguan.youmi.module.home.model.HomeModel;
import com.miguan.pick.bean.home.Data;


@Module
public class HomeModule {
    private HomeContract.View<Object> view;

    /**
     * 构建HomeModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public HomeModule(HomeContract.View<Object> view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    HomeContract.View<Object> provideHomeView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    HomeContract.Model provideHomeModel(HomeModel model) {
        return model;
    }
}