package com.miguan.youmi.widget;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;


/**
 * Desc: 首页->聊天室、技能等RecyclerView的间距
 * <p>
 * Author: SonnyJack
 * Date: 2018-07-04
 * Copyright: Copyright (c) 2013-2018
 * Company: @米冠网络
 * Update Comments:
 */
public class RecyclerViewGridSpace extends RecyclerView.ItemDecoration {

    //https://blog.csdn.net/futianyi1994/article/details/79057173

    private boolean mIncludeEdge = false;
    private int mVertical, mHorizontal, mColumn;

    /**
     * Desc:
     * <p>
     * Author: SonnyJack
     * Date: 2018-07-04
     *
     * @param vertical
     * @param horizontal
     */
    public RecyclerViewGridSpace(int vertical, int horizontal, int column) {
        mVertical = vertical;
        mHorizontal = horizontal;
        mColumn = column;
    }

    public void setIncludeEdge(boolean includeEdge) {
        mIncludeEdge = includeEdge;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        //super.getItemOffsets(outRect, view, parent, state);
        int position = parent.getChildLayoutPosition(view);
        int column = position % mColumn;
        if (mIncludeEdge) {
            outRect.left = mHorizontal - column * mHorizontal / mColumn;
            outRect.right = (column + 1) * mHorizontal / mColumn;
        } else {
            outRect.left = column * mHorizontal / mColumn;
            outRect.right = mHorizontal - (column + 1) * mHorizontal / mColumn;
        }
        outRect.bottom = mVertical;
    }
}
