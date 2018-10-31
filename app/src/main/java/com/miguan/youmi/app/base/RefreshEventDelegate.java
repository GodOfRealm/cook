package com.miguan.youmi.app.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.miguan.youmi.R;
import com.miguan.youmi.core.base.ViewHolder;
import com.miguan.youmi.core.util.CollectionUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.List;

/**
 * Desc: ListActivity和ListFragment的列表相关操作的代理类
 * <p>
 * Author: QiuRonaC
 * Date: 2018-06-28
 * Copyright: Copyright (c) 2013-2018
 * Company: @米冠网络
 * Update Comments:
 *
 * @param <T> the type parameter
 */
class RefreshEventDelegate<T> {
    private final PageIndicator mPageIndicator;
    private IRefreshEvent<T> master;
    private View mRootView;
    private SmartRefreshLayout refreshLayout;
    private BaseQuickAdapter<T, ViewHolder> mAdapter;
    private RecyclerView mRecyclerView;
    private BaseQuickAdapter.RequestLoadMoreListener mRequestLoadMoreListener = new BaseQuickAdapter.RequestLoadMoreListener() {
        @Override
        public void onLoadMoreRequested() {
            master.fetchData(mPageIndicator.getCurPage() + 1);
        }
    };

    private boolean mShowError = true;
    private EmptyView mEmptyView;
    private EmptyView mNetworkView;

    RefreshEventDelegate(IRefreshEvent<T> refresh) {
        master = refresh;
        mPageIndicator = new PageIndicator();
    }

    int getLayoutID() {
        return R.layout.base_activity_recyclerview;
    }

    void init(View rootView) {
        mRootView = rootView;
        refreshLayout = mRootView.findViewById(R.id.refreshLayout);
        refreshLayout.setEnableLoadMore(false);
        refreshLayout.setOnRefreshListener(rl ->
                master.onRefresh()
        );

        if (refreshLayout.getParent() instanceof LinearLayout) {
            ((LinearLayout.LayoutParams) refreshLayout.getLayoutParams()).weight = 1f;
        }

        mRecyclerView = rootView.findViewById(R.id.base_recycler_view);

        RecyclerView.LayoutManager layoutManager = master.generateLayoutManager();
        if (layoutManager != null) {
            mRecyclerView.setLayoutManager(layoutManager);
        }
        mRecyclerView.setAdapter(mAdapter = master.generateAdapter());
        mAdapter.setLoadMoreView(master.createLoadMoreView());
        mAdapter.setEnableLoadMore(true);
        mAdapter.setOnLoadMoreListener(mRequestLoadMoreListener, mRecyclerView);
        mAdapter.setHeaderAndEmpty(true);
    }

    void onRefreshComplete(List<T> data, boolean success) {
        if (success) {
            //noinspection unchecked
            mPageIndicator.firstPage();
            if (CollectionUtil.isEmpty(data)) {
                setEmptyView();
            }
            mAdapter.setNewData(data);
        } else if (mShowError) {
            setNotNetworkView();
        }
        refreshLayout.finishRefresh(success);
    }

    void onLoadMoreComplete(List<T> data, boolean success) {
        if (success) {
            if (CollectionUtil.isEmpty(data)) {
                mAdapter.loadMoreEnd(!master.showLoadMoreEnd());
            } else {
                //noinspection unchecked
                mPageIndicator.nextPage();
                mAdapter.addData(data);
                mAdapter.loadMoreComplete();
            }
        } else {
            mAdapter.loadMoreFail();
        }
    }

    /**
     * Desc: 设置空页面
     * <p>
     * Author: 廖培坤
     * Date: 2018-08-06
     */
    private void setEmptyView() {
        if (mEmptyView == null) {
            mEmptyView = new EmptyView(mRootView.getContext());
            // 默认实现重试刷新操作，需要更改在setupEmptyView方法中重新设置即可
            mEmptyView.setOnRetryListener(master::onRefresh);
            master.setupEmptyView(mEmptyView);
        }
        mAdapter.setEmptyView(mEmptyView);
    }

    /**
     * Desc: 无网络显示页面
     * <p>
     * Author: 廖培坤
     * Date: 2018-08-06
     */
    private void setNotNetworkView() {
        if (mNetworkView == null) {
            mNetworkView = new EmptyView(mRootView.getContext());
            mNetworkView.setEmptyImageResource(R.mipmap.list_error_not_network);
            mNetworkView.setEmptyText("网络开小差了~请刷新重试");
            mNetworkView.setRetryText("刷新");
            mNetworkView.setLayoutBackgroundColor(R.color.white);
            mNetworkView.setOnRetryListener(master::onRefresh);
        }
        mAdapter.setEmptyView(mNetworkView);
    }

    RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    BaseQuickAdapter<T, ViewHolder> getAdapter() {
        return mAdapter;
    }

    /**
     * Desc:是否允许下拉刷新
     * <p>
     * Author: QiuRonaC
     * Date: 2018-07-03
     *
     * @param enable enable
     */
    void setRefreshEnable(boolean enable) {
        refreshLayout.setEnableRefresh(enable);
    }

    /**
     * Desc: 是否允许加载更多
     * <p>
     * Author: QiuRonaC
     * Date: 2018-07-03
     *
     * @param enable enable
     */
    void setLoadMoreEnable(boolean enable) {
        mAdapter.setEnableLoadMore(enable);
        mAdapter.setOnLoadMoreListener(enable ? mRequestLoadMoreListener : null, mRecyclerView);
    }

    /**
     * Desc: 设置ToolBar的显示状态
     * <p>
     * Author: QiuRonaC
     * Date: 2018-07-05
     *
     * @param visibility 是否显示
     */
    public void setToolBarVisibility(boolean visibility) {
        getPageRootView()
                .findViewById(R.id.toolbar)
                .setVisibility(visibility ? View.VISIBLE : View.GONE);
    }

    /**
     * Desc: 是否显示无网络的错误页面
     * <p>
     * Author: 廖培坤
     * Date: 2018-08-09
     *
     * @param showError
     */
    public void setShowErrorPage(boolean showError) {
        this.mShowError = showError;
        if (mNetworkView != null) {
            mNetworkView.setVisibility(View.GONE);
        }
    }

    /**
     * Desc: 获取页面的跟布局
     * <p>
     * Author: QiuRonaC
     * Date: 2018-07-05
     *
     * @return view group
     */
    public ViewGroup getPageRootView() {
        return ((ViewGroup) (mRecyclerView.getParent()).getParent());
    }

    /**
     * Desc: 获取当前页码
     * <p>
     * Author: 廖培坤
     * Date: 2018-07-31
     *
     * @return int
     */
    public int getCurPage() {
        return mPageIndicator.getCurPage();
    }

}
