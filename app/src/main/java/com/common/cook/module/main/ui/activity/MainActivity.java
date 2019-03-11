package com.common.cook.module.main.ui.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.common.cook.R;
import com.common.cook.app.BaseActivity;
import com.common.cook.module.main.contract.MainContract;
import com.common.cook.module.main.di.component.DaggerMainComponent;
import com.common.cook.module.main.di.module.MainModule;
import com.common.cook.module.main.presenter.MainPresenter;
import com.common.cook.util.DaoSharedPreferences;
import com.common.cook.util.UrlUtils;
import com.jess.arms.di.component.AppComponent;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

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

    @OnClick({R.id.main_tv_vip})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.main_tv_vip:
                getNavigator().getVipNavigator().openVipActivity();
                break;

        }
    }

}
