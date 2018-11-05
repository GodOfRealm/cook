package com.miguan.youmi.module.repayment.ui.dialog;

import android.support.v7.app.AlertDialog;
import android.view.View;

import com.miguan.youmi.R;
import com.miguan.youmi.widget.dialog.BaseDialog;

import butterknife.OnClick;

/**
 * 作者: zws 2018/11/5 0005 15:48
 * 功能描述:
 * 备注:
 */
public class SafeCodeDemoDialog extends BaseDialog {
    @Override
    protected int getLayoutRes() {
        return R.layout.repayment_safe_code_demo_dialog;
    }

    @Override
    protected void initView(View view, AlertDialog dialog) {

    }

    @OnClick({R.id.repayment_safe_code_demo_close})
    public void viewOnclicked(View view) {
        switch (view.getId()) {
            case R.id.repayment_safe_code_demo_close:
                dismiss();
                break;

        }
    }
}
