package com.common.cook.app.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.common.cook.app.BaseActivity;
import com.common.cook.core.base.ViewHolder;

import java.util.List;

/**
 * Desc: 统一的RecyclerViewActivity
 * <p>
 * Author: QiuRonaC
 * Date: 2018-06-27
 * Copyright: Copyright (c) 2013-2018
 * Company: @米冠网络
 * Update Comments:
 *
 * @param <T> 列表数据类型
 * @param <P> the type parameter
 */
public abstract class BaseListActivity<T, P extends BaseListPresenter> extends BaseActivity<P>
        implements IBaseListView<T>, IRefreshEvent<T> {

    /**
     *
     */
    protected P mPresenter;
    private RefreshEventDelegate<T> refreshDelegate = new RefreshEventDelegate<>(this);
    /**
     *
     */
    protected RecyclerView mRecyclerView;
    /**
     *
     */
    protected BaseQuickAdapter<T, ViewHolder> mAdapter;

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return refreshDelegate.getLayoutID();
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        // NonNull
        mPresenter = super.mPresenter;
        boolean autoLoad = beginBeforeRequest();
        refreshDelegate.init(getWindow().getDecorView());
        mRecyclerView = refreshDelegate.getRecyclerView();
        mAdapter = refreshDelegate.getAdapter();
        if (autoLoad) {
            // 开始自动加载第一页数据
            onRefresh();
        }
        begin();
    }

    /**
     * Desc: 在请求列表数据前调用，可在这里设置一些参数后再请求数据
     * <p>
     * Author: QiuRonaC
     * Date: 2018-07-06
     *
     * @return true 自动请求列表数据，false 不请求列表数据，在需要的时候手动调用@{link #onRefresh()}
     * 加载数据
     */
    @Override
    public boolean beginBeforeRequest() {
        return true;
    }

    @Override
    public abstract void begin();

    @Override
    public void onRefresh() {
        fetchData(PageIndicator.START);
    }

    @Override
    public void fetchData(int page) {
        mPresenter.fetchData(page);
    }

    @Override
    public LoadMoreView createLoadMoreView() {
        return new PickLoadMoreView();
    }

    /**
     * Desc: 设置空页面的内容，文本，图片，按钮等
     * <p>
     * Author: QiuRonaC
     * Date: 2018-06-28
     *
     * @param emptyView 空页面EmptyView
     */
    @Override
    public abstract void setupEmptyView(EmptyView emptyView);

    /**
     * Desc: 如果列表样式不是LinearLayoutManager，重写返回其他layoutManager
     * <p>
     * Author: QiuRonaC
     * Date: 2018-06-27
     *
     * @return recycler view . layout manager
     */
    @Override
    public RecyclerView.LayoutManager generateLayoutManager() {
        return null;
    }

    @Override
    public void onLoadMoreComplete(List<T> data, boolean success) {
        refreshDelegate.onLoadMoreComplete(data, success);
    }

    @Override
    public void onRefreshComplete(@Nullable List<T> data, boolean success) {
        refreshDelegate.onRefreshComplete(data, success);
    }

    @Override
    public void setShowErrorPage(boolean showErrorPage) {
        refreshDelegate.setShowErrorPage(showErrorPage);
    }

    @Override
    public void setRefreshEnable(boolean enable) {
        refreshDelegate.setRefreshEnable(enable);
    }

    @Override
    public void setLoadMoreEnable(boolean enable) {
        refreshDelegate.setLoadMoreEnable(enable);
    }

    @Override
    public void setToolBarVisibility(boolean visibility) {
        refreshDelegate.setToolBarVisibility(visibility);
    }

    /**
     * Desc:获取ListFragment的页面根布局
     * <p>
     * Author: QiuRonaC
     * Date: 2018-07-05
     *
     * @return view group
     */
    @Override
    public ViewGroup getRootView() {
        return refreshDelegate.getPageRootView();
    }

    /**
     * Desc: 获取当前页码
     * <p>
     * Author: 廖培坤
     * Date: 2018-07-31
     *
     * @return int
     */
    @Override
    public int getCurPage() {
        return refreshDelegate.getCurPage();
    }

    @Override
    public boolean showLoadMoreEnd() {
        return true;
    }
}
