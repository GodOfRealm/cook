package com.common.cook.app.base;

import android.support.annotation.Nullable;
import android.view.ViewGroup;

import com.jess.arms.mvp.IView;

import java.util.List;

/**
 * Desc: 有列表页面的BaseView
 * <p>
 * Author: QiuRonaC
 * Date: 2018-06-28
 * Copyright: Copyright (c) 2013-2018
 * Company: @米冠网络
 * Update Comments:
 *
 * @param <T> the type parameter
 */
public interface IBaseListView<T> extends IView {
    /**
     * Desc: 在请求列表数据前调用，可在这里设置一些参数后再请求数据
     * <p>
     * Author: QiuRonaC
     * Date: 2018-07-06
     *
     * @return true 自动请求列表数据，false 不请求列表数据，在需要的时候手动调用@{link #onRefresh()}
     * 加载数据
     */
    boolean beginBeforeRequest();

    /**
     * 逻辑开始处
     */
    void begin();

    /**
     * Desc: 刷新完成更新列表数据
     * <p>
     * Author: qiurongc
     * Date: 2018-06-28
     *
     * @param data    刷新的数据，当刷新失败的时候为空
     * @param success 是否刷新成功
     */
    void onRefreshComplete(@Nullable List<T> data, boolean success);

    /**
     * Desc: 加载更多完成
     * <p>
     * Author: qiurongc
     * Date: 2018-06-28
     *
     * @param data    加载分页的数据，当加载失败的时候为空
     * @param success 是否加载成功
     */
    void onLoadMoreComplete(List<T> data, boolean success);

    /**
     * Desc:是否可下拉刷新
     * <p>
     * Author: QiuRonaC
     * Date: 2018-07-03
     *
     * @param enable enable
     */
    void setRefreshEnable(boolean enable);

    /**
     * Desc:是否可加载更多
     * <p>
     * Author: QiuRonaC
     * Date: 2018-07-03
     *
     * @param enable enable
     */
    void setLoadMoreEnable(boolean enable);

    void setToolBarVisibility(boolean visibility);

    /**
     * Desc: 是否显示无网络的错误页面
     * <p>
     * Author: 廖培坤
     * Date: 2018-08-09
     *
     * @param showErrorPage
     */
    void setShowErrorPage(boolean showErrorPage);

    ViewGroup getRootView();

    int getCurPage();

}
