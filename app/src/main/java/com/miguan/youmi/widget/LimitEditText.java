package com.miguan.youmi.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatEditText;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.miguan.youmi.core.util.PickToast;
import com.miguan.youmi.R;
import com.miguan.youmi.util.PickUtils;

/**
 * Desc: 字数限制的EditText，中文等于两个英文
 * <p>
 * Author: 廖培坤
 * Date: 2018-07-26
 * Copyright: Copyright (c) 2013-2018
 * Company: @米冠网络
 * Update Comments:
 */
public class LimitEditText extends AppCompatEditText {

    private int mMaxLength;
    private boolean mIsDivChAndEn;
    private boolean mIsToastTip;
    private String mToastMsg;

    public LimitEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public LimitEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LimitEditText);
        int maxLength = a.getInt(R.styleable.LimitEditText_maxCharLength, -1);
        mIsDivChAndEn = a.getBoolean(R.styleable.LimitEditText_isDivChAndEn, false);
        mIsToastTip = a.getBoolean(R.styleable.LimitEditText_isToastTip, true);
        mToastMsg = a.getText(R.styleable.LimitEditText_toastMsg) != null ? a.getText(R.styleable.LimitEditText_toastMsg).toString() : "";

        a.recycle();
        setMaxLength(maxLength);
    }

    public int getMaxLength() {
        return mMaxLength;
    }

    public void setMaxLength(int maxLength) {
        mMaxLength = maxLength;
        if (maxLength > 0) {
            setFilters(new InputFilter[]{new LengthFilter(maxLength)});
        } else {
            setFilters(null);
        }
    }

    /**
     * 字符长度，英文1个，中文2个，emoji4个
     *
     * @return
     */
    public int getCharLength() {
        return calcCharLength(getText());
    }

    private int calcCharLength(CharSequence sequence) {
        return mIsDivChAndEn ? PickUtils.getStringLength(sequence) : sequence.length();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (MotionEvent.ACTION_DOWN == event.getAction()) {
//            clearFocus();
        }
        return super.onTouchEvent(event);
    }

    private class LengthFilter implements InputFilter {

        private int maxLength;


        private LengthFilter(int maxLength) {
            this.maxLength = maxLength;
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            int dlen = calcCharLength(dest);
            int slen = calcCharLength(source);

            if (dlen + slen > maxLength) {
                int lev = Math.min(maxLength - dlen, source.length());
                source = source.subSequence(0, lev);
                while (calcCharLength(source) > maxLength - dlen) {
                    source = source.subSequence(0, source.length() - 1);
                }
                if (mIsToastTip) {
                    PickToast.show("最多输入" + maxLength + "个字");
                }
                return source;
            }
            return null;
        }
    }

}
