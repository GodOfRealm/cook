package com.miguan.youmi.module.loan.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gyf.barlibrary.ImmersionBar;
import com.jess.arms.di.component.AppComponent;

import com.miguan.youmi.R;
import com.miguan.youmi.app.base.BaseListFragment;
import com.miguan.youmi.app.base.EmptyView;
import com.miguan.youmi.core.base.BaseAdapter;
import com.miguan.youmi.module.loan.di.component.DaggerLoanComponent;
import com.miguan.youmi.module.loan.di.module.LoanModule;
import com.miguan.youmi.module.loan.contract.LoanContract;
import com.miguan.youmi.module.loan.presenter.LoanPresenter;
import com.miguan.youmi.module.loan.ui.adapter.LoanAdapter;


public class LoanFragment extends BaseListFragment<Object, LoanPresenter> implements LoanContract.View<Object> {

    public static LoanFragment newInstance() {
        LoanFragment fragment = new LoanFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerLoanComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .loanModule(new LoanModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void begin() {

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden){
            ImmersionBar immersionBar = ImmersionBar.with(this);
            immersionBar.statusBarColor(R.color.transparent);
            immersionBar.init();
        }
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.loan_fragment, container, false);
    }
    @Override
    public void setupEmptyView(EmptyView emptyView) {
        emptyView.setEmptyText("没有相关数据哦~")
                .setEmptyImageResource(R.mipmap.ic_launcher)
                .setRetryText("重试");
    }

    @Override
    public BaseAdapter<Object> generateAdapter() {
        return new LoanAdapter();
    }
}
