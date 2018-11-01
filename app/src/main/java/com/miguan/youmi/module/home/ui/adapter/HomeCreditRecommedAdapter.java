package com.miguan.youmi.module.home.ui.adapter;

import android.support.annotation.Nullable;

import com.miguan.youmi.R;
import com.miguan.youmi.core.base.BaseDividerAdapter;
import com.miguan.youmi.core.base.ViewHolder;

import java.util.List;

/**
 * 作者: zws 2018/11/1 0001 14:51
 * 功能描述:
 * 备注:
 */
public class HomeCreditRecommedAdapter extends BaseDividerAdapter<String> {
    public HomeCreditRecommedAdapter(@Nullable List<String> data) {
        super(R.layout.home_recyele_credit_recommend_item, data);
    }

    @Override
    protected void bind(ViewHolder helper, String item) {

    }
}
