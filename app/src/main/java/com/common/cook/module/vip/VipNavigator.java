package com.common.cook.module.vip;

import com.alibaba.android.arouter.launcher.ARouter;
import com.common.cook.app.constant.ARouterPaths;

//超级会员路由
public class VipNavigator {
    /**
     * Desc: 打开超级会员界面
     */
    public void openVipActivity() {
        ARouter.getInstance()
                .build(ARouterPaths.VIP_HOME)
                .navigation();
    }
}
