package com.miguan.youmi.module.home.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jess.arms.di.component.AppComponent;

import com.miguan.youmi.R;
import com.miguan.youmi.app.base.BaseListFragment;
import com.miguan.youmi.app.base.EmptyView;
import com.miguan.youmi.core.base.BaseAdapter;
import com.miguan.youmi.module.home.di.component.DaggerHomeComponent;
import com.miguan.youmi.module.home.di.module.HomeModule;
import com.miguan.youmi.module.home.contract.HomeContract;
import com.miguan.youmi.module.home.presenter.HomePresenter;
import com.miguan.pick.bean.home.Data;

import com.miguan.pick.module.home.ui.adapter.HomeAdapter;

public class HomeFragment extends BaseListFragment<Object, HomePresenter> implements HomeContract.View<Object> {

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerHomeComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .homeModule(new HomeModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void begin() {

    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_fragment, container, false);
    }

    @Override
    public void setupEmptyView(EmptyView emptyView) {
        emptyView.setEmptyText("没有相关数据哦~")
                .setEmptyImageResource(R.mipmap.ic_launcher)
                .setRetryText("重试");
    }

    @Override
    public BaseAdapter<Object> generateAdapter() {
        return new HomeAdapter();
    }
}
