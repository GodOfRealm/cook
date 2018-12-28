package com.common.cook.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.common.cook.R;
import com.common.cook.app.DefaultTextWatcher;


/**
 * Desc: 数量加减布局
 * 用：我要下单
 * <p>
 * Author: SonnyJack
 * Date: 2018-07-12
 * Copyright: Copyright (c) 2013-2018
 * Company: @米冠网络
 * Update Comments:
 */
public class AmountLayout extends LinearLayout {

    private int mMinValue = 1;
    private int mMaxValue = 24;

    private EditText mEtValue;

    private OnCountChangedListener mOnCountChangedListener;

    /**
     * Desc:
     * <p>
     * Author: SonnyJack
     * Date: 2018-07-12
     *
     * @param context
     */
    public AmountLayout(Context context) {
        super(context);
        init();
    }

    /**
     * Desc:
     * <p>
     * Author: SonnyJack
     * Date: 2018-07-12
     *
     * @param context
     * @param attrs
     */
    public AmountLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * Desc:
     * <p>
     * Author: SonnyJack
     * Date: 2018-07-12
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public AmountLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.common_layout_amount, null, false);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        addView(view, layoutParams);
        mEtValue = findViewById(R.id.common_amount_et_value);
        mEtValue.addTextChangedListener(new DefaultTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mOnCountChangedListener != null) {
                    mOnCountChangedListener.onCountChange(getValue());
                }
            }
        });
        //减号
        findViewById(R.id.common_amount_tv_reduce).setOnClickListener(v -> {
            int value = getValue();
            if (--value < mMinValue) {
                value = mMinValue;
            }
            setCount(value);
        });

        //加号
        findViewById(R.id.common_amount_tv_add).setOnClickListener(v -> {
            int value = getValue();
            if (++value > mMaxValue) {
                value = mMaxValue;
            }
            setCount(value);
        });
    }

    private void setCount(int count) {
        if (mOnCountChangedListener != null) {
            mOnCountChangedListener.onCountChange(count);
        }
        String text = String.valueOf(count);
        mEtValue.setText(text);
        mEtValue.setSelection(text.length());
    }

    /**
     * Desc: 设置最大值
     * <p>
     * Author: 廖培坤
     * Date: 2018-07-27
     *
     * @param maxValue
     */
    public void setMaxValue(int maxValue) {
        mMaxValue = maxValue;
    }

    /**
     * Desc: 设置最小值
     * <p>
     * Author: 廖培坤
     * Date: 2018-07-27
     *
     * @param minValue
     */
    public void setMinValue(int minValue) {
        mMinValue = minValue;
    }

    /**
     * Desc:
     * <p>
     * Author: SonnyJack
     * Date: 2018-07-12
     *
     * @return int
     */
    public int getValue() {
        String value = mEtValue.getText().toString().trim();
        if (TextUtils.isEmpty(value)) {
            return 0;
        }
        try {
            return Integer.valueOf(value);
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * Desc: 设置数量变化监听
     * <p>
     * Author: 廖培坤
     * Date: 2018-07-27
     *
     * @param listener
     */
    public void setOnCountChangedListener(OnCountChangedListener listener) {
        this.mOnCountChangedListener = listener;
    }

    /**
     * Desc: 数量变化监听
     * <p>
     * Author: 廖培坤
     * Date: 2018-07-27
     * Copyright: Copyright (c) 2013-2018
     * Company: @米冠网络
     * Update Comments:
     */
    public interface OnCountChangedListener {

        /**
         * Desc:数量变化
         * <p>
         * Author: 廖培坤
         * Date: 2018-07-27
         *
         * @param count
         */
        void onCountChange(int count);
    }

}
