package com.common.cook.widget;

import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.common.cook.R;

/**
 * Created by SonnyJack on 2018/9/13 14:33.
 */
public class EmptyLoadMoreView extends LoadMoreView {
    @Override
    public int getLayoutId() {
        return R.layout.common_recycler_loadmore_empty;
    }

    @Override
    protected int getLoadingViewId() {
        return com.chad.library.R.id.load_more_loading_view;
    }

    @Override
    protected int getLoadFailViewId() {
        return R.id.load_more_load_fail_view;
    }

    @Override
    protected int getLoadEndViewId() {
        return R.id.load_more_load_end_view;
    }
}
