package com.miguan.youmi.module.repayment.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jess.arms.di.component.AppComponent;

import com.miguan.youmi.R;
import com.miguan.youmi.app.BaseActivity;
import com.miguan.youmi.app.ViewConfig;
import com.miguan.youmi.app.constant.ARouterPaths;
import com.miguan.youmi.module.repayment.RepaymentNavigator;
import com.miguan.youmi.module.repayment.di.component.DaggerAddCreditBaseInfoComponent;
import com.miguan.youmi.module.repayment.di.module.AddCreditBaseInfoModule;
import com.miguan.youmi.module.repayment.contract.AddCreditBaseInfoContract;
import com.miguan.youmi.module.repayment.presenter.AddCreditBaseInfoPresenter;
import com.miguan.youmi.module.repayment.ui.dialog.PoundageDialog;
import com.miguan.youmi.widget.CaptchaButton;
import com.miguan.youmi.widget.HPEditText;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Desc:添加信用卡基础信息
 * <p>
 * Author: 张文顺
 * Date: 2018-11-06
 * Copyright: Copyright (c) 2013-2018
 * Company: @米冠网络
 * Update Comments:
 */
@Route(path = ARouterPaths.REPAYMENT_ADD_CREDIT_BASE_INFO)
public class AddCreditBaseInfoActivity extends BaseActivity<AddCreditBaseInfoPresenter> implements AddCreditBaseInfoContract.View {
    @BindView(R.id.repayment_ad_credit_base_et_credit)
    HPEditText mEtCreditNumber;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerAddCreditBaseInfoComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .addCreditBaseInfoModule(new AddCreditBaseInfoModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.repayment_activity_add_credit_base_info; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @OnClick({R.id.repayment_ad_credit_base_tv_action, R.id.repayment_ad_credit_base_tv_camera})
    public void viewOnclicked(View view) {
        switch (view.getId()) {
            case R.id.repayment_ad_credit_base_tv_action:
                getNavigator().getRepaymentNavigator().openAddCardDetailInfo();
                break;
            case R.id.repayment_ad_credit_base_tv_camera:
                PoundageDialog poundageDialog = new PoundageDialog();
                poundageDialog.showDialog(getSupportFragmentManager());
                break;
        }
    }

    @Override
    public ViewConfig getViewConfig() {
        return super.getViewConfig().setToolbarRightText("支持银行").setToolbarRightTextVisible(true).setRightTextClickListener(v -> {
            getNavigator().getRepaymentNavigator().openSelectBankActivity();
        });
    }
}
