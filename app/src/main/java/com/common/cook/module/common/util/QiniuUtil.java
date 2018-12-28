package com.common.cook.module.common.util;

import com.qiniu.android.storage.UploadManager;

/**
 * Desc: 七牛上传辅助类，为了让UploadManager是单例
 * <p>
 * Author: 廖培坤
 * Date: 2018-07-17
 * Copyright: Copyright (c) 2013-2018
 * Company: @米冠网络
 * Update Comments:
 */
public class QiniuUtil {

    private static QiniuUtil sQiniuUtil;

    private UploadManager mUploadManager;

    private QiniuUtil() {
        mUploadManager = new UploadManager();
    }

    /**
     * Desc:
     * <p>
     * Author: 廖培坤
     * Date: 2018-07-17
     *
     * @return qiniu util
     */
    public static QiniuUtil getInstance() {
        if (null == sQiniuUtil) {
            synchronized (QiniuUtil.class) {
                if (sQiniuUtil == null) {
                    sQiniuUtil = new QiniuUtil();
                }
            }
        }
        return sQiniuUtil;
    }

    /**
     * Desc:
     * <p>
     * Author: 廖培坤
     * Date: 2018-07-17
     *
     * @return upload manager
     */
    public UploadManager getUploadManager() {
        return mUploadManager;
    }

}
