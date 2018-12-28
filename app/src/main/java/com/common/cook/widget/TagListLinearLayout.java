package com.common.cook.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.SizeUtils;
import com.common.cook.R;
import com.common.cook.core.widget.radius.RadiusTextView;
import com.common.cook.core.widget.radius.RadiusViewDelegate;

import java.util.ArrayList;

import static android.util.TypedValue.COMPLEX_UNIT_PX;

/**
 * Desc: 水平排列标签样式
 * <p>
 * Author: 廖培坤
 * Date: 2018-08-07
 * Copyright: Copyright (c) 2013-2018
 * Company: @米冠网络
 * Update Comments:
 *
 * @param <T> the type parameter
 */
public class TagListLinearLayout<T> extends LinearLayout {

    public ArrayList<T> mTagList = new ArrayList<>();

    private int mBackgroundColor; // 背景颜色
    private float mSpace; // Tag之间距离
    private float mTagWidth;
    private float mTagHeight;
    private int mTagPaddingLeft;
    private int mTagPaddingRight;
    private int mTagPaddingTop;
    private int mTagPaddingBottom;
    private int mTextColor; // 文字颜色
    private float mTextSize; // 字体大小
    private int mRadius; // 圆角

    public TagListLinearLayout(Context context) {
        super(context);
    }

    /**
     * Desc:
     * <p>
     * Author: 廖培坤
     * Date: 2018-08-07
     *
     * @param context
     * @param attrs
     */
    public TagListLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    /**
     * Desc:
     * <p>
     * Author: 廖培坤
     * Date: 2018-08-07
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public TagListLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        this.setHorizontalGravity(HORIZONTAL);

        int defaultTextColor = context.getResources().getColor(R.color.color_78c4de);
        int defaultBackground = context.getResources().getColor(R.color.color_tag_e7f6fb);
        float defaultTextSize = context.getResources().getDimension(R.dimen.text_size_small);
        float defaultMarginLeft = context.getResources().getDimension(R.dimen.label_default_margin_left);
        float defaultPadding = context.getResources().getDimension(R.dimen.spacing_tiny);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TagListLinearLayout);
        mTagWidth = ta.getDimension(R.styleable.TagListLinearLayout_tlv_tag_width, 0);
        mTagHeight = ta.getDimension(R.styleable.TagListLinearLayout_tlv_tag_height, 0);
        mTagPaddingLeft = (int) ta.getDimension(R.styleable.TagListLinearLayout_tlv_tag_padding_left, defaultPadding);
        mTagPaddingRight = (int) ta.getDimension(R.styleable.TagListLinearLayout_tlv_tag_padding_right, defaultPadding);
        mTagPaddingTop = (int) ta.getDimension(R.styleable.TagListLinearLayout_tlv_tag_padding_top, 0);
        mTagPaddingBottom = (int) ta.getDimension(R.styleable.TagListLinearLayout_tlv_tag_padding_bottom, 0);
        mTextColor = ta.getColor(R.styleable.TagListLinearLayout_tlv_text_color, defaultTextColor);
        mTextSize = ta.getDimension(R.styleable.TagListLinearLayout_tlv_text_size, defaultTextSize);
        mBackgroundColor = ta.getColor(R.styleable.TagListLinearLayout_tlv_background_color, defaultBackground);
        mSpace = ta.getDimension(R.styleable.TagListLinearLayout_tlv_space, defaultMarginLeft);
        mRadius = (int) ta.getDimension(R.styleable.TagListLinearLayout_tlv_radius, SizeUtils.dp2px(5));
        ta.recycle();
    }

    /**
     * Desc: 设置数据
     * <p>
     * Author: 廖培坤
     * Date: 2018-08-07
     *
     * @param list
     * @param callback
     */
    public void setData(ArrayList<T> list, TagTextCallback<T> callback) {
        mTagList.clear();
        mTagList.addAll(list);
        this.removeAllViews();
        for (int i = 0; i < list.size(); i++) {
            addView(createTextView(list, i, callback));
        }
    }

    /**
     * Desc: 创建TextView
     * <p>
     * Author: 廖培坤
     * Date: 2018-08-07
     *
     * @param index
     * @param callback
     * @return radius text view
     */
    private RadiusTextView createTextView(ArrayList<T> list, int index, TagTextCallback<T> callback) {
        RadiusTextView tvLabel = new RadiusTextView(getContext());
        LayoutParams params = new LayoutParams(mTagWidth > 0 ? (int) mTagWidth : -2, mTagHeight > 0 ? (int) mTagHeight : -1);
        tvLabel.setLayoutParams(params);
        if (index != 0) {
            params.setMargins((int) mSpace, 0, 0, 0);
        }
        tvLabel.setPadding(mTagPaddingLeft, mTagPaddingTop, mTagPaddingRight, mTagPaddingBottom);
        tvLabel.setGravity(Gravity.CENTER);

        tvLabel.setTextSize(COMPLEX_UNIT_PX, mTextSize);
        RadiusViewDelegate delegate = tvLabel.getDelegate();
        delegate.setTextColor(mTextColor);
        delegate.setBackgroundColor(mBackgroundColor);
        delegate.setRadius(mRadius, false);

        if (callback != null) {
            tvLabel.setText(callback.getText(list.get(index)));
        }

        return tvLabel;
    }

    /**
     * Desc:
     * <p>
     * Author: 廖培坤
     * Date: 2018-08-07
     * Copyright: Copyright (c) 2013-2018
     * Company: @米冠网络
     * Update Comments:
     *
     * @param <T> the type parameter
     */
    public interface TagTextCallback<T> {
        /**
         * Desc:
         * <p>
         * Author: 廖培坤
         * Date: 2018-08-07
         *
         * @param item
         * @return string
         */
        String getText(T item);
    }

}
