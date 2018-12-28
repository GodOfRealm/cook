package com.common.cook.module.common.model.services;

import com.common.cook.bean.home.Banner;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

import static com.common.cook.app.constant.Constant.SYSTEM_API_HEAD;
import static com.common.cook.app.constant.Constant.USER_API_HEAD;

/**
 * Desc: 通用网络请求
 * <p>
 * Author: 廖培坤
 * Date: 2018-07-17
 * Copyright: Copyright (c) 2013-2018
 * Company: @米冠网络
 * Update Comments:
 */
public interface CommonService {



    /**
     * Desc: 下载
     * <p>
     * Author: SonnyJack
     * Date: 2018-09-10
     *
     * @param url
     * @return observable
     */
    @Streaming
    @GET
    Observable<ResponseBody> download(@Url String url);

    /**
     * Desc:分享后告知服务端
     * <p>
     * Author: SonnyJack
     * Date: 2018-09-10
     *
     * @param code 分享的code，用于区分哪个地方的分享
     * @return observable
     */
    @GET(USER_API_HEAD + "share/callback")
    Observable<String> shareCallBack(
            @Query("code") String code
    );

    /**
     * Desc: 根据类型获取Banner
     * <p>
     * Author: 廖培坤
     * Date: 2018-09-20
     *
     * @param type banner类型: 0.主页, 1.活动, 2.启动页, 3.聊天室内的banner 4.意见反馈 5达人列表
     * @return observable
     */
    @GET(SYSTEM_API_HEAD + "banner")
    Observable<List<Banner>> getBanners(
            @Query("type") int type
    );

}
