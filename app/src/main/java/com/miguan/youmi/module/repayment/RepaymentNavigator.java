package com.miguan.youmi.module.repayment;

import com.alibaba.android.arouter.launcher.ARouter;
import com.miguan.youmi.app.constant.ARouterPaths;


/**
 * Desc: 还款模块路由
 * <p>
 * Author: SonnyJack
 * Date: 2018-06-28
 * Copyright: Copyright (c) 2013-2018
 * Company: @米冠网络
 * Update Comments:
 */
public class RepaymentNavigator {


    /**
     * Desc: 选择银行方法
     * <p>
     * Author: SonnyJack
     * Date: 2018-06-28
     */
    public void openSelectBankActivity() {
        ARouter.getInstance()
                .build(ARouterPaths.REPAYMENT_SELECT_BANK)
                .navigation();
    }



}
