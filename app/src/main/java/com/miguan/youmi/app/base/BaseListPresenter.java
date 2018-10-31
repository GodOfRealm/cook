package com.miguan.youmi.app.base;

import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.mvp.IModel;
import com.jess.arms.utils.RxLifecycleUtils;
import com.miguan.youmi.app.DefaultTransform;
import com.miguan.youmi.app.ServicesObserver;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import static com.miguan.youmi.app.base.PageIndicator.START;

/**
 * Desc: BaseList页面的Presenter，继承BaseListActivity或BaseListFragment的子类的Presenter需要继承此Presenter
 * <p>
 * Author: QiuRonaC
 * Date: 2018-06-28
 * Copyright: Copyright (c) 2013-2018
 * Company: @米冠网络
 * Update Comments:
 *
 * @param <M> the type parameter
 * @param <V> the type parameter
 */
public abstract class BaseListPresenter<T, M extends IModel, V extends IBaseListView<T>> extends BasePresenter<M, V> {
    @Inject
    protected RxErrorHandler mErrorHandler;

    public BaseListPresenter(M model, V rootView) {
        super(model, rootView);
    }

    /**
     * Desc: 请求列表数据
     * <p>
     * Author: QiuRonaC
     * Date: 2018-06-28
     *
     * @param page 要请求的页码
     */
    public void fetchData(int page) {
        request(page, provideRequestObservable(page));
    }

    /**
     * Desc: 提供列表刷新的请求Observable
     * 如果接口返回的数据并不是Array，并且在model请求完成后还要交给Presenter对数据做进一步处理，
     * 可以在model中定义具体的方法，返回值也不必是List，
     * 在处理完成后provideRequestObservable的返回值是List就可以<p>
     * <code>
     * Observable<'Object> obj = model.getData()
     * <p>obj.doProfileNext {
     *      //...做数据进一步处理
     * }.flatMap {
     *      Object -> List
     * }
     * </code>
     * <p>
     * Author: QiuRonaC
     * Date: 2018-06-29
     *
     * @param page 页码
     * @return observable
     */
    protected abstract Observable<List<T>> provideRequestObservable(int page);

    /**
     * Desc: 请求列表数据
     * <p>如果需要使用另外的请求方法，重写此方法并在请求完成后调用{@link #onLoadDataSuccess(int, List)}
     * 和{@link #onLoadDataError(int)}即可
     * <p>
     * Author: QiuRonaC
     * Date: 2018-06-28
     *
     * @param page       请求的页码
     * @param observable 请求Observable
     */
    protected void request(int page, Observable<List<T>> observable) {
        if (null == observable) {
            return;//测试需要  目前null先不处理
            //throw new NullPointerException("请求Observable provideRequestObservable(int) 不能返回空!! ");
        }
        observable.compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .compose(new DefaultTransform<>())
                .subscribe(new ServicesObserver<List<T>>() {
                    @Override
                    public void onNext(List<T> data) {
                        onLoadDataSuccess(page, data);
                    }

                    @Override
                    public void onError(int code, String msg) {
                        onLoadDataError(page);
                    }
                });
    }

    /**
     * Desc:请求数据失败，如果有重写的必要记得调用super
     * <p>
     * Author: QiuRonaC
     * Date: 2018-06-28
     *
     * @param page 当前请求的页码
     */
    protected void onLoadDataError(int page) {
        loadComplete(page, null, false);
    }

    /**
     * Desc: 请求列表数据成功，允许子类在请求成功后操作数据再设置到adapter，
     * note： 重写必须要调用super
     * <p>
     * Author: QiuRonaC
     * Date: 2018-06-28
     *
     * @param page 当前请求的页码
     * @param data 请求到的数据
     */
    protected void onLoadDataSuccess(int page, List<T> data) {
        loadComplete(page, data, true);
    }

    private void loadComplete(int page, List<T> data, boolean success) {
        if (page == START) {
            mRootView.onRefreshComplete(data, success);
        } else {
            mRootView.onLoadMoreComplete(data, success);
        }
    }
}
