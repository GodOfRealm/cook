package com.common.cook.widget.dialog;

import android.support.annotation.IntDef;

/**
 * Desc:单按钮、双按钮
 * <p>
 * Author: 张文顺
 * Date: 2018-07-19
 * Copyright: Copyright (c) 2013-2018
 * Company: @米冠网络
 * Update Comments:
 */
public interface CustomDialogType {


    int TYPE_DOUBLE = 0;


    int TYPE_SINGLE = 1;


    @IntDef({TYPE_DOUBLE, TYPE_SINGLE})
    @interface Val {
    }

}
