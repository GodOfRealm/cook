package com.miguan.youmi.module.repayment.ui.dialog;

import android.support.v7.app.AlertDialog;
import android.view.View;

import com.miguan.youmi.R;
import com.miguan.youmi.widget.dialog.BaseDialog;

/**
 * 作者: zws 2018/11/8 0008 17:00
 * 功能描述:手续费弹窗
 * 备注:
 */
public class PoundageDialog extends BaseDialog {
    @Override
    protected int getLayoutRes() {
        return R.layout.repayment_poundage_dialog;
    }

    @Override
    protected void initView(View view, AlertDialog dialog) {

    }
}
