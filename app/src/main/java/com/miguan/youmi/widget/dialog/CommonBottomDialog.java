package com.miguan.youmi.widget.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.SizeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.miguan.youmi.R;
import com.miguan.youmi.core.base.BaseAdapter;
import com.miguan.youmi.core.base.ViewHolder;
import com.miguan.youmi.widget.RecyclerViewLinearLayoutSpace;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Desc:  顶部弹出选项Dialog
 * <p>
 * Author: SonnyJack
 * Date: 2018-08-10
 * Copyright: Copyright (c) 2013-2018
 * Company: @米冠网络
 * Update Comments:
 */
public class CommonBottomDialog extends BottomSheetDialog {

    private Builder mBuilder;

    @BindView(R.id.common_rv_bottom)
    RecyclerView mRecyclerView;

    private CommonBottomDialog(@NonNull Context context) {
        super(context);
    }

    private CommonBottomDialog(@NonNull Context context, int theme) {
        super(context, theme);
    }

    private void setBuilder(Builder builder) {
        mBuilder = builder;
        init();
    }

    @OnClick(R.id.common_tv_bottom_cancel)
    public void onCancel(){
        dismiss();
    }

    private void init() {
        View view = View.inflate(mBuilder.context, R.layout.common_dialog_bottom, null);
        ButterKnife.bind(this, view);
        setContentView(view);
        //透明
        getWindow().findViewById(R.id.design_bottom_sheet).setBackgroundResource(android.R.color.transparent);

        initRecyclerView();
        setData();
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mBuilder.context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        int itemSize = SizeUtils.dp2px(0.5f);
        RecyclerViewLinearLayoutSpace recyclerViewLinearLayoutSpace = new RecyclerViewLinearLayoutSpace(
                LinearLayoutManager.HORIZONTAL,
                itemSize,
                mBuilder.context.getResources().getColor(R.color.color_eeeeee)
        );
        mRecyclerView.addItemDecoration(recyclerViewLinearLayoutSpace);
    }

    private void setData() {
        ArrayAdapter arrayAdapter = new ArrayAdapter(R.layout.common_vertical_item, mBuilder.menus);
        arrayAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (mBuilder.onItemClickListener != null) {
                mBuilder.onItemClickListener.onItemClick(adapter, view, position);
            }
            dismiss();
        });
        mRecyclerView.setAdapter(arrayAdapter);
    }

    private class ArrayAdapter extends BaseAdapter<String> {

        public ArrayAdapter(int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(ViewHolder helper, String item) {
            TextView textView = helper.getView(R.id.common_tv_vertical_item);
            textView.setTextColor(textView.getContext().getResources().getColor(R.color.color_text_666666));
            textView.setTextSize(16);
            textView.setText(item);
        }
    }

    public static class Builder {
        private Context context;
        private ArrayList<String> menus;
        private BaseQuickAdapter.OnItemClickListener onItemClickListener;

        public Builder setMenus(ArrayList<String> menus) {
            this.menus = menus;
            return this;
        }

        public Builder setOnItemClickListener(BaseQuickAdapter.OnItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
            return this;
        }

        public CommonBottomDialog build(Context context) {
            this.context = context;
            CommonBottomDialog commonBottomDialog = new CommonBottomDialog(this.context);
            commonBottomDialog.setBuilder(this);
            return commonBottomDialog;
        }
    }
}
