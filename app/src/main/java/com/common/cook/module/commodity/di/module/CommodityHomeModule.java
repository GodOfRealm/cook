package com.common.cook.module.commodity.di.module;

import com.jess.arms.di.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;

import com.common.cook.module.commodity.contract.CommodityHomeContract;
import com.common.cook.module.commodity.model.CommodityHomeModel;


@Module
public class CommodityHomeModule {
    private CommodityHomeContract.View view;

    /**
     * 构建CommodityHomeModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public CommodityHomeModule(CommodityHomeContract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    CommodityHomeContract.View provideCommodityHomeView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    CommodityHomeContract.Model provideCommodityHomeModel(CommodityHomeModel model) {
        return model;
    }
}