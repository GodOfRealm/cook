package com.miguan.youmi.module.main.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.mvp.IView;
import com.jess.arms.utils.RxLifecycleUtils;
import com.miguan.youmi.app.BaseActivity;


import com.miguan.youmi.app.DefaultObserverImp;
import com.miguan.youmi.app.DefaultTransform;
import com.miguan.youmi.module.main.di.component.DaggerSplashActivityComponent;
import com.miguan.youmi.module.main.di.module.SplashActivityModule;
import com.miguan.youmi.module.main.contract.SplashActivityContract;
import com.miguan.youmi.module.main.presenter.SplashActivityPresenter;

import com.miguan.youmi.R;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

public class SplashActivity extends BaseActivity<SplashActivityPresenter> implements SplashActivityContract.View {

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerSplashActivityComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .splashActivityModule(new SplashActivityModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.main_activity_splash; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        IView iView = this;
        Observable.timer(1500, TimeUnit.MILLISECONDS)
                .compose(new DefaultTransform<>())
                .compose(RxLifecycleUtils.bindUntilEvent(iView, ActivityEvent.DESTROY))
                .subscribe(new DefaultObserverImp<Long>() {
                    @Override
                    public void onNext(Long aLong) {
                        gotoNextPage();
                    }
                });
    }

    /**
     * Desc:启动页显示后跳转到下一个页面
     * <p>
     * Author: SonnyJack
     * Date: 2018-06-28
     */
    private void gotoNextPage() {
        getNavigator().getMainNavigator().openMainActivity();
        finish();
    }

}
