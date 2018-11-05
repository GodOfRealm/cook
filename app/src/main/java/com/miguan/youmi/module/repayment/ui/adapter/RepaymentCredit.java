package com.miguan.youmi.module.repayment.ui.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.miguan.youmi.R;
import com.miguan.youmi.bean.home.HomeCreditRecommendListBean;
import com.miguan.youmi.bean.repayment.RepaymentCreditListBean;
import com.miguan.youmi.core.base.ItemPresenter;
import com.miguan.youmi.core.base.ViewHolder;
import com.miguan.youmi.module.home.ui.adapter.HomeCreditRecommedAdapter;

import java.util.ArrayList;

/**
 * 作者: zws 2018/11/1 0001 14:44
 * 功能描述:
 * 备注:
 */
public class RepaymentCredit extends ItemPresenter<RepaymentCreditListBean> {
    @Override
    public int getLayoutRes() {
        return R.layout.repayment_recyele_credit;
    }

    @Override
    public void convert(ViewHolder holder, RepaymentCreditListBean repaymentCreditListBean) {
        RecyclerView recyclerView = holder.getView(R.id.repayment_recyele_credit_rv_list);


        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        ArrayList<String> titleList = new ArrayList<String>();
        for (int i = 0; i < 3; i++) {
            titleList.add(i + "");
        }
        RepaymentCreditAdapter adapter = new RepaymentCreditAdapter(titleList);
        recyclerView.setAdapter(adapter);
    }
}
