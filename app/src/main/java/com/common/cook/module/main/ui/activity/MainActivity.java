package com.common.cook.module.main.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.common.cook.app.BaseActivity;
import com.jess.arms.di.component.AppComponent;

import com.common.cook.module.main.di.component.DaggerMainComponent;
import com.common.cook.module.main.di.module.MainModule;
import com.common.cook.module.main.contract.MainContract;
import com.common.cook.module.main.presenter.MainPresenter;

import com.common.cook.R;

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMainComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .mainModule(new MainModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.main_activity_main; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }
}
