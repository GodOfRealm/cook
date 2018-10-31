package com.miguan.youmi.module.common.model;

import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.miguan.youmi.app.DefaultTransform;
import com.miguan.youmi.module.common.contract.ShareContract;
import com.miguan.youmi.module.common.model.services.CommonService;

import javax.inject.Inject;

import io.reactivex.Observable;


public class ShareModel extends BaseModel implements ShareContract.Model {

    @Inject
    public ShareModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public Observable<String> shareCallBack(String code) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .shareCallBack(code)
                .compose(new DefaultTransform<>());
    }
}