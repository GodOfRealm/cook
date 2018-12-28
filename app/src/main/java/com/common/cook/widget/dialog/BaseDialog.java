package com.common.cook.widget.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.common.cook.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Desc:basedialog
 *
 * Author: 张文顺
 * Date: 2018-07-12
 * Copyright: Copyright (c) 2013-2018
 * Company: @米冠网络
 * Update Comments:
 */
public abstract class BaseDialog extends DialogFragment {
    private DialogInterface.OnDismissListener mOnDismissListener;
    private Unbinder mBind;

    //宽度占屏幕的百分比，默认不设置
    protected float getWidthPercent(){
        return -1;
    }

    /**
     * Desc:
     * <p>
     * Author: 张文顺
     * Date: 2018-07-12
     *
     * @param onDismissListener
     */
    public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        mOnDismissListener = onDismissListener;
    }

    @SuppressLint("RestrictedApi")
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.CommonDialogTheme);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(getLayoutRes(), null);
        AlertDialog alertDialog = builder.create();
        alertDialog.setView(view);
        mBind = ButterKnife.bind(this, view);
        initView(view, alertDialog);
        return alertDialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        //设置宽度
        Dialog dialog = getDialog();
        if (dialog != null && -1 != getWidthPercent()) {
            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            dialog.getWindow().setLayout((int) (dm.widthPixels * getWidthPercent()), ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if(null != mOnDismissListener){
            mOnDismissListener.onDismiss(dialog);
        }
    }

    /**
     * Desc:
     * <p>
     * Author: 张文顺
     * Date: 2018-07-12
     *
     * @return int
     */
    protected abstract int getLayoutRes();

    /**
     * Desc:
     * <p>
     * Author: 张文顺
     * Date: 2018-07-12
     *
     * @param view
     * @param dialog
     */
    protected abstract void initView(View view, AlertDialog dialog);

    /*
    /**
     * Desc:
     * <p>
     * Author: 张文顺
     * Date: 2018-07-12
     *
     * @return boolean
     */
    public boolean isShowing() {
        return getDialog() != null && getDialog().isShowing();
    }

    /**
     * Desc:
     * <p>
     * Author: 张文顺
     * Date: 2018-07-12
     *
     * @param fragmentManager
     */
    public void showDialog(FragmentManager fragmentManager) {
        String simpleName = this.getClass().getSimpleName();
        Fragment fragment = fragmentManager.findFragmentByTag(simpleName);
        if (fragment == null || !isShowing()) {
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.add(this, simpleName);
            ft.commitAllowingStateLoss();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBind.unbind();
    }

}
