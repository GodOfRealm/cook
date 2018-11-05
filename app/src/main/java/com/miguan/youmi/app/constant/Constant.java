package com.miguan.youmi.app.constant;

import android.os.Environment;

import com.blankj.utilcode.util.AppUtils;
import com.miguan.youmi.BuildConfig;

import java.io.File;

/**
 * Desc:全局的常量
 * <p>
 * Author: 廖培坤
 * Date: 2018-06-14
 * Copyright: Copyright (c) 2013-2018
 * Company: @米冠网络
 * Update Comments:
 */
public interface Constant {

    /**
     * 注意： 如果通过改DEBUG = false来运行debug的正式服会导致融云token失效的问题无法通讯
     */
    boolean DEBUG = BuildConfig.DEBUG;

    String BASE_URL = "http://" + (DEBUG ? "beta." : (BuildConfig.IS_PREVIEW ? "pre-" : "")) + "api.pickdoki.com/";
    //String BASE_URL = "http://pre-api.pickdoki.com/";
    String USER_API_HEAD = "api-user/";
    String SYSTEM_API_HEAD = "api-system/";
    String ORDER_API_HEAD = "api-order/";

    int SHORT_VIDEO_MAX_DURATION = 10999;

    int DEFAULT_PAGE_SIZE = 30;//请求列表数据默认的一页大小

    int PUBLISH_ORDER_TIME_MINUTE = 20;//发布需求订单有效时长(分钟)

    String WX_APP_ID = "wx791fc97221e2c6ad";
    String WX_SECRET = "8df87f02324e79d2d3b731f87568c816";

    String QQ_ZONE_KEY = "101483753";
    String QQ_ZONE_SECRET = "990af7a48f3b5bdff6ef425f1491fb66";
    String PAY_REFERER="http://api.91mmliao.com/";

    /**
     * 声网APP_ID
     */
    String AGORA_APP_ID = "28e7a246775a409189646f62b94b95d2";

    /**
     * SD卡目录
     */
    String OUTPUT_DIR = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + AppUtils.getAppPackageName();

    /**
     * 认证录音
     */
    String VOICE_CERTIFY_PATH = OUTPUT_DIR + "/CertifyVoice/";

    /**
     * 图片
     */
    String IMAGE_PATH = OUTPUT_DIR + "/Image/";

    /**
     * 下载
     */
    String DOWNLOAD_PATH = OUTPUT_DIR + "/Download/";
    /**
     * 数字字体
     */
    String TYPEFACE = "fonts/DINMittelschrift.otf";
}
