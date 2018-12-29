package com.common.cook.module.commodity.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.common.cook.module.commodity.di.module.CommodityHomeModule;

import com.jess.arms.di.scope.FragmentScope;
import com.common.cook.module.commodity.ui.fragment.CommodityHomeFragment;

@FragmentScope
@Component(modules = CommodityHomeModule.class, dependencies = AppComponent.class)
public interface CommodityHomeComponent {
    void inject(CommodityHomeFragment fragment);
}