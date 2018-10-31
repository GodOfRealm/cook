package com.miguan.youmi.core.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.miguan.youmi.core.R;

/**
 * Desc: 可以设置宽高比的ImageView
 * <p>
 * Author: 廖培坤
 * Date: 2018-07-16
 * Copyright: Copyright (c) 2013-2018
 * Company: @米冠网络
 * Update Comments:
 */
public class RatioImageView extends android.support.v7.widget.AppCompatImageView {
    public static final int MODE_AUTO_FIT = 0;
    public static final int MODE_FIT_WIDTH = 1;
    public static final int MODE_FIT_HEIGHT = 2;
    private float mRatio;
    private int mMode;

    public RatioImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RatioImageView, defStyle, 0);
        this.mRatio = a.getFloat(R.styleable.RatioImageView_ratio, 1.0F);
        this.mMode = a.getInt(R.styleable.RatioImageView_ratio_mode, 0);
        a.recycle();
    }

    public RatioImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RatioImageView(Context context) {
        super(context);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (this.mMode != MODE_FIT_WIDTH && (this.mMode != MODE_AUTO_FIT || MeasureSpec.AT_MOST != widthMode || MeasureSpec.EXACTLY != heightMode)) {
            if (this.mMode == MODE_FIT_HEIGHT || this.mMode == MODE_AUTO_FIT && MeasureSpec.AT_MOST == heightMode && MeasureSpec.EXACTLY == widthMode) {
                heightSize = (int) ((float) widthSize / this.mRatio);
                heightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.EXACTLY);
            }
        } else {
            widthSize = (int) ((float) heightSize * this.mRatio);
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(widthSize, MeasureSpec.EXACTLY);
        }

        this.setMeasuredDimension(widthSize, heightSize);
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    public void setRatioAndMode(int mode, float ratio) {
        this.mMode = mode;
        this.mRatio = ratio;
    }
}