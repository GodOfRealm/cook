package com.miguan.youmi.widget;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;


/**
 * Desc: ecyclerView的间距
 * <p>
 * Author: SonnyJack
 * Date: 2018-07-04
 * Copyright: Copyright (c) 2013-2018
 * Company: @米冠网络
 * Update Comments:
 */
public class RecyclerViewLinearLayoutSpace extends RecyclerView.ItemDecoration {

    private int mOrientation;

    private int mItemSize = 1;

    private Paint mPaint;

    private int mLeftPadding, mRightPadding, mTopPadding, mBottomPadding;

    //是否绘制底部分割线
    private boolean mDrawFooterDivider = false;

    public RecyclerViewLinearLayoutSpace(int orientation, int itemSize, int color) {
        this.mOrientation = orientation;
        this.mItemSize = itemSize;
        if (orientation != LinearLayoutManager.VERTICAL && orientation != LinearLayoutManager.HORIZONTAL) {
            throw new IllegalArgumentException("the orientation is error");
        }
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(color);
        mPaint.setStyle(Paint.Style.FILL);
    }

    public void setDrawFooterDivider(boolean drawFooterDivider) {
        this.mDrawFooterDivider = drawFooterDivider;
    }

    public void setPadding(int leftPadding, int rightPadding, int topPadding, int bottomPadding) {
        mLeftPadding = leftPadding;
        mRightPadding = rightPadding;
        mTopPadding = topPadding;
        mBottomPadding = bottomPadding;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (mOrientation == LinearLayoutManager.VERTICAL) {
            //画竖线
            drawVertical(c, parent);
        } else {
            //画横线
            drawHorizontal(c, parent);
        }
    }

    /**
     * 绘制竖线
     *
     * @param canvas
     * @param parent
     */
    private void drawVertical(Canvas canvas, RecyclerView parent) {
        final int childSize = parent.getChildCount();
        for (int i = 0; i < childSize; i++) {
            View view = parent.getChildAt(i);
            if (isLast(view, parent) && !mDrawFooterDivider) {
                continue;
            }
            float top = view.getTop() + mTopPadding;
            float bottom = view.getBottom() - mBottomPadding;
            float left = view.getRight();
            float right = left + mItemSize;
            canvas.drawRect(left, top, right, bottom, mPaint);
        }
    }

    /**
     * 绘制横线
     *
     * @param canvas
     * @param parent
     */
    private void drawHorizontal(Canvas canvas, RecyclerView parent) {
        final int childSize = parent.getChildCount();
        for (int i = 0; i < childSize; i++) {
            View view = parent.getChildAt(i);
            if (isLast(view, parent) && !mDrawFooterDivider) {
                continue;
            }

            float top = view.getBottom();
            float bottom = top + mItemSize;
            float left = view.getLeft() + mLeftPadding;
            float right = view.getRight() - mRightPadding;
            canvas.drawRect(left, top, right, bottom, mPaint);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (mOrientation == LinearLayoutManager.VERTICAL) {
            //画竖线
            if (isLast(view, parent) && !mDrawFooterDivider) {
                outRect.bottom = 0;
            } else {
                outRect.right = mItemSize;
            }
        } else {
            //画横线
            if (isLast(view, parent) && !mDrawFooterDivider) {
                outRect.bottom = 0;
            } else {
                outRect.bottom = mItemSize;
            }
        }
    }

    private boolean isLast(View view, RecyclerView parent) {
        int position = parent.getChildAdapterPosition(view);
        RecyclerView.Adapter adapter = parent.getAdapter();
        int count = parent.getAdapter().getItemCount();
        if (adapter instanceof com.miguan.youmi.core.base.BaseAdapter) {
            count = ((com.miguan.youmi.core.base.BaseAdapter) adapter).getDataCount();
        }
        return position >= count - 1;
    }
}
