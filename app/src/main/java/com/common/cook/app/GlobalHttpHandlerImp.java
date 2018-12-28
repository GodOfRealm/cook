package com.common.cook.app;

import android.text.TextUtils;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.MetaDataUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.jess.arms.http.GlobalHttpHandler;
import com.common.cook.R;
import com.common.cook.util.CommonUtils;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Copyright (c) 2018 Miguan Inc All rights reserved.
 * Created by Liaopeikun on 2018/6/21
 */
public class GlobalHttpHandlerImp implements GlobalHttpHandler {

    @Override
    public Response onHttpResultResponse(String httpResult, Interceptor.Chain chain, Response response) {
        return response;
    }

    @Override
    public Request onHttpRequestBefore(Interceptor.Chain chain, Request request) {
        if (!NetworkUtils.isConnected()) {
            request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
        }
        if (TextUtils.isEmpty(request.header("imei"))) {
            String timeMillis = String.valueOf(System.currentTimeMillis());
            request = request.newBuilder()
                    .header("imei", CommonUtils.getImei() == null ? "" : CommonUtils.getImei())
                    .header("Authorization", CommonUtils.getToken())
                    .header("channel", CommonUtils.getChannel())
                    .header("deviceType", "1")
                    .header("version", AppUtils.getAppVersionName())
                    .header("signature", getSignature(timeMillis))
                    .header("timestamp", timeMillis)
                    .build();
        }
        return request;
    }

    private String getSignature(String timeMillis) {
        String key1 = MetaDataUtils.getMetaDataInApp("PICK_APP_KEY");
        String key = PickApplication.getInstance().getResources().getString(R.string.key, key1, timeMillis);
        return EncryptUtils.encryptMD5ToString(key).toLowerCase();
    }

}
