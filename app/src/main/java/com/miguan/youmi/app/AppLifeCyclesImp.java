package com.miguan.youmi.app;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.http.HttpResponseCache;
import android.support.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.ProcessUtils;
import com.blankj.utilcode.util.Utils;
import com.jess.arms.base.App;
import com.jess.arms.base.delegate.AppLifecycles;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.miguan.youmi.BuildConfig;
import com.miguan.youmi.R;
import com.miguan.youmi.app.analysis.AnalysisManager;
import com.miguan.youmi.app.constant.Constant;
import com.miguan.youmi.core.util.IImageLoader;
import com.miguan.youmi.util.CrashHandler;
import com.miguan.youmi.util.PickUtils;
import com.noober.background.BackgroundLibrary;
import com.sensorsdata.analytics.android.sdk.SensorsDataAPI;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

import timber.log.Timber;

/**
 * Desc: Application生成周期代理类
 * <p>
 * Author: 廖培坤
 * Date: 2018-07-05
 * Copyright: Copyright (c) 2013-2018
 * Company: @米冠网络
 * Update Comments:
 */
public class AppLifeCyclesImp implements AppLifecycles {


    @Override
    public void attachBaseContext(Context base) {
        MultiDex.install(base);  //这里比 onCreate 先执行,常用于 MultiDex 初始化,插件化框架的初始化
    }

    @Override
    public void onCreate(Application application) {
        Utils.init(application);
        if (!ProcessUtils.isMainProcess()) {
            return;
        }
        initHandleListener(application);
        PickUtils.init(application);
//        initCanary(application);
        initLog();
        initARouter(application);
        initUmeng(application);
        initJPush(application);
        IImageLoader.init(R.mipmap.common_ic_def_image);
        CrashHandler.initialCrashHandler(application);
        initSensors(application);

        File cacheDir = new File(application.getCacheDir(), "http");
        try {
            HttpResponseCache.install(cacheDir, 1024 * 1024 * 128);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    /**
     * Desc: 全局事件处理，通过{@link com.jess.arms.integration.AppManager#post}发布事件
     * 通过 Message 对象中不同的 what 区分不同的方法和 Handler 同理
     * <p>
     * Author: 廖培坤
     * Date: 2018-07-04
     */
    private void initHandleListener(Application application) {
        ArmsUtils.obtainAppComponentFromContext(application).appManager()
                .setHandleListener(new AppManagerHandlerListener());
    }

    private void initLog() {
        if (BuildConfig.LOG_DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }


    /**
     * Desc: 初始LeakCanary内存检测
     * <p>
     * Author: 廖培坤
     * Date: 2018-07-05
     *
     * @param application
     */
    private void initCanary(Application application) {
        if (application instanceof App && !LeakCanary.isInAnalyzerProcess(application)) {
            AppComponent appComponent = ArmsUtils.obtainAppComponentFromContext(application);
            appComponent.extras().put(RefWatcher.class.getName(),
                    BuildConfig.USE_CANARY ? LeakCanary.install(application) : RefWatcher.DISABLED);
        }
    }

    /**
     * Desc: 初始阿里路由
     * <p>
     * Author: 廖培坤
     * Date: 2018-07-05
     *
     * @param application
     */
    private void initARouter(Application application) {
        if (com.blankj.utilcode.util.AppUtils.isAppDebug(application.getPackageName())) {
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(application);
    }

    /**
     * Desc:初始化友盟分享和统计
     * <p>
     * Author: 廖培坤
     * Date: 2018-07-05
     *
     * @param application
     */
    private void initUmeng(Application application) {
        //友盟渠道
        //注意！！！umeng的渠道每台设备只识别一次，测试不同渠道号，需要不同的umeng key
        String channel = PickUtils.getChannel();
        //友盟对应参数：UMConfigure.init(Context context, String appkey, String channel, int deviceType, String pushSecret);
        String appKey = getMeta("umeng_app_key", application);
        UMConfigure.init(application, appKey, channel, UMConfigure.DEVICE_TYPE_PHONE, null);

        PlatformConfig.setWeixin(Constant.WX_APP_ID, Constant.WX_SECRET);
        PlatformConfig.setQQZone(Constant.QQ_ZONE_KEY, Constant.QQ_ZONE_SECRET);
    }

    /**
     * Desc:初始化神策
     *
     * 调用顺序：
     * ① 初始化 SDK
     * ② registerSuperProperties 设置公共属性（如果不需要设置公共属性，忽略此步）
     * ③ if 获取到用户的 注册 ID ，调用 login 传入 注册 ID
     * ④ trackInstallation 记录激活事件，渠道追踪 （如果要做 App 内推广，请参考 App 内推广文档调用 trackInstallation ）
     * ⑤ enableAutoTrack 开启全埋点自动采集
     * ⑥ trackFragmentAppViewScreen 开启 Fragment 页面浏览事件自动采集（如果不需要自动采集 Fragment 浏览页面事件，忽略此步）
     *
     * <p>
     * Author: 廖培坤
     * <p>
     * <p>
     * <p>
     * Date: 2018-10-09
     *
     * @param application
     */
    private void initSensors(Application application) {
//        String serverUrl = "http://91mmliao.cloud.sensorsdata.cn:8006/sa?project=" + (Constant.DEBUG ? "default" : "PICK") + "&token=87974179eacab0e6";
        SensorsDataAPI.DebugMode debugMode = BuildConfig.DEBUG ? SensorsDataAPI.DebugMode.DEBUG_AND_TRACK
                : SensorsDataAPI.DebugMode.DEBUG_OFF;
        SensorsDataAPI.sharedInstance(application, "", debugMode);

        try {
            JSONObject propertiesInstall = new JSONObject();
            propertiesInstall.put("Channel", PickUtils.getChannel());
            SensorsDataAPI.sharedInstance().trackInstallation("AppInstall");

            AnalysisManager.getInstance().registerSuperProperties();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 打开自动采集, 并指定追踪哪些 AutoTrack 事件
        List<SensorsDataAPI.AutoTrackEventType> eventTypeList = new ArrayList<>();
        // $AppStart
        eventTypeList.add(SensorsDataAPI.AutoTrackEventType.APP_START);
        // $AppEnd
        eventTypeList.add(SensorsDataAPI.AutoTrackEventType.APP_END);
        // $AppViewScreen
        eventTypeList.add(SensorsDataAPI.AutoTrackEventType.APP_VIEW_SCREEN);
        // $AppClick
        eventTypeList.add(SensorsDataAPI.AutoTrackEventType.APP_CLICK);
        SensorsDataAPI.sharedInstance().enableAutoTrack(eventTypeList);

        SensorsDataAPI.sharedInstance().trackFragmentAppViewScreen();
    }

    /**
     * Desc:
     * <p>
     * Author: 廖培坤
     * Date: 2018-07-04
     *
     * @param key
     * @param application
     * @return string
     */
    public String getMeta(String key, Application application) {
        try {
            ApplicationInfo applicationInfo = application.getPackageManager().getApplicationInfo(application.getPackageName(), PackageManager.GET_META_DATA);
            return applicationInfo.metaData.getString(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 初始化极光推送
     */
    private void initJPush(Context context) {
        JPushInterface.setDebugMode(BuildConfig.DEBUG);
        JPushInterface.init(context);
    }

    @Override
    public void onTerminate(Application application) {

    }
}
