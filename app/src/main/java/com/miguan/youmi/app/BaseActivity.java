/*
 * Copyright 2017 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.miguan.youmi.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.KeyboardUtils;
import com.jess.arms.base.delegate.IActivity;
import com.jess.arms.integration.cache.Cache;
import com.jess.arms.integration.cache.CacheType;
import com.jess.arms.integration.lifecycle.ActivityLifecycleable;
import com.jess.arms.mvp.IPresenter;
import com.jess.arms.utils.ArmsUtils;
import com.miguan.youmi.R;
import com.miguan.youmi.app.overlay.DefaultViewExpansionDelegate;
import com.miguan.youmi.app.overlay.IViewConfig;
import com.miguan.youmi.app.overlay.ViewExpansionDelegate;
import com.miguan.youmi.core.util.PickToast;
import com.noober.background.BackgroundLibrary;
import com.trello.rxlifecycle2.android.ActivityEvent;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;

import static com.jess.arms.utils.ThirdViewUtil.convertAutoView;

/**
 * ================================================
 * 因为 Java 只能单继承,所以如果要用到需要继承特定 {@link Activity} 的三方库,那你就需要自己自定义 {@link Activity}
 * 继承于这个特定的 {@link Activity},然后再按照 {@link com.jess.arms.base.BaseActivity} 的格式,将代码复制过去,记住一定要实现{@link IActivity}
 * <p>
 * Created by JessYan on 22/03/2016
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 *
 * @param <P> the type parameter
 */
public abstract class BaseActivity<P extends IPresenter> extends AppCompatActivity implements IActivity, ActivityLifecycleable, IViewConfig {
    /**
     *
     */
    protected final String TAG = this.getClass().getSimpleName();
    private final BehaviorSubject<ActivityEvent> mLifecycleSubject = BehaviorSubject.create();
    /**
     *
     */
    @Inject
    @Nullable
    protected P mPresenter;//如果当前页面逻辑简单, Presenter 可以为 null
    private Cache<String, Object> mCache;
    private Unbinder mUnbinder;
    private Navigator mNavigator = new Navigator();

    @NonNull
    @Override
    public synchronized Cache<String, Object> provideCache() {
        if (mCache == null) {
            mCache = ArmsUtils.obtainAppComponentFromContext(this).cacheFactory().build(CacheType.ACTIVITY_CACHE);
        }
        return mCache;
    }

    @NonNull
    @Override
    public final Subject<ActivityEvent> provideLifecycleSubject() {
        return mLifecycleSubject;
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        View view = convertAutoView(name, context, attrs);
        return view == null ? super.onCreateView(name, context, attrs) : view;
    }

    /************************************ Start： 一些必要且必须写在Activity的方法 */
    protected ViewExpansionDelegate mDelegate;

    @Override
    public void finish() {
        KeyboardUtils.hideSoftInput(getActivity());
        super.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null && mUnbinder != Unbinder.EMPTY)
            mUnbinder.unbind();
        this.mUnbinder = null;
        if (mPresenter != null)
            mPresenter.onDestroy();//释放资源
        this.mPresenter = null;
    }

    /**
     * 是否使用eventBus,默认为使用(true)，
     *
     * @return
     */
    @Override
    public boolean useEventBus() {
        return true;
    }

    /**
     * 这个Activity是否会使用Fragment,框架会根据这个属性判断是否注册{@link android.support.v4.app.FragmentManager.FragmentLifecycleCallbacks}
     * 如果返回false,那意味着这个Activity不需要绑定Fragment,那你再在这个Activity中绑定继承于 {@link com.jess.arms.base.BaseFragment} 的Fragment将不起任何作用
     *
     * @return
     */
    @Override
    public boolean useFragment() {
        return true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        BackgroundLibrary.inject(this);//只能写在这里，要在super.onCreate(savedInstanceState);之前执行
        super.onCreate(savedInstanceState);
        try {
            int layoutResID = initView(savedInstanceState);
            //如果initView返回0,框架则不会调用setContentView(),当然也不会 Bind ButterKnife
            if (layoutResID != 0) {
                setContentView(layoutResID);
                //绑定到butterknife
                mUnbinder = ButterKnife.bind(this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        mNavigator = Navigator.getInstance();
        createViewExpansionDelegate();

        initData(savedInstanceState);
    }

    /**
     * Desc: Activity功能扩展代理类
     * <p>
     * Author: 廖培坤
     * Date: 2018-07-09
     */
    public void createViewExpansionDelegate() {
        mDelegate = new DefaultViewExpansionDelegate(this);
    }

    @Override
    public ViewConfig getViewConfig() {
        return ViewConfig.DEFAULT.clone();
    }

    /**
     * Desc:设置页面标题
     * <p>
     * Author: QiuRonaC
     * Date: 2018-07-05
     */
    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        View view = findViewById(R.id.base_toolbar_title);
        if (view instanceof TextView) {
            ((TextView) view).setText(title);
        }
    }

    /**
     * Desc:获取统一跳转入口类
     * <p>
     * Author: 廖培坤
     * Date: 2018-06-27
     *
     * @return navigator
     */
    public Navigator getNavigator() {
        return mNavigator;
    }

    /**
     * Desc: 获取当前Activity对象
     * <p>
     * Author: SonnyJack
     * Date: 2018-06-28
     *
     * @return activity
     */
    public Activity getActivity() {
        return this;
    }

    /************************************ Start： MVP中的IView接口的方法 */
    public void showLoading() {
        mDelegate.showProgressBar();
    }

    /**
     * Desc:
     * <p>
     * Author: 廖培坤
     * Date: 2018-07-09
     */
    public void hideLoading() {
        mDelegate.hideProgressBar();
    }

    /**
     * Desc:
     * <p>
     * Author: SonnyJack
     * Date: 2018-07-23
     *
     * @param message
     */
    public void showMessage(int message) {
        showMessage(getString(message));
    }

    /**
     * Desc:
     * <p>
     * Author: 廖培坤
     * Date: 2018-07-09
     *
     * @param message
     */
    public void showMessage(@NonNull String message) {
        if (TextUtils.isEmpty(message)) {
            return;
        }
        runOnUiThread(() -> PickToast.show(message));
    }

    /**
     * Desc:
     * <p>
     * Author: 廖培坤
     * Date: 2018-07-09
     *
     * @param intent
     */
    public void launchActivity(@NonNull Intent intent) {
        if (null == intent) {
            return;
        }
        ArmsUtils.startActivity(intent);
    }

    /**
     * Desc:
     * <p>
     * Author: 廖培坤
     * Date: 2018-07-09
     */
    public void killMyself() {
        finish();
    }
    /**********************************   End： MVP中的IView接口的方法 ****************************************/

    /**********************************   Start：设置点击空白处隐藏键盘 ****************************************/
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                InputMethodManager imm =
                        (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS
                );
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    // Return whether touch the view.
    protected boolean isShouldHideKeyboard(@Nullable View v, MotionEvent event) {
        if (v instanceof EditText) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            return !(event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom);
        }
        return false;
    }
    /**********************************   End：设置点击空白处隐藏键盘 ****************************************/

}
