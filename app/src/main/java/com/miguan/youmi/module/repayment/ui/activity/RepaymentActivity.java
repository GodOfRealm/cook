package com.miguan.youmi.module.repayment.ui.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.SpanUtils;
import com.jess.arms.di.component.AppComponent;

import com.miguan.youmi.R;
import com.miguan.youmi.app.BaseActivity;
import com.miguan.youmi.app.ViewConfig;
import com.miguan.youmi.app.constant.ARouterPaths;
import com.miguan.youmi.app.constant.Constant;
import com.miguan.youmi.app.constant.ExtraConstant;
import com.miguan.youmi.module.repayment.RepaymentType;
import com.miguan.youmi.module.repayment.di.component.DaggerThisCardBalanceComponent;
import com.miguan.youmi.module.repayment.di.module.ThisCardBalanceModule;
import com.miguan.youmi.module.repayment.contract.ThisCardBalanceContract;
import com.miguan.youmi.module.repayment.presenter.ThisCardBalancePresenter;
import com.miguan.youmi.module.repayment.ui.dialog.SafeCodeDemoDialog;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Desc:本卡余额代偿、信用代偿
 * <p>
 * Author: 张文顺
 * Date: 2018-11-05
 * Copyright: Copyright (c) 2013-2018
 * Company: @米冠网络
 * Update Comments:
 */
@Route(path = ARouterPaths.REPAYMENT_THIS_CARD_BALANCE)
public class RepaymentActivity extends BaseActivity<ThisCardBalancePresenter> implements ThisCardBalanceContract.View {
    @BindView(R.id.repayment_this_card_balance_tv_head_message)
    TextView mTvHeadMessage;
    @BindView(R.id.repayment_this_card_balance_tv_poundage_message)
    TextView mTvPoundageMessage;//手续费
    @Autowired(name = ExtraConstant.REPAYMENT_TYPE)
    @RepaymentType.Val
    int mRepaymentType;//代偿模式
    @BindView(R.id.repayment_this_card_balance_tv_mini)
    TextView mTvMini;
    @BindView(R.id.base_v_vertical_line)
    View mVLine;
    @BindView(R.id.repayment_this_card_balance_tv_poundage)
    TextView mTvPoundage;
    @BindView(R.id.repayment_this_card_balance_et_card_balance_count)
    TextView mEtCardBalance;
    @BindView(R.id.repayment_this_card_balance_tv_card_balance_title)
    TextView mTvPoundageOrCardBalance;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerThisCardBalanceComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .thisCardBalanceModule(new ThisCardBalanceModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.repayment_activity_this_card_balance; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        setViewType();

        mTvHeadMessage.setText(new SpanUtils().append("您的每日还款额度为355")
                .append("5000").setTypeface(Typeface.createFromAsset(getAssets(), Constant.TYPEFACE))
                .append(",快来邀请好友提升额度吧")
                .create());
//        mTvPoundageMessage.setText(new SpanUtils().append("预计本次还款手续费：")
//                .append("5").setForegroundColor(getResources().getColor(R.color.colorPrimary)).setTypeface(Typeface.createFromAsset(getAssets(), Constant.TYPEFACE))
//                .append("元")
//                .create());
    }

    /**
     * Desc:根据不同的代偿模式设置不同的样式
     * <p>
     * Author: 张文顺
     * Date: 2018-11-06
     */
    public void setViewType() {
        if (mRepaymentType == RepaymentType.THIS_CARD) {
            mTvMini.setVisibility(View.VISIBLE);
            mVLine.setVisibility(View.VISIBLE);
            mTvPoundage.setVisibility(View.GONE);
            mEtCardBalance.setVisibility(View.VISIBLE);
            mTvPoundageMessage.setVisibility(View.GONE);
            mTvPoundageOrCardBalance.setText(getString(R.string.repayment_this_card_balance_repayment_card_balance));
        } else if (mRepaymentType == RepaymentType.CREDIT) {
            mTvMini.setVisibility(View.GONE);
            mVLine.setVisibility(View.GONE);
            mEtCardBalance.setVisibility(View.GONE);
            mTvPoundage.setVisibility(View.VISIBLE);
            mTvPoundageMessage.setVisibility(View.VISIBLE);
            mTvPoundageMessage.setText(getString(R.string.repayment_this_card_balance_repayment_credit_message));
            mTvPoundageOrCardBalance.setText(getString(R.string.repayment_this_card_balance_repayment_poundage));
            mTvPoundage.setHint(new SpanUtils().append("还款金额*")
                    .append("1.50").setTypeface(Typeface.createFromAsset(getAssets(), Constant.TYPEFACE))
                    .append("%")
                    .create());
        }
    }

    @OnClick({R.id.repayment_this_card_balance_iv_question})
    public void viewOnclicked(View view) {
        switch (view.getId()) {
            case R.id.repayment_this_card_balance_iv_question:
                SafeCodeDemoDialog safeCodeDemoDialog = new SafeCodeDemoDialog();
                safeCodeDemoDialog.showDialog(getSupportFragmentManager());
                break;
        }
    }

    @Override
    public ViewConfig getViewConfig() {
        return super.getViewConfig().setKeyboardEnable(true).setToolbarTitle(settitle());
    }

    /**
     * Desc:根据不同的代偿模式设置不同的title
     * <p>
     * Author: 张文顺
     * Date: 2018-11-06
     */
    private String settitle() {
        if (mRepaymentType == RepaymentType.CREDIT) {
            return "信用代偿";

        } else if (mRepaymentType == RepaymentType.THIS_CARD) {
            return "本卡余额代偿 ";
        }
        return "";
    }
}
