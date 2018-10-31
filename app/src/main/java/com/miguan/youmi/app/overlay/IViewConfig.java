package com.miguan.youmi.app.overlay;

import com.miguan.youmi.app.ViewConfig;

/**
 * Desc:实现该接口可重写Activity/Fragment各个配置参数
 * <p>
 * Author: 廖培坤
 * Date: 2018-06-27
 * Copyright: Copyright (c) 2013-2018
 * Company: @米冠网络
 * Update Comments:
 */
public interface IViewConfig {

    /**
     * Desc:获取Activity的各个配置参数，具体配置见{@link ViewConfig}
     * <p>
     * Author: 廖培坤
     * Date: 2018-06-27
     *
     * @return activity config
     */
    ViewConfig getViewConfig();

}
