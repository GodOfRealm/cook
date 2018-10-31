package com.miguan.youmi.app.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.miguan.youmi.app.BaseFragment;
import com.miguan.youmi.core.base.ViewHolder;

import java.util.List;

/**
 * Desc: 有列表的Fragment
 * <p>
 * Author: QiuRonaC
 * Date: 2018-06-28
 * Copyright: Copyright (c) 2013-2018
 * Company: @米冠网络
 * Update Comments:
 *
 * @param <T> the type parameter
 * @param <P> the type parameter
 */
public abstract class BaseListFragment<T, P extends BaseListPresenter> extends BaseFragment<P>
        implements IBaseListView<T>, IRefreshEvent<T> {

    protected RecyclerView mRecyclerView;
    protected BaseQuickAdapter<T, ViewHolder> mAdapter;
    /**
     * 列表相关的代理
     */
    private RefreshEventDelegate<T> refreshEventDelegate = new RefreshEventDelegate<>(this);

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(refreshEventDelegate.getLayoutID(), container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        boolean autoLoad = beginBeforeRequest();
        refreshEventDelegate.init(mView);
        mRecyclerView = refreshEventDelegate.getRecyclerView();
        mAdapter = refreshEventDelegate.getAdapter();
        if (autoLoad) {
            // 开始自动加载第一页数据
            onRefresh();
        }
        begin();
    }

    @Override
    public boolean beginBeforeRequest() {
        return true;
    }

    @Override
    public abstract void begin();

    @Override
    public void setToolBarVisibility(boolean visibility) {
        refreshEventDelegate.setToolBarVisibility(visibility);
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
        return refreshEventDelegate.getPageRootView();
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
        return refreshEventDelegate.getCurPage();
    }

    @Override
    public void onLoadMoreComplete(List<T> data, boolean success) {
        refreshEventDelegate.onLoadMoreComplete(data, success);
    }

    @Override
    public void onRefreshComplete(@Nullable List<T> data, boolean success) {
        refreshEventDelegate.onRefreshComplete(data, success);
    }

    @Override
    public void setRefreshEnable(boolean enable) {
        refreshEventDelegate.setRefreshEnable(enable);
    }

    @Override
    public void setLoadMoreEnable(boolean enable) {
        refreshEventDelegate.setLoadMoreEnable(enable);
    }

    @Override
    public void setShowErrorPage(boolean showErrorPage) {
        refreshEventDelegate.setShowErrorPage(showErrorPage);
    }

    @Override
    public void onRefresh() {
        fetchData(PageIndicator.START);
    }

    @Override
    public void fetchData(int page) {
        assert mPresenter != null;
        mPresenter.fetchData(page);
    }

    @Override
    public LoadMoreView createLoadMoreView() {
        return new PickLoadMoreView();
    }

    @Override
    public RecyclerView.LayoutManager generateLayoutManager() {
        return null;
    }

    @Override
    public boolean showLoadMoreEnd() {
        return true;
    }
}
