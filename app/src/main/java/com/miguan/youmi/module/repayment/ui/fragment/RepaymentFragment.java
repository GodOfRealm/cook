package com.miguan.youmi.module.repayment.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gyf.barlibrary.ImmersionBar;
import com.jess.arms.di.component.AppComponent;

import com.miguan.youmi.R;
import com.miguan.youmi.app.ViewConfig;
import com.miguan.youmi.app.base.BaseListFragment;
import com.miguan.youmi.app.base.EmptyView;
import com.miguan.youmi.bean.repayment.RepaymentCreditListBean;
import com.miguan.youmi.core.base.BaseAdapter;
import com.miguan.youmi.core.base.BaseMixAdapter;
import com.miguan.youmi.module.repayment.di.component.DaggerRepaymentComponent;
import com.miguan.youmi.module.repayment.di.module.RepaymentModule;
import com.miguan.youmi.module.repayment.contract.RepaymentContract;
import com.miguan.youmi.module.repayment.presenter.RepaymentPresenter;
import com.miguan.youmi.module.repayment.ui.adapter.RepaymentCredit;
import com.miguan.youmi.module.repayment.ui.adapter.RepaymentEmptyCredit;

import butterknife.BindView;


/**
 * Desc:还款主界面
 * <p>
 * Author: 张文顺
 * Date: 2018-11-02
 * Copyright: Copyright (c) 2013-2018
 * Company: @米冠网络
 * Update Comments:
 */
public class RepaymentFragment extends BaseListFragment<Object, RepaymentPresenter> implements RepaymentContract.View<Object> {


    /**
     * Desc:
     * <p>
     * Author: 张文顺
     * Date: 2018-11-02
     *
     * @return repayment fragment
     */
    public static RepaymentFragment newInstance() {
        RepaymentFragment fragment = new RepaymentFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerRepaymentComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .repaymentModule(new RepaymentModule(this))
                .build()
                .inject(this);
    }



    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.repayment_fragment, container, false);
    }

    @Override
    public void begin() {
        //底部
        View top = LayoutInflater.from(getContext()).inflate(R.layout.repayment_foot_layput, getRootView(), false);
        mAdapter.addFooterView(top);
        mAdapter.addData("1");
//        mAdapter.addData(new RepaymentCreditListBean());
        setLoadMoreEnable(false);
        setRefreshEnable(false);
    }

    @Override
    public void setupEmptyView(EmptyView emptyView) {
        emptyView.setEmptyText("没有相关数据哦~")
                .setEmptyImageResource(R.mipmap.ic_launcher)
                .setRetryText("重试");
    }

    @Override
    public BaseAdapter<Object> generateAdapter() {
        BaseMixAdapter baseMixAdapter = new BaseMixAdapter();
        baseMixAdapter.addItemPresenter(new RepaymentEmptyCredit());
        baseMixAdapter.addItemPresenter(new RepaymentCredit());
        return baseMixAdapter;
    }

    @Override
    public ViewConfig getViewConfig() {
        return super.getViewConfig().setStatusBarColor(R.color.colorPrimary);
    }
}
