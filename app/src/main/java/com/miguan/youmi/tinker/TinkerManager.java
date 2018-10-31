package com.miguan.youmi.tinker;

import android.app.Application;
import android.content.Context;

import com.tencent.tinker.lib.listener.DefaultPatchListener;
import com.tencent.tinker.lib.listener.PatchListener;
import com.tencent.tinker.lib.patch.AbstractPatch;
import com.tencent.tinker.lib.patch.UpgradePatch;
import com.tencent.tinker.lib.reporter.DefaultLoadReporter;
import com.tencent.tinker.lib.reporter.DefaultPatchReporter;
import com.tencent.tinker.lib.reporter.LoadReporter;
import com.tencent.tinker.lib.reporter.PatchReporter;
import com.tencent.tinker.lib.service.AbstractResultService;
import com.tencent.tinker.lib.tinker.TinkerApplicationHelper;
import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.tencent.tinker.loader.app.ApplicationLike;

import java.util.HashMap;

/**
 * Created by SonnyJack on 2018/6/22 16:20.
 */
public class TinkerManager {

    private static boolean isInstalled = false;
    // 这里的ApplicationLike可以理解为Application的载体
    private static ApplicationLike sApplicationLike;

    /**
     * 默认初始化Tinker
     *
     * @param applicationLike
     */
    public synchronized static void installTinker(ApplicationLike applicationLike) {
        sApplicationLike = applicationLike;
        if (isInstalled) {
            return;
        }
        TinkerInstaller.install(applicationLike);
        isInstalled = true;
    }

    public synchronized static void installTinker(ApplicationLike applicationLike, Class<? extends AbstractResultService> resultServiceClass) {
        sApplicationLike = applicationLike;
        if (isInstalled) {
            return;
        }
        Application application = sApplicationLike.getApplication();
        LoadReporter loadReporter = new DefaultLoadReporter(application);//Tinker在加载补丁时的一些回调
        PatchReporter patchReporter = new DefaultPatchReporter(application);//Tinker在修复或者升级补丁时的一些回调
        PatchListener patchListener = new DefaultPatchListener(application);//用来过滤Tinker收到的补丁包的修复、升级请求，也就是决定我们是不是真的要唤起:patch进程去尝试补丁合成
        AbstractPatch upgradePatchProcessor = new UpgradePatch();//用来升级当前补丁包的处理类，一般来说你也不需要复写它
        TinkerInstaller.install(applicationLike,
                loadReporter,
                patchReporter,
                patchListener,
                resultServiceClass,
                upgradePatchProcessor);
        isInstalled = true;
    }

    /**
     * 增加补丁包
     *
     * @param path
     */
    public static void addPatch(String path) {
        try {
            TinkerInstaller.onReceiveUpgradePatch(getApplicationContext(), path);
        } catch (Exception e) {

        }
    }

    /**
     * 获取上下文
     *
     * @return
     */
    private static Context getApplicationContext() {
        if (sApplicationLike != null) {
            return sApplicationLike.getApplication().getApplicationContext();
        }
        return null;
    }

    public static int getPatchVersion() {
        HashMap<String, String> map = TinkerApplicationHelper.getPackageConfigs(sApplicationLike);
        String patchVersion = null == map ? null : map.get("patchVersion");
        int version = 0;
        try {
            //当前补丁版本
            version = Integer.valueOf(patchVersion);
        } catch (Exception e) {
            version = 0;
        }
        return version;
    }
}
