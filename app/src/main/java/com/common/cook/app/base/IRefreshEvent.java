package com.common.cook.app.base;

import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.common.cook.core.base.ViewHolder;

/**
 * Desc:
 * <p>
 * Author: QiuRonaC
 * Date: 2018-06-28
 * Copyright: Copyright (c) 2013-2018
 * Company: @米冠网络
 * Update Comments:
 */
public interface IRefreshEvent<T> {

    /**
     * Desc: 刷新列表
     * <p>
     * Author: QiuRonaC
     * Date: 2018-06-28
     */
    void onRefresh();

    /**
     * Desc: 设置空页面的内容，文本，图片，按钮等
     * <p>
     * Author: QiuRonaC
     * Date: 2018-06-28
     *
     * @param emptyView 空页面EmptyView
     */
    void setupEmptyView(EmptyView emptyView);

    /**
     * Desc: 如果列表样式不是LinearLayoutManager，重写返回其他layoutManager
     * <p>
     * Author: QiuRonaC
     * Date: 2018-06-27
     *
     * @return recycler view . layout manager
     */
    RecyclerView.LayoutManager generateLayoutManager();

    /**
     * Desc: 创建RecyclerView的Adapter
     * <p>
     * Author: QiuRonaC
     * Date: 2018-06-27
     *
     * @return base adapter
     */
    BaseQuickAdapter<T, ViewHolder> generateAdapter();

    LoadMoreView createLoadMoreView();

    void fetchData(int page);

    //上拉没有数据时，是否显示：底部提示View【没有更多数据了...】
    boolean showLoadMoreEnd();
}
