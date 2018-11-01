package com.miguan.youmi.module.home.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.miguan.youmi.R;
import com.miguan.youmi.core.base.BaseDividerAdapter;
import com.miguan.youmi.core.base.ViewHolder;
import com.miguan.youmi.widget.TagListLinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者: zws 2018/11/1 0001 14:51
 * 功能描述:
 * 备注:
 */
public class HomeHotRecommedAdapter extends BaseDividerAdapter<String> {
    public HomeHotRecommedAdapter(@Nullable List<String> data) {
        super(R.layout.home_recyele_hot_recommend_item, data);
    }

    @Override
    protected void bind(ViewHolder helper, String item) {
        TagListLinearLayout<String> layoutView = helper.getView(R.id.home_recyele_hot_item_ll_tags);
        ArrayList<String> titleList = new ArrayList<String>();
        for (int i = 0; i < 3; i++) {
            titleList.add(i + "身份证");
        }
        layoutView.setData(titleList, t -> t);
    }
}
