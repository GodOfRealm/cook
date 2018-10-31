package com.miguan.youmi.module.home.presenter;

import android.app.Application;

import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.FragmentScope;
import com.miguan.pick.bean.home.Data;
import com.jess.arms.http.imageloader.ImageLoader;

import javax.inject.Inject;

import com.miguan.youmi.app.base.BaseListPresenter;
import com.miguan.youmi.module.home.contract.HomeContract;

import java.util.List;

import io.reactivex.Observable;

@FragmentScope
public class HomePresenter extends BaseListPresenter<Object, HomeContract.Model, HomeContract.View<Object>> {
    @Inject
    Application mApplication;
    @Inject
    AppManager mAppManager;

    @Inject
    public HomePresenter(HomeContract.Model model, HomeContract.View<Object> rootView) {
        super(model, rootView);
    }

    @Override
    protected Observable<List<Object>> provideRequestObservable(int page) {
        return null; // mModel.getUser(page);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mAppManager = null;
        this.mApplication = null;
    }
}
