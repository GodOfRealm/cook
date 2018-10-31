package com.miguan.youmi.widget.sticky;

import android.content.Context;
import android.graphics.Canvas;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewParent;

import com.blankj.utilcode.util.SizeUtils;
import com.miguan.youmi.R;

import java.lang.reflect.Field;

/**
 * Created by afon on 2017/1/3.
 */

public class CustomCollapsingToolbarLayout extends CollapsingToolbarLayout {
    public static final String TAG = "CustomCollapsingLayout";

    private int scrimAlpha = 0;

    private AppBarLayout.OnOffsetChangedListener onOffsetChangedListener;

    private OnScrolledListener mOnScrolledListener;
    private View mTitleLayout;

    private void drawTitleLayout(Canvas canvas) {
        if (mTitleLayout != null) {
            if (scrimAlpha > 10) { // 为什么不设置为零 因为有的手机将此滑动到顶部的时候 透明度并不一定为零 经过测试全在10一下（也可能有的手机不在），
                mTitleLayout.getBackground().mutate().setAlpha(scrimAlpha);
                mTitleView.setAlpha(scrimAlpha);
                if (mOnScrolledListener != null) {
                    mOnScrolledListener.onScrolled(true, scrimAlpha);
                }
            } else {
                mTitleLayout.getBackground().mutate().setAlpha(0);
                mTitleView.setAlpha(0);
                if (mOnScrolledListener != null) {
                    mOnScrolledListener.onScrolled(false, 0);
                }
            }
        }
    }
    private View mTitleView;

    public CustomCollapsingToolbarLayout(Context context) {
        this(context, null);
    }

    public CustomCollapsingToolbarLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomCollapsingToolbarLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initUI(context);
    }

    private void initUI(Context context) {
        setStatusBarScrim(null);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        final ViewParent parent = getParent();
        if (parent instanceof AppBarLayout) {
            if (onOffsetChangedListener == null) {
                onOffsetChangedListener = new OffsetUpdateListener();
            }
            ((AppBarLayout) parent).addOnOffsetChangedListener(onOffsetChangedListener);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        final ViewParent parent = getParent();
        if (onOffsetChangedListener != null && parent instanceof AppBarLayout) {
            ((AppBarLayout) parent).removeOnOffsetChangedListener(onOffsetChangedListener);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        try {
            Field scrimAlpha = CollapsingToolbarLayout.class.getDeclaredField("mScrimAlpha");
            scrimAlpha.setAccessible(true);
            this.scrimAlpha = scrimAlpha.getInt(this);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        drawTitleLayout(canvas);
    }

    public void setTitleLayout(View titleLayout) {
        this.mTitleLayout = titleLayout;
        this.mTitleView = titleLayout.findViewById(R.id.base_toolbar_title);
    }

    public void setOnScrolledListener(OnScrolledListener listener) {
        mOnScrolledListener = listener;
    }

    public interface OnScrolledListener {
        void onScrolled(boolean isShow, float alpha);
    }

    private class OffsetUpdateListener implements AppBarLayout.OnOffsetChangedListener {

        @Override
        public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
            setScrimsShown(getHeight() + verticalOffset < SizeUtils.dp2px(300));
            ViewCompat.postInvalidateOnAnimation(CustomCollapsingToolbarLayout.this);
        }

    }

}
