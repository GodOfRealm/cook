package com.common.cook.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.common.cook.R;

/**
 * Created by SonnyJack on 2018/7/9 09:36.
 */
public class SearchEditText extends AppCompatEditText implements TextWatcher {

    private Drawable mRightDrawable;
    private int mRightDrawableWidth, mRightDrawableHeight;

    public SearchEditText(Context context) {
        super(context);
        init(null);
    }

    public SearchEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public SearchEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attributeSet) {
        setAttributeValue(attributeSet);

        addTextChangedListener(this);
    }

    private void setAttributeValue(AttributeSet attributeSet) {
        if (null == attributeSet) {
            return;
        }
        TypedArray typedArray = getContext().obtainStyledAttributes(attributeSet, R.styleable.search_layout);
        if (null == typedArray) {
            return;
        }
        //左边icon
        int leftIcon = typedArray.getResourceId(R.styleable.search_edit_text_left_icon, R.mipmap.common_ic_search);
        int leftIconWidth = typedArray.getDimensionPixelSize(R.styleable.search_edit_text_left_icon_width, 0);
        int leftIconHeight = typedArray.getDimensionPixelSize(R.styleable.search_edit_text_left_icon_height, 0);
        Drawable drawable = getResources().getDrawable(leftIcon);
        setLeftIcon(drawable, leftIconWidth, leftIconHeight);

        //右边icon
        int rightIcon = typedArray.getResourceId(R.styleable.search_edit_text_right_icon, R.mipmap.common_ic_search);
        mRightDrawableWidth = typedArray.getDimensionPixelSize(R.styleable.search_edit_text_right_icon_width, 0);
        mRightDrawableHeight = typedArray.getDimensionPixelSize(R.styleable.search_edit_text_right_icon_height, 0);
        mRightDrawable = getResources().getDrawable(rightIcon);
        showRightIcon(true);

        typedArray.recycle();
    }

    public void setLeftIcon(Drawable drawable) {
        setLeftIcon(drawable, 0, 0);
    }

    public void setLeftIcon(Drawable drawable, int iconWidth, int iconHeight) {
        if (null == drawable) {
            return;
        }
        if (iconWidth == 0) {
            iconWidth = drawable.getIntrinsicWidth();
        }
        if (iconHeight == 0) {
            iconHeight = drawable.getIntrinsicHeight();
        }
        drawable.setBounds(0, 0, iconWidth, iconHeight);
        setCompoundDrawables(drawable, getCompoundDrawables()[1], getCompoundDrawables()[2], getCompoundDrawables()[3]);
    }

    public void setRightIcon(Drawable drawable) {
        mRightDrawable = drawable;
        mRightDrawableWidth = 0;
        mRightDrawableHeight = 0;
        showRightIcon(true);
    }

    public void showRightIcon(boolean show) {
        Drawable rightDrawable = show ? mRightDrawable : null;
        if (null != rightDrawable) {
            if (mRightDrawableWidth == 0) {
                mRightDrawableWidth = rightDrawable.getIntrinsicWidth();
            }
            if (mRightDrawableHeight == 0) {
                mRightDrawableHeight = rightDrawable.getIntrinsicHeight();
            }
            rightDrawable.setBounds(0, 0, mRightDrawableWidth, mRightDrawableHeight);
        }
        setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], rightDrawable, getCompoundDrawables()[3]);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Drawable rightDrawable = getCompoundDrawables()[2];
        if (event.getAction() == MotionEvent.ACTION_UP && null != rightDrawable) {
            boolean touchable = event.getX() > (getWidth() - getPaddingRight() - rightDrawable.getIntrinsicWidth())
                    && (event.getX() < ((getWidth() - getPaddingRight())));
            if (touchable) {
                setText(null);
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        int length = null == s ? 0 : s.length();
        showRightIcon(length > 0);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
