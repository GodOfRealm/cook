package com.common.cook.module.common.model;

import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.common.cook.app.DefaultTransform;
import com.common.cook.module.common.contract.ShareContract;
import com.common.cook.module.common.model.services.CommonService;

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