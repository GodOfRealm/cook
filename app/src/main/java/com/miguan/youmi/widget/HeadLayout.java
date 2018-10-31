package com.miguan.youmi.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.miguan.youmi.R;
import com.miguan.youmi.core.widget.radius.RadiusImageView;


/**
 * Desc: 头像控件
 * <p>
 * Author: SonnyJack
 * Date: 2018-09-13
 * Copyright: Copyright (c) 2013-2018
 * Company: @米冠网络
 * Update Comments:
 */
public class HeadLayout extends LinearLayout {

    //该值改变后，需要改变base_iv_head控件的layout_constraintHeight_percent、layout_constraintWidth_percent的值[1/mScale]
    private final float mScale = 1.25f;

    private View mChild;

    private ImageView mIvHead, mIvDress;

    private int mClipParentNum = 1;

    public HeadLayout(Context context) {
        super(context);
        init(null);
    }

    public HeadLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public HeadLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        setOrientation(LinearLayout.VERTICAL);
        setGravity(Gravity.CENTER);
        mChild = LayoutInflater.from(getContext()).inflate(R.layout.base_layout_head, null);
        int[] systemAttrs = {android.R.attr.layout_width, android.R.attr.layout_height};
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, systemAttrs);
        int width = typedArray.getDimensionPixelOffset(0, ViewGroup.LayoutParams.MATCH_PARENT);
        int height = typedArray.getDimensionPixelOffset(1, ViewGroup.LayoutParams.MATCH_PARENT);
        typedArray.recycle();
        typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.HeadLayout);
        mClipParentNum = typedArray.getInt(R.styleable.HeadLayout_clip_parent_num, 1);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams((int) (mScale * width), (int) (mScale * height));
        addView(mChild, layoutParams);

        mIvHead = findViewById(R.id.base_iv_head);
        mIvDress = findViewById(R.id.base_iv_dress);
        //变宽的高度
        if (typedArray.hasValue(R.styleable.HeadLayout_border_width)) {
            float borderWidth = typedArray.getDimensionPixelOffset(R.styleable.HeadLayout_border_width, 0);
            setHeadBorderWidth(borderWidth);
        }
        //变宽的颜色
        if (typedArray.hasValue(R.styleable.HeadLayout_border_color)) {
            int borderColor = typedArray.getColor(R.styleable.HeadLayout_border_color, Color.TRANSPARENT);
            setHeadBorderColor(borderColor);
        }
        typedArray.recycle();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        initParent();
    }

    private void initParent() {
        int temp = 1;
        ViewParent parent = getParent();
        while (null != parent && temp <= mClipParentNum) {
            if (parent instanceof ViewGroup) {
                ((ViewGroup) parent).setClipChildren(false);
                ((ViewGroup) parent).setClipToPadding(false);
            }
            parent = parent.getParent();
            temp++;
        }
    }

    public ImageView getHeadView() {
        return mIvHead;
    }

    public ImageView getDressView() {
        return mIvDress;
    }

    public void setDressVisible(boolean visible) {
        mIvDress.setVisibility(visible ? VISIBLE : GONE);
    }

    public void setHeadBorderWidth(float borderWidth) {
        if (mIvHead instanceof RadiusImageView) {
            RadiusImageView radiusImageView = (RadiusImageView) mIvHead;
            radiusImageView.setBorderWidth(borderWidth);
        }
    }

    public void setHeadBorderColor(@ColorInt int color) {
        if (mIvHead instanceof RadiusImageView) {
            RadiusImageView radiusImageView = (RadiusImageView) mIvHead;
            radiusImageView.setBorderColor(color);
        }
    }
}
