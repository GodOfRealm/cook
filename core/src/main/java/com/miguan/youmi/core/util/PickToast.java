package com.miguan.youmi.core.util;

import android.support.annotation.StringRes;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.miguan.youmi.core.R;


/**
 * toast工具类
 *
 * @author liyu
 */
public class PickToast {
    private PickToast() {

    }

    public static void show(@StringRes int res) {
        show(Utils.getApp().getString(res));
    }

    public static void show(String text) {
        TextView view = (TextView) ((ViewGroup) ToastUtils.showCustomShort(R.layout.comment_toast_layout)).getChildAt(0);
        view.setText(text);
    }

    public static void setGravity(int center, int i, int i1) {
        ToastUtils.setGravity(center, i, i1);
    }
}
