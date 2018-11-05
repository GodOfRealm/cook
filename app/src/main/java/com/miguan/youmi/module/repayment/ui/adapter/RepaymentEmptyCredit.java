package com.miguan.youmi.module.repayment.ui.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.miguan.youmi.R;
import com.miguan.youmi.bean.home.HomeHotRecommendBean;
import com.miguan.youmi.core.base.ItemPresenter;
import com.miguan.youmi.core.base.ViewHolder;
import com.miguan.youmi.module.home.ui.adapter.HomeHotRecommedAdapter;

import java.util.ArrayList;

/**
 * 作者: zws 2018/11/1 0001 14:44
 * 功能描述:
 * 备注:
 */
public class RepaymentEmptyCredit extends ItemPresenter<String> {
    @Override
    public int getLayoutRes() {
        return R.layout.repayment_recyele_empty_credit;
    }

    @Override
    public void convert(ViewHolder holder, String s) {
    }
}
