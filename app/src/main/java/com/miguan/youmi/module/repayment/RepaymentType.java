package com.miguan.youmi.module.repayment;

import android.support.annotation.IntDef;

/**
 * Desc:还款模式
 * <p>
 * Author: 张文顺
 * Date: 2018-07-19
 * Copyright: Copyright (c) 2013-2018
 * Company: @米冠网络
 * Update Comments:
 */
public interface RepaymentType {
    /**
     * 本卡余额代偿
     */
    int THIS_CARD = 0;

    /**
     * 信用代偿
     */
    int CREDIT = 1;


    @IntDef({THIS_CARD, CREDIT})
    @interface Val {
    }

}
