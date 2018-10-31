package com.miguan.youmi.module.common.model;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.miguan.youmi.module.common.contract.PreviewImageContract;

import javax.inject.Inject;


@ActivityScope
public class PreviewImageModel extends BaseModel implements PreviewImageContract.Model {

    @Inject
    public PreviewImageModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}