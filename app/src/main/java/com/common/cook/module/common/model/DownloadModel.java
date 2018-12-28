package com.common.cook.module.common.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.common.cook.module.common.contract.DownloadContract;
import com.common.cook.module.common.model.services.CommonService;

import javax.inject.Inject;

import io.reactivex.Observable;
import okhttp3.ResponseBody;


@ActivityScope
public class DownloadModel extends BaseModel implements DownloadContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public DownloadModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<ResponseBody> download(String url) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .download(url);
    }
}