package com.miguan.youmi.app;

import android.util.Log;

import com.blankj.utilcode.util.LogUtils;
import com.jess.arms.mvp.IView;
import com.miguan.youmi.app.http.HttpResponseCode;
import com.miguan.youmi.app.http.ServicesException;
import com.miguan.youmi.core.util.PickToast;
import com.miguan.youmi.util.EventBusUtil;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

import static com.miguan.youmi.app.constant.EventBusTags.EVENT_RE_LOGIN;
import static com.miguan.youmi.app.http.HttpResponseCode.ERROR_UNKNOWN;

/**
 * Desc: 所有接口都应该用该Observer订阅
 * <p>
 * Author: 廖培坤
 * Date: 2018-07-04
 * Copyright: Copyright (c) 2013-2018
 * Company: @米冠网络
 * Update Comments:
 *
 * @param <T> the type parameter
 */
public abstract class ServicesObserver<T> implements Observer<T> {

    /**
     * 没有数据时
     */
    public static final int EMPTY_DATA = 4009;

    private IView mRootView;
    //错误时，是否toast错误信息，默认为true
    private boolean mShowErrorToast = true;

    public ServicesObserver() {
    }

    public ServicesObserver(IView needShowLoading) {
        mRootView = needShowLoading;
    }

    public ServicesObserver(IView needShowLoading, boolean showErrorToast) {
        this(showErrorToast);
        mRootView = needShowLoading;
    }

    public ServicesObserver(boolean showErrorToast) {
        mShowErrorToast = showErrorToast;
    }

    public void setRootView(IView needShowLoading) {
        mRootView = needShowLoading;
    }

    @Override
    public void onSubscribe(Disposable d) {
        if (mRootView != null) {
            mRootView.showLoading();
        }
    }

    @Override
    public void onError(Throwable t) {
        LogUtils.d(Log.getStackTraceString(t));
        if (t instanceof UnknownHostException || t instanceof ConnectException) {
            onServicesException(new ServicesException(HttpResponseCode.ERROR_UNKNOWN_HOST, "网络不可用!"));
        } else if (t instanceof SocketTimeoutException) {
            onServicesException(new ServicesException(HttpResponseCode.ERROR_SOCKET_TIMEOUT, "请求网络超时!"));
        } else if (t instanceof ServicesException) {
            onServicesException((ServicesException) t);
        } else if (t instanceof HttpException) {
            onHttpException((HttpException) t);
        } else {
            onServicesException(new ServicesException(ERROR_UNKNOWN, "未知错误"));
        }
        if (mRootView != null) {
            mRootView.hideLoading();
        }
    }

    /**
     * Desc: 处理HttpException
     * <p>
     * Author: 廖培坤
     * Date: 2018-07-04
     *
     * @param httpException
     */
    private void onHttpException(HttpException httpException) {
        String msg;
        if (httpException.code() == 504) {
            msg = "网络连接不可用";
        } else if (httpException.code() == 500) {
            msg = "服务器发生错误";
        } else if (httpException.code() == 401) {
            msg = "登录过期，请重新登录";
        } else if (httpException.code() == 404) {
            msg = "请求地址不存在";
        } else if (httpException.code() == 403) {
            msg = "请求被服务器拒绝";
        } else if (httpException.code() == 307) {
            msg = "请求被重定向到其他页面";
        } else {
            msg = httpException.message();
        }
        onServicesException(new ServicesException(httpException.code(), msg));
    }

    /**
     * Desc: 处理ServicesException
     * <p>
     * Author: 廖培坤
     * Date: 2018-07-04
     *
     * @param e
     */
    private void onServicesException(ServicesException e) {
        if (mShowErrorToast && e.needShowToast()) {
            PickToast.show(e.getMsg());
        }

        switch (e.getCode()) {
            case HttpResponseCode.ERROR_DEVICE_DISABLE:
            case HttpResponseCode.ERROR_NO_PERMISSION:
                // 发送重新登录，在AppManagerHandlerListener处理
                EventBusUtil.postAppMessage(EVENT_RE_LOGIN);
                break;
            default:
                // 把错误事件发送出去，至于要不要处理在AppManagerHandlerListener
                EventBusUtil.postAppMessage(e.getCode(), e.getMsg());
                this.onError(e.getCode(), e.getMsg());
        }
    }

    @Override
    public void onComplete() {
        if (mRootView != null) {
            mRootView.hideLoading();
        }
    }

    @Override
    public abstract void onNext(T t);

    /**
     * Desc: 具体业务各自处理
     * <p>
     * Author: 廖培坤
     * Date: 2018-07-04
     *
     * @param code
     * @param msg
     */
    public abstract void onError(int code, String msg);

}
