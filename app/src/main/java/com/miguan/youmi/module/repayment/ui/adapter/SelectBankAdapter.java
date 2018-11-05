package com.miguan.youmi.module.repayment.ui.adapter;


import com.miguan.youmi.R;
import com.miguan.youmi.bean.repayment.BankItemBean;
import com.miguan.youmi.core.base.BaseAdapter;
import com.miguan.youmi.core.base.ViewHolder;

public class SelectBankAdapter extends BaseAdapter<BankItemBean> {
    public SelectBankAdapter() {
        super(R.layout.repayment_recycle_item_select_bank);
    }

    @Override
    protected void convert(ViewHolder helper, BankItemBean item) {
    }
}
