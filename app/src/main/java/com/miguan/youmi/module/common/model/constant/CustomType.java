package com.miguan.youmi.module.common.model.constant;

import android.support.annotation.IntDef;

/**
 * Desc: 客服类型
 * <p>
 * Author: 廖培坤
 * Date: 2018-09-13
 * Copyright: Copyright (c) 2013-2018
 * Company: @米冠网络
 * Update Comments:
 */
public interface CustomType {

    int HIDE = 0; // 默认隐藏

    int ORDER_APPEAL = 1;

    int HELP_CENTER = 2;

    @IntDef({HIDE, ORDER_APPEAL, HELP_CENTER})
    @interface Type {
    }

}
