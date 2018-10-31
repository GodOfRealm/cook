package com.miguan.youmi.bean.account;


import android.support.annotation.IntDef;

/**
 * Desc:  性别
 * <p>
 * Author: SonnyJack
 * Date: 2018-07-09
 * Copyright: Copyright (c) 2013-2018
 * Company: @米冠网络
 * Update Comments:
 */
public interface GenderType {

    int UNKNOWN = 0; // 未知

    int MALE = 1;//男

    int FEMALE = 2;//女

    int ALL = 3;//全部

    @IntDef({MALE, FEMALE})
    @interface Val {
    }

}
