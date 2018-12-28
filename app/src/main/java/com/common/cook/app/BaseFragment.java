package com.common.cook.app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jess.arms.mvp.IPresenter;
import com.jess.arms.utils.ArmsUtils;
import com.common.cook.app.overlay.DefaultViewExpansionDelegate;
import com.common.cook.app.overlay.IViewConfig;
import com.common.cook.app.overlay.ViewExpansionDelegate;
import com.common.cook.core.util.PickToast;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * Desc:  Fragment基类
 * <p>
 * Author: SonnyJack
 * Date: 2018-07-02
 * Copyright: Copyright (c) 2013-2018
 * Company: @米冠网络
 * Update Comments:
 *
 * @param <P> the type parameter
 */
public abstract class BaseFragment<P extends IPresenter> extends com.jess.arms.base.BaseFragment<P> implements IViewConfig {

    protected View mView;
    protected ViewExpansionDelegate mDelegate;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = super.onCreateView(inflater, container, savedInstanceState);
            createViewExpansionDelegate();
        }
        return mView;
    }

    public void createViewExpansionDelegate() {
        mDelegate = new DefaultViewExpansionDelegate(getActivity());
    }

    /**
     * 通过此方法可以使 Fragment 能够与外界做一些交互和通信, 比如说外部的 Activity 想让自己持有的某个 Fragment 对象执行一些方法,
     * 建议在有多个需要与外界交互的方法时, 统一传 {@link Message}, 通过 what 字段来区分不同的方法, 在 {@link #setData(Object)}
     * 方法中就可以 {@code switch} 做不同的操作, 这样就可以用统一的入口方法做多个不同的操作, 可以起到分发的作用
     * <p>
     * 调用此方法时请注意调用时 Fragment 的生命周期, 如果调用 {@link #setData(Object)} 方法时 {@link Fragment#onCreate(Bundle)} 还没执行
     * 但在 {@link #setData(Object)} 里却调用了 Presenter 的方法, 是会报空的, 因为 Dagger 注入是在 {@link Fragment#onCreate(Bundle)} 方法中执行的
     * 然后才创建的 Presenter, 如果要做一些初始化操作,可以不必让外部调用 {@link #setData(Object)}, 在 {@link #initData(Bundle)} 中初始化就可以了
     * <p>
     * Example usage:
     * <pre>
     * public void fillIsMeUserInfo(@Nullable Object data) {
     *     if (data != null && data instanceof Message) {
     *         switch (((Message) data).what) {
     *             case 0:
     *                 loadData(((Message) data).arg1);
     *                 break;
     *             case 1:
     *                 refreshUI();
     *                 break;
     *             default:
     *                 //do something
     *                 break;
     *         }
     *     }
     * }
     *
     * // call fillIsMeUserInfo(Object):
     * Message data = new Message();
     * data.what = 0;
     * data.arg1 = 1;
     * fragment.fillIsMeUserInfo(data);
     * </pre>
     *
     * @param data 当不需要参数时 {@code data} 可以为 {@code null}
     */
    @Override
    public void setData(@Nullable Object data) {

    }

    /**
     * Desc:
     * <p>
     * Author: SonnyJack
     * Date: 2018-07-02
     */
    public void showLoading() {
        mDelegate.showProgressBar();
    }

    /**
     * Desc:
     * <p>
     * Author: SonnyJack
     * Date: 2018-07-02
     */
    public void hideLoading() {
        mDelegate.hideProgressBar();
    }

    /**
     * Desc:
     * <p>
     * Author: SonnyJack
     * Date: 2018-07-02
     *
     * @param message
     */
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        getActivity().runOnUiThread(() -> PickToast.show(message));
    }

    /**
     * Desc:
     * <p>
     * Author: SonnyJack
     * Date: 2018-07-02
     *
     * @param intent
     */
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    /**
     * Desc:
     * <p>
     * Author: SonnyJack
     * Date: 2018-07-02
     */
    public void killMyself() {
        if (getActivity() != null) {
            getActivity().finish();
        }
    }

    public Navigator getNavigator() {
        return Navigator.getInstance();
    }

    @Override
    public ViewConfig getViewConfig() {
        return ViewConfig.DEFAULT.clone();
    }

}
