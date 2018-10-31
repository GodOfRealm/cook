package com.miguan.youmi.module.main;

import android.content.Context;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.jess.arms.utils.ArmsUtils;
import com.miguan.youmi.app.constant.ARouterPaths;


import java.util.ArrayList;
import java.util.List;


/**
 * Desc: Main模块路由
 * <p>
 * Author: SonnyJack
 * Date: 2018-06-28
 * Copyright: Copyright (c) 2013-2018
 * Company: @米冠网络
 * Update Comments:
 */
public class MainNavigator {

    /**
     * Desc: 打开MainActivity方法
     * <p>
     * Author: SonnyJack
     * Date: 2018-06-28
     */
    public void openMainActivity() {
        ARouter.getInstance()
                .build(ARouterPaths.MAIN_HOME)
                .navigation();
    }


    /**
     * Desc: 打开SearchActivity方法
     * <p>
     * Author: SonnyJack
     * Date: 2018-07-02
     */
    public void openSearchActivity() {
        ARouter.getInstance()
                .build(ARouterPaths.HOME_SEARCH)
                .navigation();
    }

    /**
     * Desc:打开引导页
     * <p>
     * Author: 张文顺
     * Date: 2018-08-09
     */
    public void openguide() {
        ARouter.getInstance()
                .build(ARouterPaths.MAIN_GUIDE)
                .navigation();
    }



}
