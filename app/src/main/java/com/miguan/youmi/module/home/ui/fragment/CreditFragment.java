package com.miguan.youmi.module.home.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jess.arms.di.component.AppComponent;
import com.miguan.youmi.R;
import com.miguan.youmi.app.BaseFragment;

/**
 * 作者: zws 2018/10/31 0031 16:16
 * 功能描述:首页信用卡代还
 * 备注:
 */
public class CreditFragment extends BaseFragment {
    public static CreditFragment newInstance() {
        CreditFragment fragment = new CreditFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_credit_fragment, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }
}
