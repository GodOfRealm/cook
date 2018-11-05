package com.miguan.youmi.app;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;

import com.jess.arms.base.App;
import com.jess.arms.base.delegate.AppDelegate;
import com.jess.arms.base.delegate.AppLifecycles;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.Preconditions;
import com.miguan.youmi.app.constant.Constant;
import com.miguan.youmi.tinker.TinkerApplicationLike;
import com.tencent.tinker.loader.app.TinkerApplication;
import com.tencent.tinker.loader.shareutil.ShareConstants;


/**
 * Desc:
 * <p>
 * Author: SonnyJack
 * Date: 2018-08-07
 * Copyright: Copyright (c) 2013-2018
 * Company: @米冠网络
 * Update Comments:
 */
public class PickApplication extends TinkerApplication implements App {

    private AppLifecycles mAppDelegate;
    private Typeface typeface;
    private static PickApplication sApplication;

    /**
     * Desc:
     * <p>
     * Author: SonnyJack
     * Date: 2018-08-07
     */
    public PickApplication() {
        super(ShareConstants.TINKER_ENABLE_ALL, TinkerApplicationLike.class.getName());
    }

    /**
     * Desc:
     * <p>
     * Author: SonnyJack
     * Date: 2018-08-07
     *
     * @return pick application
     */
    public static PickApplication getInstance() {
        return sApplication;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        if (mAppDelegate == null)
            this.mAppDelegate = new AppDelegate(base);
        this.mAppDelegate.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = this;
        if (mAppDelegate != null) {
            this.mAppDelegate.onCreate(this);
        }
        typeface = Typeface.createFromAsset(sApplication.getAssets(), Constant.TYPEFACE);//下载的字体

    }

    /**
     * 在模拟环境中程序终止时会被调用
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
        if (mAppDelegate != null) {
            this.mAppDelegate.onTerminate(this);
        }
    }
    public Typeface getTypeface() {
        return typeface;
    }
    /**
     * 将 {@link AppComponent} 返回出去, 供其它地方使用, {@link AppComponent} 接口中声明的方法所返回的实例, 在 {@link #getAppComponent()} 拿到对象后都可以直接使用
     *
     * @return AppComponent
     * @see ArmsUtils#obtainAppComponentFromContext(Context) 可直接获取 {@link AppComponent}
     */
    @NonNull
    @Override
    public AppComponent getAppComponent() {
        Preconditions.checkNotNull(mAppDelegate, "%s cannot be null", AppDelegate.class.getName());
        Preconditions.checkState(mAppDelegate instanceof App, "%s must be implements %s", AppDelegate.class.getName(), App.class.getName());
        return ((App) mAppDelegate).getAppComponent();
    }
}
