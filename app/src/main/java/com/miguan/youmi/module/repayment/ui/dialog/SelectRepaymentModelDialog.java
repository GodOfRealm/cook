package com.miguan.youmi.module.repayment.ui.dialog;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.miguan.youmi.R;
import com.miguan.youmi.widget.RecyclerViewGridSpace;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Desc: 选择代偿模式
 * <p>
 * Author: 张文顺
 * Date: 2018-07-11
 * Copyright: Copyright (c) 2013-2018
 * Company: @米冠网络
 * Update Comments:
 */
public class SelectRepaymentModelDialog extends BottomSheetDialog {

//    SelectPriceCallback mCallback;
    Context mContext;



    public SelectRepaymentModelDialog(@NonNull Context context) {
        super(context);
        mContext = context;
        init();
    }


    private void init() {
        //设置视图
        View view = LayoutInflater.from(getContext()).inflate(R.layout.repayment_select_repayment_model_dialog, null);
        ButterKnife.bind(this, view);
        setContentView(view);
        //设置默认弹出高度
        BottomSheetBehavior mBehavior = BottomSheetBehavior.from((View) view.getParent());
        mBehavior.setPeekHeight(SizeUtils.dp2px(400));

    }

    @OnClick({R.id.repayment_select_model_iv_close,R.id.repayment_select_model_iv_question})
    public void viewOnclicked(View view) {
        switch (view.getId()) {
            case R.id.repayment_select_model_iv_close:
                dismiss();
                break;
            case R.id.repayment_select_model_iv_question:

                break;
        }
    }

//
//    public void setSelectCallback(SelectPriceCallback callback) {
//        mCallback = callback;
//    }
//
//    public interface SelectPriceCallback {
//        public void selectPrice(AcceptOrderSettingBean.UserSkillsBean.PricesBean selectedPrice);
//    }

}
