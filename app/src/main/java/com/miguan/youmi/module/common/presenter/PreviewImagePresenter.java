package com.miguan.youmi.module.common.presenter;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.miguan.youmi.module.common.contract.PreviewImageContract;

import javax.inject.Inject;


/**
 * Desc:
 * <p>
 * Author: SonnyJack
 * Date: 2018-07-19
 * Copyright: Copyright (c) 2013-2018
 * Company: @米冠网络
 * Update Comments:
 */
@ActivityScope
public class PreviewImagePresenter extends BasePresenter<PreviewImageContract.Model, PreviewImageContract.View> {

    /**
     * Desc:
     * <p>
     * Author: SonnyJack
     * Date: 2018-07-19
     *
     * @param model
     * @param rootView
     */
    @Inject
    public PreviewImagePresenter(PreviewImageContract.Model model, PreviewImageContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
