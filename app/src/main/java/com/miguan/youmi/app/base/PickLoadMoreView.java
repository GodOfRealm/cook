package com.miguan.youmi.app.base;

import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.miguan.youmi.R;

/**
 * Desc:加载更多
 * <p>
 * Author: QiuRonaC
 * Date: 2018-08-13
 * Copyright: Copyright (c) 2013-2018
 * Company: @米冠网络
 * Update Comments:
 */
public class PickLoadMoreView extends LoadMoreView {
    @Override
    public int getLayoutId() {
        return R.layout.common_recycler_loadmore;
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
