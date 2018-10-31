package com.miguan.youmi.module.common.model.services;

import android.support.annotation.StringDef;

/**
 * Desc: 获取七牛上传token的资源类型: img:图片, video:视频, voice:语音
 * <p>
 * Author: 廖培坤
 * Date: 2018-07-17
 * Copyright: Copyright (c) 2013-2018
 * Company: @米冠网络
 * Update Comments:
 */
public interface UploadType {

    String IMAGE = "img";

    String VIDEO = "video";

    String VOICE = "voice";

    @StringDef({IMAGE, VIDEO, VOICE})
    @interface Val {
    }

}
