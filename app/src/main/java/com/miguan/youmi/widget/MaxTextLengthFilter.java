package com.miguan.youmi.widget;

import android.text.InputFilter;
import android.text.Spanned;

import com.miguan.youmi.core.util.PickToast;

/**
 * 作者: zws 2018/7/4 0004 13:54
 * 功能描述:
 * 备注:
 */
public class MaxTextLengthFilter implements InputFilter {

    private int mMaxLength;

    //构造方法中传入最多能输入的字数
    public MaxTextLengthFilter(int max) {
        mMaxLength = max;
    }

    public CharSequence filter(CharSequence source, int start, int end,
                               Spanned dest, int dstart, int dend) {
        int keep = mMaxLength - (dest.length() - (dend - dstart));
        if (keep < (end - start)) {
            PickToast.show("最多只能输入" + mMaxLength + "个字");
        }
        if (keep <= 0) {
            return "";
        } else if (keep >= end - start) {
            return null;
        } else {
            return source.subSequence(start, start + keep);
        }
    }
}
