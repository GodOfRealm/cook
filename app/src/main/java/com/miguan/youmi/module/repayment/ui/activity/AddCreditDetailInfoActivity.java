package com.miguan.youmi.module.repayment.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jess.arms.di.component.AppComponent;

import com.miguan.youmi.R;
import com.miguan.youmi.app.BaseActivity;
import com.miguan.youmi.app.ViewConfig;
import com.miguan.youmi.app.constant.ARouterPaths;
import com.miguan.youmi.module.repayment.di.component.DaggerAddCreditDetailInfoComponent;
import com.miguan.youmi.module.repayment.di.module.AddCreditDetailInfoModule;
import com.miguan.youmi.module.repayment.contract.AddCreditDetailInfoContract;
import com.miguan.youmi.module.repayment.presenter.AddCreditDetailInfoPresenter;
import com.miguan.youmi.widget.CaptchaButton;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Desc:添加信用卡详细信息
 * <p>
 * Author: 张文顺
 * Date: 2018-11-07
 * Copyright: Copyright (c) 2013-2018
 * Company: @米冠网络
 * Update Comments:
 */
@Route(path = ARouterPaths.REPAYMENT_ADD_CREDIT_DETAIL_INFO)
public class AddCreditDetailInfoActivity extends BaseActivity<AddCreditDetailInfoPresenter> implements AddCreditDetailInfoContract.View {
    @BindView(R.id.repayment_add_credit_detail_bt_captcha)
    CaptchaButton mCaptchaButton;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerAddCreditDetailInfoComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .addCreditDetailInfoModule(new AddCreditDetailInfoModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.repayment_activity_add_credit_detail_info; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @OnClick({R.id.repayment_add_credit_detail_bt_captcha})
    public void viewOnclicked(View view) {
        switch (view.getId()) {
            case R.id.repayment_add_credit_detail_bt_captcha:
                mCaptchaButton.startTickWork();
                break;
        }
    }

    @Override
    public ViewConfig getViewConfig() {
        return super.getViewConfig().setKeyboardEnable(true);
    }
}
