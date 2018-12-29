package com.common.cook.module.commodity.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.common.cook.R;
import com.common.cook.app.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.common.cook.module.commodity.di.component.DaggerCommodityHomeComponent;
import com.common.cook.module.commodity.di.module.CommodityHomeModule;
import com.common.cook.module.commodity.contract.CommodityHomeContract;
import com.common.cook.module.commodity.presenter.CommodityHomePresenter;


import static com.jess.arms.utils.Preconditions.checkNotNull;


public class CommodityHomeFragment extends BaseFragment<CommodityHomePresenter> implements CommodityHomeContract.View {

    public static CommodityHomeFragment newInstance() {
        CommodityHomeFragment fragment = new CommodityHomeFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerCommodityHomeComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .commodityHomeModule(new CommodityHomeModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.commodity_fragment_commodity_home, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

}
