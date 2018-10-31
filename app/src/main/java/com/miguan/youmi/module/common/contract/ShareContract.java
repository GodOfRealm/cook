package com.miguan.youmi.module.common.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;

import io.reactivex.Observable;


/**
 * Desc:分享操作相关
 * <p>
 * Author: SonnyJack
 * Date: 2018-09-10
 * Copyright: Copyright (c) 2013-2018
 * Company: @米冠网络
 * Update Comments:
 */
public interface ShareContract {
    /**
     * Desc:
     * <p>
     * Author: SonnyJack
     * Date: 2018-09-10
     * Copyright: Copyright (c) 2013-2018
     * Company: @米冠网络
     * Update Comments:
     */
//对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

    }

    /**
     * Desc:
     * <p>
     * Author: SonnyJack
     * Date: 2018-09-10
     * Copyright: Copyright (c) 2013-2018
     * Company: @米冠网络
     * Update Comments:
     */
//Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        //分享后告知服务器
        Observable<String> shareCallBack(String code);
    }
}
