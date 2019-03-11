package com.common.cook.util;

import com.common.cook.bean.VideoTypeBean;
import com.common.cook.module.main.ui.activity.MainActivity;
import com.common.cook.module.vip.ui.activity.VipActivity;

import java.util.ArrayList;
import java.util.Arrays;

import static com.common.cook.util.CommonUtils.getServerConfig;

/**
 * Desc:url相关工具类
 * <p>
 * Author: 张文顺
 * Date: 2019-02-04
 * Copyright: Copyright (c) 2013-2019
 * Update Comments:
 */
public class UrlUtils {
    /**
     * Desc:是否是视频连接
     * <p>
     * Author: 张文顺
     * Date: 2019-02-04
     *
     * @return string
     */
    public static boolean isVideo(String url) {
        VideoTypeBean qq = new VideoTypeBean();
        qq.setPrefix("qq");
        qq.setSuffix("cover");
        VideoTypeBean youku = new VideoTypeBean();
        youku.setPrefix("youku");
        youku.setSuffix("html");
        VideoTypeBean iqiyi = new VideoTypeBean();
        iqiyi.setPrefix("iqiyi");
        iqiyi.setSuffix("html");
        ArrayList<VideoTypeBean> list = new ArrayList<VideoTypeBean>() {{
            add(qq);
            add(youku);
            add(iqiyi);
        }};

        for (VideoTypeBean videoTypeBean : list) {
            if (!url.contains(VipActivity.VIP_URL) && url.contains(videoTypeBean.getPrefix()) && url.contains(videoTypeBean.getSuffix())) {
                return true;
            }
        }
        return false;
    }
}
