package com.miguan.youmi.module.repayment;

import com.alibaba.android.arouter.launcher.ARouter;
import com.miguan.youmi.app.constant.ARouterPaths;
import com.miguan.youmi.app.constant.ExtraConstant;


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
    /**
     * Desc: 本卡余额代偿
     * <p>
     * Author: SonnyJack
     * Date: 2018-06-28
     */
    public void openThisCardBalanceActivity(@RepaymentType.Val int type) {
        ARouter.getInstance()
                .build(ARouterPaths.REPAYMENT_THIS_CARD_BALANCE)
                .withInt(ExtraConstant.REPAYMENT_TYPE, type)
                .navigation();
    }

    /**
     * Desc:添加信用卡基础信息
     * <p>
     * Author: SonnyJack
     * Date: 2018-06-28
     */
    public void openAddCardBaseInfo() {
        ARouter.getInstance()
                .build(ARouterPaths.REPAYMENT_ADD_CREDIT_BASE_INFO)
                .navigation();
    }
}
